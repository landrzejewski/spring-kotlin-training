package pl.training

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import pl.training.blog.application.ArticleAuthorActionsService
import pl.training.blog.application.ArticleReaderActionsService
import pl.training.blog.application.ArticleSearchService
import pl.training.blog.application.output.ArticleEventEmitter
import pl.training.blog.application.output.ArticleRepository

@EnableAspectJAutoProxy
@ComponentScan
@Configuration
open class ApplicationConfiguration {

    @Bean
    open fun articleAuthorActions(articleRepository: ArticleRepository) =
        ArticleAuthorActionsService(articleRepository)

    @Bean
    open fun articleReaderActions(articleRepository: ArticleRepository, articleEventEmitter: ArticleEventEmitter) =
        ArticleReaderActionsService(articleRepository, articleEventEmitter)

    @Bean
    open fun articleSearch(articleRepository: ArticleRepository) =
        ArticleSearchService(articleRepository)

}