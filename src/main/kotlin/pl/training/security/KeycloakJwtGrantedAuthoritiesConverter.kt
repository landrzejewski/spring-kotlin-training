package pl.training.security

import org.springframework.core.convert.converter.Converter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt

class KeycloakJwtGrantedAuthoritiesConverter : Converter<Jwt, Collection<GrantedAuthority>> {

    override fun convert(jwt: Jwt) =
        jwt.getClaim<Map<String, List<String>>>(REALM_CLAIM)[ROLES_KEY]
            ?.map { SimpleGrantedAuthority("$ROLE_PREFIX$it") }
            .orEmpty()

    companion object {

        private const val REALM_CLAIM = "realm_access"
        private const val ROLES_KEY = "roles"
        private const val ROLE_PREFIX = "ROLE_"

    }

}
