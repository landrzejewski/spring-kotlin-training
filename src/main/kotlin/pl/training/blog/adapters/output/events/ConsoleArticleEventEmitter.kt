package pl.training.blog.adapters.output.events

import org.springframework.stereotype.Component
import pl.training.blog.application.ArticleEvent
import pl.training.blog.application.output.ArticleEventEmitter

@Component
class ConsoleArticleEventEmitter : ArticleEventEmitter {

    override fun emit(articleEvent: ArticleEvent) {
        println("New article event: $articleEvent")
    }

}