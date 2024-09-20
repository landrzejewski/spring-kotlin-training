package pl.training.blog.domain

import pl.training.blog.domain.ArticleStatus.DRAFT
import pl.training.blog.domain.ArticleStatus.PUBLISHED
import java.time.Instant
import java.util.*
import kotlin.collections.emptySet

data class Article(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val author: String,
    val content: String,
    val category: ArticleCategory,
    val status: ArticleStatus = DRAFT,
    val tags: Set<Tag> = emptySet(),
    val created: Instant = Instant.now(),
    val published: Instant? = null,
    val likes: Int = 0,
    val dislikes: Int = 0,
    val comments: MutableList<Comment> = ArrayList()
) {

    fun like() = copy(likes = likes + 1)

    fun dislike() = copy(dislikes = dislikes + 1)

    fun addComment(comment: Comment) {
        comments.add(comment)
    }

    fun publish() =
        if (status != PUBLISHED) {
            copy(
                status = PUBLISHED,
                published = Instant.now()
            )
        } else
            this

    fun patch(update: ArticleUpdate) = copy(
        title = update.title ?: title,
        content = update.content ?: content,
        tags = if (update.tags.isNotEmpty()) update.tags else emptySet(),
    )

}
