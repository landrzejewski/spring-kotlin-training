package pl.training.security

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.security.core.CredentialsContainer
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity(name = "User")
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue
    val id: Long? = null,
    val login: String,
    var secret: String,
    val enabled: Boolean,
    val verified: Boolean,
    val roles: String
) : UserDetails, CredentialsContainer {

    override fun getAuthorities() =
        roles.split(ROLE_SEPARATOR)
            .map { SimpleGrantedAuthority(it.trim()) }
            .toSet()

    override fun getUsername() = login

    override fun getPassword() = secret

    override fun isEnabled() = enabled

    override fun isAccountNonExpired() = enabled

    override fun isAccountNonLocked() = enabled

    override fun isCredentialsNonExpired() = enabled

    override fun eraseCredentials() {
        secret = ""
    }

    companion object {
        private const val ROLE_SEPARATOR = ","
    }

}
