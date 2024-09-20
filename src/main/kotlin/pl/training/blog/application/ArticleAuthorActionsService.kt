package pl.training.blog.application

import pl.training.blog.application.input.ArticleAuthorActions
import pl.training.blog.application.output.ArticleRepository
import pl.training.blog.domain.Article
import pl.training.blog.domain.ArticleUpdate
import java.util.UUID

class ArticleAuthorActionsServie(private val articleRepository: ArticleRepository) : ArticleAuthorActions {

    override fun create(template: ArticleTemplate): UUID {
        val article = Article(
            title = template.title,
            author = template.author,
            content = template.content,
            category = template.category
        )
        return articleRepository.save(article).id
    }

    override fun delete(articleId: UUID) = articleRepository.deleteById(articleId)

    override fun update(articleId: UUID, articleUpdate: ArticleUpdate) =
        articleRepository.apply(articleId) { it.patch(articleUpdate) }

    override fun publish(articleId: UUID) =
        articleRepository.apply(articleId) { it.publish() }

}
