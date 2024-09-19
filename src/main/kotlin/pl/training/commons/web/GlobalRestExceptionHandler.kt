package pl.training.commons.web

import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import java.util.Locale

@ControllerAdvice(annotations = [RestController::class])
class GlobalRestExceptionHandler(private val exceptionResponseBuilder: RestExceptionResponseBuilder) {

    @ExceptionHandler(Exception::class)
    fun onException(exception: Exception, locale: Locale) =
        exceptionResponseBuilder.build(exception, INTERNAL_SERVER_ERROR, locale)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun onMethodArgumentNotValid(
        exception: MethodArgumentNotValidException,
        locale: Locale
    ): ResponseEntity<ExceptionDto> {
        val description = exceptionResponseBuilder.getLocalizedMessage(exception, locale, getValidationErrors(exception))
        return exceptionResponseBuilder.build(description, BAD_REQUEST)
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
