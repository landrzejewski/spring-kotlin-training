package pl.training.security

import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface JpaUserRepository : CrudRepository<UserEntity, Long> {

    fun findByUsername(username: String): Optional<UserEntity>

}
