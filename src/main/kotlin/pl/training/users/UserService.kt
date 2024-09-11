package pl.training.users

import kotlinx.coroutines.flow.filter
import org.springframework.stereotype.Service

@Service
class UserService(private val repository: UserRepository) {

    suspend fun add(user: User)= repository.save(user)

    suspend fun getAll() = repository.findAll()
        .filter { it.email.endsWith("@training.com") }

    suspend fun getById(id: Long) = repository.findById(id)

}