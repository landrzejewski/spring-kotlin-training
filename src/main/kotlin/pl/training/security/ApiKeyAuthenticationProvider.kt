package pl.training.security

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken.authenticated
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

@Component
class ApiKeyAuthenticationProvider : AuthenticationProvider {

    override fun authenticate(authentication: Authentication?): Authentication? {
        if (authentication is ApiKeyAuthentication) {
            val apiKey = authentication.apiKey
            if (apiKey !in VALID_API_KEYS) {
                throw BadCredentialsException("Invalid ApiKey")
            }
            return authenticated("", "", DEFAULT_ROLES)
        }
        return null
    }

    override fun supports(authentication: Class<*>) =
        ApiKeyAuthentication::class.java.isAssignableFrom(authentication)

    companion object {

        private val VALID_API_KEYS = setOf("1234567890")

        private val DEFAULT_ROLES = setOf("ROLE_ADMIN")
            .map { SimpleGrantedAuthority(it) }
            .toSet()

    }

}
