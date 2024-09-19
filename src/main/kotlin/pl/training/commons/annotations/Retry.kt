package pl.training.commons.annotations

@Target(AnnotationTarget.FUNCTION)
annotation class Retry(
    val attempts: Int = 3
)