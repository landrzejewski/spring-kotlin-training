package pl.training.commons.annotations

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class Atomic(
    val timeoutInMilliseconds: Int = -1
)