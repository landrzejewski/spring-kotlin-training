package pl.training.blog.adapters.input.rest

import java.time.Instant

class ArticleDto(
    val title: String,
    val author: String,
    val content: String,
    val category: String,
    val status: String,
    val created: Instant,
    val likes: Int,
    val dislikes: Int
)
