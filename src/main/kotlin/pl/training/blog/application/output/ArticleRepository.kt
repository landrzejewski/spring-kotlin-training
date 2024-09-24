package pl.training.blog.application.output

import pl.training.commons.model.PageSpec
import pl.training.commons.model.ResultPage
import pl.training.blog.application.ArticleNotFoundException
import pl.training.blog.application.ArticleView
import pl.training.blog.domain.Article
import pl.training.blog.domain.ArticleCategory
import pl.training.blog.domain.Tag
import java.util.UUID

interface ArticleRepository {

    fun findById(id: UUID): Article?

    fun findByCategory(category: ArticleCategory, pageSpec: PageSpec): ResultPage<ArticleView>

    fun findByTags(tags: Set<Tag>, pageSpec: PageSpec): ResultPage<ArticleView>

    fun findByCategoryAndTags(category: ArticleCategory, tags: Set<Tag>, pageSpec: PageSpec): ResultPage<ArticleView>

    fun save(article: Article): Article

    fun deleteById(articleId: UUID)

    fun existsByTitle(title: String): Boolean

    fun apply(articleId: UUID, operation: (Article) -> Article) {
        val article = findById(articleId) ?: throw ArticleNotFoundException()
        save(operation(article))
    }

}
