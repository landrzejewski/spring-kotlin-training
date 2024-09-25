package pl.training.blog.adapters.input.rest

import jakarta.validation.constraints.Pattern

class ArticleTemplateDto(
    @field:Pattern(regexp = "\\w{3,}")
    val title: String,
    val author: String,
    val content: String,
    val category: String
)
