package pl.training.security.jwt

class JwtPrincipal(
    val username: String,
    val roles: Set<String>
)
