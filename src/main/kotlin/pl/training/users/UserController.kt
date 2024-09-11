package pl.training.users

import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.notFound
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("api/users")
@RestController
class UserController(private val service: UserService) {

    @GetMapping("{id:\\d+}")
    suspend fun getById(@PathVariable id: Long): ResponseEntity<User> {
        val user = service.getById(id)
        return if (user == null) notFound().build() else ok(user)
    }

    @GetMapping
    suspend fun getAll() = service.getAll()

    @PostMapping
    suspend fun add(@RequestBody user: User) = service.add(user)

}