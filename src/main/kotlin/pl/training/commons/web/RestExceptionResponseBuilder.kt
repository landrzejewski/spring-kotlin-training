package pl.training.commons.web

import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.Locale

@Component
class RestExceptionResponseBuilder(private val messageSource: MessageSource) {

    fun build(description: String, status: HttpStatus) =
        ResponseEntity.status(status).body(ExceptionDto(description))

    fun build(exception: Exception, status: HttpStatus, locale: Locale) =
        build(getLocalizedMessage(exception, locale), status)

    fun getLocalizedMessage(exception: Exception, locale: Locale, vararg params: String) =
        messageSource.getMessage(exception.javaClass.simpleName, params, DEFAULT_DESCRIPTION, locale) ?: DEFAULT_DESCRIPTION

    companion object {

        private const val DEFAULT_DESCRIPTION = "Unknown error"

    }

}
