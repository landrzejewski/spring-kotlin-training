package pl.training.blog.application

import pl.training.blog.domain.ArticleCategory

data class ArticleTemplate(
    val title: String,
    val author: String,
    val content: String,
    val category: ArticleCategory
)
