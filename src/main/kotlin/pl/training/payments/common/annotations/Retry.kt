package pl.training.payments.common.annotations

@Target(AnnotationTarget.FUNCTION)
annotation class Retry(
    val attempts: Int = 3
)