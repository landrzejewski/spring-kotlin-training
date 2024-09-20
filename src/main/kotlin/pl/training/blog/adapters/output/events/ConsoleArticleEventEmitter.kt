package pl.training.blog.adapters.output.events

import pl.training.blog.application.ArticleEvent
import pl.training.blog.application.output.ArticleEventEmitter

class ConsoleArticleEventEmitter : ArticleEventEmitter {

    override fun emit(articleEvent: ArticleEvent) {
        println("New article event: $articleEvent")
    }

}