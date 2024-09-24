package pl.training

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import pl.training.blog.application.ArticleSearchService
import pl.training.blog.application.ArticleTemplate
import pl.training.blog.application.input.ArticleAuthorActions
import pl.training.blog.application.input.ArticleReaderActions
import pl.training.blog.domain.ArticleCategory.IT
import pl.training.blog.domain.ArticleUpdate
import pl.training.blog.domain.Tag
import pl.training.commons.model.PageSpec


@SpringBootApplication
class Application(
    private val authorActions: ArticleAuthorActions,
    private val readerActions: ArticleReaderActions,
    private val search: ArticleSearchService
) :
    ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        val article = ArticleTemplate("Test", "Jan Kowalski", "", IT)
        val id = authorActions.create(article)
        readerActions.like(id)
        val tags = setOf(Tag("training"))
        val articleUpdate = ArticleUpdate(null, null, tags)
        authorActions.update(id, articleUpdate)
        println(search.findByUid(id).toString())
        println(search.findByCategory(IT, PageSpec(0, 10)).toString())
        println(search.findByTags(tags, PageSpec(0, 10)).toString())
    }
}

fun main() {
    runApplication<Application>()
}
