package pl.training.blog.application.input

import pl.training.commons.model.PageSpec
import pl.training.commons.model.ResultPage
import pl.training.blog.application.ArticleView
import pl.training.blog.domain.Article
import pl.training.blog.domain.ArticleCategory
import pl.training.blog.domain.Tag
import java.util.UUID

interface ArticleSearch {

    fun findByUid(id: UUID): Article

    fun findByCategory(category: ArticleCategory, pageSpec: PageSpec): ResultPage<ArticleView>

    fun findByTags(tags: Set<Tag>, pageSpec: PageSpec): ResultPage<ArticleView>

    fun findByCategoryAndTags(
        category: ArticleCategory,
        tags: Set<Tag>,
        pageSpec: PageSpec
    ): ResultPage<ArticleView>

}
