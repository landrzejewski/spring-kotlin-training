package pl.training.security.jwt

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken.authenticated
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority

//@Component
class JwtAuthenticationProvider(private val jwtService: JwtService) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication?): Authentication? {
        if (authentication is JwtAuthentication) {
            val token = authentication.token
            return jwtService.verify(token)
                .map { principal ->
                    val roles = principal.roles.map { SimpleGrantedAuthority(it) }
                    authenticated(principal.username, token, roles)
                }
                .orElseThrow { BadCredentialsException("Invalid token") }
        }
        return null
    }

    override fun supports(authentication: Class<*>): Boolean {
        return JwtAuthentication::class.java.isAssignableFrom(authentication)
    }

}
