package pl.training.commons.model.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.math.BigDecimal

class MonetaryAmountValidator : ConstraintValidator<MonetaryAmount, BigDecimal> {

    private var maxValue = 0.0

    override fun initialize(constraintAnnotation: MonetaryAmount) {
        maxValue = constraintAnnotation.maxValue
    }

    override fun isValid(value: BigDecimal, context: ConstraintValidatorContext) =
        try {
            with(value.toDouble()) {
                this >= 0.0 && this <= maxValue
            }
        } catch (_: NumberFormatException) {
            false
        }

}