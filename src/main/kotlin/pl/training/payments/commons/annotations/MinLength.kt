package pl.training.payments.commons.annotations

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class MinLength(
    val value: Int = 5
)