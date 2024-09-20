package pl.training.security

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class SecurityInitializer(
    private val userRepository: JpaUserRepository,
    private val passwordEncoder: PasswordEncoder
) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        if (userRepository.findByLogin("jan").isEmpty) {
            val user = UserEntity(
                id = null,
                login = "jan",
                secret = passwordEncoder.encode("123"),
                enabled = true,
                verified = true,
                roles = "ROLE_ADMIN,ROLE_USER"
            )
            userRepository.save(user)
        }
    }
}
