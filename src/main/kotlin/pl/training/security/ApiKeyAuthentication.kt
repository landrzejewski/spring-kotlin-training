package pl.training.security

import org.springframework.security.authentication.AbstractAuthenticationToken

class ApiKeyAuthentication(val apiKey: String) : AbstractAuthenticationToken(emptyList()) {

    override fun getCredentials() = apiKey

    override fun getPrincipal() = null

}
