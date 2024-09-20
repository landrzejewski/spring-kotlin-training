package pl.training.security

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class JpaUserDetailsService(private val userRepository: JpaUserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String) =
        userRepository.findByUsername(username)
            .orElseThrow { UsernameNotFoundException(username) }

}
