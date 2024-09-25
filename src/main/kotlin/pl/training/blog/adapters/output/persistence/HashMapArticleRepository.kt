package pl.training.blog.adapters.output.persistence

import pl.training.commons.model.PageSpec
import pl.training.commons.model.ResultPage
import pl.training.blog.application.ArticleView
import pl.training.blog.application.output.ArticleRepository
import pl.training.blog.domain.Article
import pl.training.blog.domain.ArticleCategory
import pl.training.blog.domain.Tag
import java.util.UUID

class HashMapArticleRepository : ArticleRepository {

    private val articles = mutableMapOf<UUID, Article>()

    override fun findById(id: UUID) = articles[id]

    override fun findByCategory(category: ArticleCategory, pageSpec: PageSpec) =
        ResultPage(emptyList<ArticleView>(), pageSpec, 0)

    override fun findByTags(tags: Set<Tag>, pageSpec: PageSpec) =
        ResultPage(emptyList<ArticleView>(), pageSpec, 0)

    override fun findByCategoryAndTags(category: ArticleCategory, tags: Set<Tag>, pageSpec: PageSpec) =
        ResultPage(emptyList<ArticleView>(), pageSpec, 0)

    override fun save(article: Article): Article {
        articles[article.id] = article
        return article
    }

    override fun deleteById(articleId: UUID) {
        articles.remove(articleId)
    }

    override fun existsByTitle(title: String) = false

}
