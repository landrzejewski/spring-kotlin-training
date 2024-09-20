package pl.training.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RequestMapping("api/users/me")
@RestController
class UserRestController {

    @GetMapping
    fun getCurrentUser(authentication: Authentication, principal: Principal): UserEntity {
        val userAuth = SecurityContextHolder.getContext().authentication
        return userAuth.principal as UserEntity
    }

}
