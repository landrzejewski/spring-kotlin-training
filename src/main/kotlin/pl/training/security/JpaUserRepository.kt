package pl.training.security

import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface JpaUserRepository : CrudRepository<UserEntity, Long> {

    fun findByLogin(username: String): Optional<UserEntity>

}
