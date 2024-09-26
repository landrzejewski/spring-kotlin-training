package pl.training

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import pl.training.blog.application.ArticleAuthorActionsService
import pl.training.blog.application.ArticleReaderActionsService
import pl.training.blog.application.ArticleSearchService
import pl.training.blog.application.output.ArticleEventEmitter
import pl.training.blog.application.output.ArticleRepository

@EnableWebSecurity(debug = true)
@Configuration
class ApplicationConfiguration {

    @Bean
    fun articleAuthorActions(articleRepository: ArticleRepository) =
        ArticleAuthorActionsService(articleRepository)

    @Bean
    fun articleReaderActions(articleRepository: ArticleRepository, articleEventEmitter: ArticleEventEmitter) =
        ArticleReaderActionsService(articleRepository, articleEventEmitter)

    @Bean
    fun articleSearch(articleRepository: ArticleRepository) =
        ArticleSearchService(articleRepository)

}