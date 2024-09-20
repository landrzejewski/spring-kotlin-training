package pl.training.security

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.access.intercept.RequestAuthorizationContext
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import java.util.function.Supplier

class RequestUrlAuthorizationManager : AuthorizationManager<RequestAuthorizationContext> {

    override fun check(
        authentication: Supplier<Authentication>,
        context: RequestAuthorizationContext
    ): AuthorizationDecision {
        val request = context.request
        val hasAccess = getRoles(authentication)
            .any { role -> check(role, request) }
        return AuthorizationDecision(hasAccess)
    }

    private fun getRoles(authentication: Supplier<Authentication>): Set<String> {
        return authentication.get()
            .authorities
            .map(GrantedAuthority::getAuthority)
            .toSet()
    }

    private fun check(role: String, httpRequest: HttpServletRequest) =
        mappings.getOrDefault(role, emptySet())
            .any { matcher -> matcher.matches(httpRequest) }

    companion object {

        private val mappings: Map<String, Set<RequestMatcher>> = mapOf(
            "ROLE_ADMIN" to setOf(AntPathRequestMatcher("/**")),
            "ROLE_USER" to setOf(AntPathRequestMatcher("/api/**"))
        )

    }

}
