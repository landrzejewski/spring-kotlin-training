package pl.training.payments.utils.aop

@Target(AnnotationTarget.FUNCTION)
annotation class Retry(
    val attempts: Int = 3
)
