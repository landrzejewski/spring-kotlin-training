package pl.training.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(private val authenticationConfiguration: AuthenticationConfiguration) :
    OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader(AUTHORIZATION)
        if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
            val token = authHeader.substring(TOKEN_PREFIX.length)
            val jwtAuthentication = JwtAuthentication(token)
            try {
                val authentication = authenticationConfiguration.authenticationManager
                    .authenticate(jwtAuthentication)
                save(authentication)
            } catch (_: AuthenticationException) {
                response.status = SC_UNAUTHORIZED
            } catch (exception: Exception) {
                throw ServletException(exception)
            }
        }
        filterChain.doFilter(request, response)
    }

    private fun save(authentication: Authentication) {
        val context = SecurityContextHolder.createEmptyContext()
        context.authentication = authentication
        SecurityContextHolder.setContext(context)
    }

    companion object {
        private const val TOKEN_PREFIX = "bearer "
    }

}
