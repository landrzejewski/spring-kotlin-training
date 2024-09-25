package pl.training.commons.web

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import pl.training.blog.application.ArticleNotFoundException

@ControllerAdvice(annotations = [RestController::class])
class GlobalExceptionHandler(
    private val exceptionMapper: ValidationExceptionMapper
) {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun onMethodArgumentNotValidException(exception: MethodArgumentNotValidException): ResponseEntity<ExceptionDto> {
        val validationErrors = exceptionMapper.toValidationErrors(exception)
        return ResponseEntity.badRequest().body(ExceptionDto(description = validationErrors))
    }

    @ExceptionHandler(ArticleNotFoundException::class)
    fun onArticleNotFoundException(exception: ArticleNotFoundException): ResponseEntity<ExceptionDto> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionDto(description = "Article not found"))
    }

}
