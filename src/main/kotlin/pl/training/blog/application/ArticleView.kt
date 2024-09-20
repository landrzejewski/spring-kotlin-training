package pl.training.blog.application

import java.util.UUID

data class ArticleView(
    val id: UUID,
    val title: String,
    val author: String
)
