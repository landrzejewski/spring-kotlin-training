package pl.training.blog.adapters.output.persistence.jpa

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import pl.training.blog.application.ArticleView
import pl.training.blog.domain.Article
import pl.training.blog.domain.ArticleCategory
import pl.training.blog.domain.Tag
import pl.training.commons.model.PageSpec
import pl.training.commons.model.ResultPage

@Mapper(componentModel = "spring")
abstract class SpringDataArticleMapper {

    abstract fun toDomain(articleEntity: ArticleEntity): Article

    abstract fun toEntity(article: Article): ArticleEntity

    fun toEntity(category: ArticleCategory) = category.name

    fun toEntity(tags: Set<Tag>) = tags.map { it.name }.toSet()

    fun toEntity(pageSpec: PageSpec) = PageRequest.of(pageSpec.index, pageSpec.size)

    fun toDomain(page: Page<ArticleView>) =
        ResultPage(page.content, PageSpec(page.number, page.size), page.totalPages)

}
