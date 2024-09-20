package pl.training.security.jwt

import org.springframework.security.authentication.AbstractAuthenticationToken

class JwtAuthentication(val token: String) : AbstractAuthenticationToken(emptyList()) {

    override fun getCredentials() = token

    override fun getPrincipal(): Any? = null

}
