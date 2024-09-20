package pl.training.security.jwt

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.Optional

@RestController
class JwtRestController(private val jwtService: JwtService) {

    @PostMapping("api/tokens")
    fun login(@RequestBody credentials: CredentialsDto): ResponseEntity<TokenDto> {
        val loginResult = login(credentials.username, credentials.password)
        return if (loginResult.isPresent) {
            val token = jwtService.createToken(credentials.username, loginResult.get())
            val tokenDto = TokenDto(token)
            ResponseEntity.ok(tokenDto)
        } else {
            ResponseEntity.status(401).build()
        }
    }

    private fun login(username: String, password: String): Optional<Set<String>> {
        return if (username == "jan" && password == "123") {
            Optional.of(setOf("ROLE_ADMIN"))
        } else {
            Optional.empty()
        }
    }

}
