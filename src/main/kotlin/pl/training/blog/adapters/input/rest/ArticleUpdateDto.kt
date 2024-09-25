package pl.training.blog.adapters.input.rest

class ArticleUpdateDto(
    val title: String,
    val content: String,
    val tags: Set<String>
)
