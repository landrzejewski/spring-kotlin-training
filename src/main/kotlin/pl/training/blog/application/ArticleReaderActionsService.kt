package pl.training.blog.application

import pl.training.blog.application.input.ArticleReaderActions
import pl.training.blog.application.output.ArticleEventEmitter
import pl.training.blog.application.output.ArticleRepository
import pl.training.blog.domain.Comment
import java.util.UUID

class ArticleReaderActionsService(
    private val articleRepository: ArticleRepository,
    private val eventsEmitter: ArticleEventEmitter
) : ArticleReaderActions {

    override fun like(articleId: UUID) {
        articleRepository.apply(articleId) { it.like() }
        eventsEmitter.emit(ArticleEvent(LIKE_EVENT, articleId))
    }

    override fun dislike(articleId: UUID) =
        articleRepository.apply(articleId) { it.dislike() }

    override fun comment(articleId: UUID, comment: Comment) =
        articleRepository.apply(articleId) {
            it.addComment(comment)
            it
        }

    companion object {

        private const val LIKE_EVENT = "LIKE"

    }

}
