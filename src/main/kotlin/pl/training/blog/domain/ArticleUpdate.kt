package pl.training.blog.domain

data class ArticleUpdate(
    val title: String?,
    val content: String?,
    val tags: Set<Tag> = emptySet()
)
