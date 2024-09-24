package pl.training.blog.adapters.output.persistence.jpa

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import pl.training.blog.application.ArticleView
import pl.training.blog.application.output.ArticleRepository
import pl.training.blog.domain.Article
import pl.training.blog.domain.ArticleCategory
import pl.training.blog.domain.Tag
import pl.training.commons.model.PageSpec
import pl.training.commons.model.ResultPage
import java.util.*

@Primary
@Transactional
@Repository
class SpringDataRepositoryAdapter(
    private val articleRepository: SpringDataArticleRepository,
    private val mapper: SpringDataArticleMapper
) : ArticleRepository {

    override fun findById(id: UUID): Article? {
        return articleRepository.findById(id)
            .map(mapper::toDomain)
            .orElse(null)
    }

    override fun save(article: Article): Article {
        val articleEntity = mapper.toEntity(article)
        val savedArticleEntity = articleRepository.save(articleEntity)
        return mapper.toDomain(savedArticleEntity)
    }

    override fun findByCategory(category: ArticleCategory, pageSpec: PageSpec): ResultPage<ArticleView> {
        val page = mapper.toEntity(pageSpec)
        val categoryName = mapper.toEntity(category)
        val result = articleRepository.findByCategory(categoryName, page)
        return mapper.toDomain(result)
    }

    override fun findByTags(tags: Set<Tag>, pageSpec: PageSpec): ResultPage<ArticleView> {
        val page = mapper.toEntity(pageSpec)
        val tagNames = mapper.toEntity(tags)
        val result = articleRepository.findByTags(tagNames, tagNames.size, page)
        return mapper.toDomain(result)
    }

    override fun findByCategoryAndTags(category: ArticleCategory, tags: Set<Tag>, pageSpec: PageSpec): ResultPage<ArticleView> {
        val page = mapper.toEntity(pageSpec)
        val categoryName = mapper.toEntity(category)
        val tagNames = mapper.toEntity(tags)
        val result = articleRepository.findByCategoryAndTags(categoryName, tagNames, tagNames.size, page)
        return mapper.toDomain(result)
    }

    override fun deleteById(articleId: UUID) {
        articleRepository.deleteById(articleId)
    }

    override fun existsByTitle(title: String): Boolean {
        return articleRepository.existsByTitle(title)
    }

}
