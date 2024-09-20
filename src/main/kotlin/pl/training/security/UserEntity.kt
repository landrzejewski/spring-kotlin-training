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
    private val id: Long? = null,
    private var username: String,
    private var password: String,
    private var enabled: Boolean,
    private var verified: Boolean,
    private var roles: String
) : UserDetails, CredentialsContainer {

    override fun getAuthorities() =
        roles.split(ROLE_SEPARATOR)
            .map { SimpleGrantedAuthority(it.trim()) }
            .toSet()

    override fun getUsername() = username

    override fun getPassword() = password

    override fun isEnabled() = enabled

    override fun isAccountNonExpired() = enabled

    override fun isAccountNonLocked() = enabled

    override fun isCredentialsNonExpired() = enabled

    override fun eraseCredentials() {
        password = ""
    }

    companion object {
        private const val ROLE_SEPARATOR = ","
    }

}
