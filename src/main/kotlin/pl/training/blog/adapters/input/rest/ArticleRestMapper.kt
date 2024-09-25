package pl.training.blog.adapters.input.rest

import org.mapstruct.Mapper
import pl.training.blog.application.ArticleTemplate
import pl.training.blog.application.ArticleView
import pl.training.blog.domain.Article
import pl.training.blog.domain.ArticleCategory
import pl.training.blog.domain.ArticleUpdate
import pl.training.blog.domain.Tag
import pl.training.commons.model.ResultPage

@Mapper(componentModel = "spring")
abstract class ArticleRestMapper {

    abstract fun toDomain(articleTemplateDto: ArticleTemplateDto): ArticleTemplate

    abstract fun toDomain(articleUpdateDto: ArticleUpdateDto): ArticleUpdate

    abstract fun toDto(article: Article): ArticleDto

    abstract fun toDomainCategory(categoryName: String): ArticleCategory

    abstract fun toDto(articleViewResultPage: ResultPage<ArticleView>): ResultPage<ArticleViewDto>

    fun toDomain(tags: Set<String>) = tags.map { Tag(it) }.toSet()

}
