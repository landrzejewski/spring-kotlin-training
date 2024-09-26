package pl.training.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.naming.AuthenticationException

@Component
class ApiKeyAuthenticationFilter(private val authenticationConfiguration: AuthenticationConfiguration) :
    OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader(AUTHORIZATION)
        if (authorizationHeader != null && authorizationHeader.startsWith(API_KEY_PREFIX)) {
            val apiKey = authorizationHeader.substring(API_KEY_PREFIX.length)
            val apiKeyAuthentication = ApiKeyAuthentication(apiKey)
            try {
                val resultAuthentication = authenticationConfiguration.authenticationManager
                    .authenticate(apiKeyAuthentication)
                save(resultAuthentication)
            } catch (authenticationException: AuthenticationException) {
                response.status = SC_UNAUTHORIZED
            }
        }
        filterChain.doFilter(request, response)
    }

    private fun save(authentication: Authentication) {
        val securityContext = SecurityContextHolder.createEmptyContext()
        securityContext.authentication = authentication
        SecurityContextHolder.setContext(securityContext)
    }

    companion object {

        private const val API_KEY_PREFIX = "API_KEY "

    }

}
