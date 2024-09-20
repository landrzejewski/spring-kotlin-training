package pl.training.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

class KeycloakLogoutHandler(private val restTemplate: RestTemplate) : LogoutHandler {

    override fun logout(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        val user = authentication.principal as OidcUser
        val endSessionEndpoint = "${user.issuer}$LOGOUT_ENDPOINT"
        val uri = UriComponentsBuilder
            .fromUriString(endSessionEndpoint)
            .queryParam(ID_TOKEN_HINT, user.idToken.tokenValue)
            .toUriString()
        restTemplate.getForObject(uri, String::class.java)
    }

    companion object {
        private const val LOGOUT_ENDPOINT = "/protocol/openid-connect/logout"
        private const val ID_TOKEN_HINT = "id_token_hint"
    }

}
