package pl.training

import pl.training.blog.adapters.output.persistene.HashMapArticleRepository
import pl.training.blog.application.ArticleAuthorActionsServie
import pl.training.blog.application.ArticleSearchService
import pl.training.blog.application.ArticleTemplate
import pl.training.blog.application.input.ArticleAuthorActions
import pl.training.blog.application.input.ArticleSearch
import pl.training.blog.application.output.ArticleRepository
import pl.training.blog.domain.ArticleCategory.IT


fun main() {
    val articleRepository: ArticleRepository = HashMapArticleRepository()
    val authorActions: ArticleAuthorActions = ArticleAuthorActionsServie(articleRepository)
    val search: ArticleSearch = ArticleSearchService(articleRepository)

    val article = ArticleTemplate("Test", "Jan Kowalski", "", IT)
    val id = authorActions.create(article)
    println(search.findByUid(id))
}
