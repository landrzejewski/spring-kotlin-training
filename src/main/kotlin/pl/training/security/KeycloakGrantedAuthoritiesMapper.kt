package pl.training.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority

class KeycloakGrantedAuthoritiesMapper : GrantedAuthoritiesMapper {

    @Suppress("UNCHECKED_CAST")
    override fun mapAuthorities(authorities: Collection<GrantedAuthority>): Collection<GrantedAuthority> {
        val grantedAuthorities = mutableSetOf<String>()
        authorities.forEach { authority ->
            when (authority) {
                is OAuth2UserAuthority -> {
                    val userAttributes = authority.attributes
                    val realmAccess = userAttributes[REALM_CLAIM] as? Map<String, Any>
                    val roles = realmAccess?.get(ROLES_CLAIM) as? Collection<String>
                    if (roles != null) {
                        grantedAuthorities.addAll(roles)
                    }
                }
                else -> grantedAuthorities.add(authority.authority)
            }
        }
        return grantedAuthorities.map { SimpleGrantedAuthority(ROLE_PREFIX + it.uppercase()) }.toSet()
    }

    companion object {
        private const val REALM_CLAIM = "realm_access"
        private const val ROLES_CLAIM = "roles"
        private const val ROLE_PREFIX = "ROLE_"
    }

}
