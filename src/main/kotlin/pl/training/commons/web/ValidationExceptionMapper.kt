package pl.training.commons.web

import org.springframework.stereotype.Component
import org.springframework.web.bind.MethodArgumentNotValidException

@Component
class ValidationExceptionMapper {

    fun toValidationErrors(exception: MethodArgumentNotValidException) =
        exception.bindingResult.fieldErrors.joinToString(DELIMITER) {
            "${it.field}$KEY_VALUE_SEPARATOR${it.defaultMessage}"
        }

    companion object {
        const val KEY_VALUE_SEPARATOR = ": "
        const val DELIMITER = ", "
    }

}
