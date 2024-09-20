package pl.training.blog.application

import pl.training.commons.model.PageSpec
import pl.training.blog.application.input.ArticleSearch
import pl.training.blog.application.output.ArticleRepository
import pl.training.blog.domain.ArticleCategory
import pl.training.blog.domain.Tag
import java.util.UUID

class ArticleSearchService(private val articleRepository: ArticleRepository) : ArticleSearch {

    override fun findByUid(id: UUID) = articleRepository.findById(id) ?: throw ArticleNotFoundException()

    override fun findByCategory(category: ArticleCategory, pageSpec: PageSpec) =
        articleRepository.findByCategory(category, pageSpec)

    override fun findByTags(tags: java.util.Set<Tag>, pageSpec: PageSpec) =
        articleRepository.findByTags(tags, pageSpec)

    override fun findByCategoryAndTags(category: ArticleCategory, tags: java.util.Set<Tag>, pageSpec: PageSpec) =
        if (tags.isEmpty()) {
            findByCategory(category, pageSpec)
        } else {
            articleRepository.findByCategoryAndTags(category, tags, pageSpec)
        }

}