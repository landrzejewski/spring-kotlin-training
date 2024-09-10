package pl.training.payments.infrastructure.input.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import pl.training.payments.application.CardNotFoundException

@ControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(CardNotFoundException::class)
    fun onCardNotFoundException(exception: CardNotFoundException) = ResponseEntity.notFound()

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun onValidationException(exception: MethodArgumentNotValidException): ResponseEntity<String> {
        var validationErrors = getValidationErrors(exception)
        return ResponseEntity.badRequest().body(validationErrors)
    }

    private fun getValidationErrors(methodArgumentNotValidException: MethodArgumentNotValidException): String {
        return methodArgumentNotValidException.bindingResult
            .fieldErrors
            .joinToString(DELIMITER) { fieldError ->
                "${fieldError.field}$KEY_VALUE_SEPARATOR${fieldError.defaultMessage}"
            }
    }

    companion object {
        const val KEY_VALUE_SEPARATOR = " "
        const val DELIMITER = ", "
    }

}
