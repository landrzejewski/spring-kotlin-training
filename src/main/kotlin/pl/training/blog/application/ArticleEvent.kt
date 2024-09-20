package pl.training.blog.application

import java.util.UUID

data class ArticleEvent(
    val type: String,
    val articleUuid: UUID
)
