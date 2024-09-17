package pl.training.payments.utils.annotations

@Target(AnnotationTarget.FUNCTION)
annotation class Retry(
    val attempts: Int = 3
)