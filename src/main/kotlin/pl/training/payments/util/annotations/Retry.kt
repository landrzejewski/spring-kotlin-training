package pl.training.payments.util.annotations

@Target(AnnotationTarget.FUNCTION)
annotation class Retry(
    val attempts: Int = 3
)