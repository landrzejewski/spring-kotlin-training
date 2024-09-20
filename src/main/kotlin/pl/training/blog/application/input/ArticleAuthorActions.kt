package pl.training.blog.application.input

import pl.training.blog.application.ArticleTemplate
import pl.training.blog.domain.ArticleUpdate
import java.util.UUID

interface ArticleAuthorActions {

    fun create(articleTemplate: ArticleTemplate): UUID

    fun delete(articleId: UUID)

    fun update(articleId: UUID, articleUpdate: ArticleUpdate)

    fun publish(articleId: UUID)

}
