package pl.training.payments.utils.aop

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class MinLength(
    val value: Int = 5
)
