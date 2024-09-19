package pl.training.commons.model.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.reflect.KClass

@Constraint(validatedBy = [MonetaryAmountValidator::class])
@Target(FIELD)
annotation class MonetaryAmount(

    val message: String = "{MonetaryAmount}",

    val groups: Array<KClass<*>> = [],

    val payload: Array<KClass<out Payload>> = [],

    val maxValue: Double = 10_000.0

)

