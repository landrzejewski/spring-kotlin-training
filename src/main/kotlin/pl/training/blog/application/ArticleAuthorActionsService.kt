package pl.training.blog.application

import pl.training.blog.application.input.ArticleAuthorActions
import pl.training.blog.application.output.ArticleRepository
import pl.training.blog.domain.Article
import pl.training.blog.domain.ArticleUpdate
import java.util.UUID

class ArticleAuthorActionsService(private val articleRepository: ArticleRepository) : ArticleAuthorActions {

    override fun create(articleTemplate: ArticleTemplate): UUID {
        val article = Article(
            title = articleTemplate.title,
            author = articleTemplate.author,
            content = articleTemplate.content,
            category = articleTemplate.category
        )
        return articleRepository.save(article).id
    }

    override fun delete(articleId: UUID) = articleRepository.deleteById(articleId)

    override fun update(articleId: UUID, articleUpdate: ArticleUpdate) =
        articleRepository.apply(articleId) { it.patch(articleUpdate) }

    override fun publish(articleId: UUID) =
        articleRepository.apply(articleId) { it.publish() }

}
