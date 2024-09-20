package pl.training.blog.application.input

import pl.training.blog.domain.Comment
import java.util.UUID

interface ArticleReaderActions {

    fun like(articleId: UUID)

    fun dislike(articleId: UUID)

    fun comment(articleId: UUID, comment: Comment)

}
