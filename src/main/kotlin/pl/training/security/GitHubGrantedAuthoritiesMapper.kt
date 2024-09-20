package pl.training.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper

class GitHubGrantedAuthoritiesMapper : GrantedAuthoritiesMapper {

    override fun mapAuthorities(authorities: Collection<GrantedAuthority>) =
        setOf(SimpleGrantedAuthority("ROLE_ADMIN"))

}
