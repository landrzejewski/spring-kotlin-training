package pl.training.blog.domain

import java.time.Instant

data class Comment(
    val text: String,
    val created: Instant,
    val author: String
)
