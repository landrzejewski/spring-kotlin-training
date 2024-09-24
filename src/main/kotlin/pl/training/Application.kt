package pl.training

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import pl.training.blog.application.ArticleTemplate
import pl.training.blog.application.input.ArticleAuthorActions
import pl.training.blog.application.input.ArticleSearch
import pl.training.blog.domain.ArticleCategory.IT


fun main() {
    AnnotationConfigApplicationContext(ApplicationConfiguration::class.java).use {
        val authorActions = it.getBean(ArticleAuthorActions::class.java)
        val search = it.getBean(ArticleSearch::class.java)
        val article = ArticleTemplate("Test", "Jan Kowalski", "", IT)
        val id = authorActions.create(article)
        println(search.findByUid(id))
    }

}
