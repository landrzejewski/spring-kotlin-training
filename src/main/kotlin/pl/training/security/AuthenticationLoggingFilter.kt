package pl.training.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.util.logging.Logger

class AuthenticationLoggingFilter : OncePerRequestFilter() {

    private val log = Logger.getLogger(AuthenticationLoggingFilter::class.java.name)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication != null) {
            log.info("Authenticated user: $authentication")
        }
        filterChain.doFilter(request, response)
    }

}
