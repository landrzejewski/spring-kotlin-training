package pl.training.blog.application.output

import pl.training.blog.application.ArticleEvent

interface ArticleEventEmitter {

    fun emit(articleEvent: ArticleEvent)

}
