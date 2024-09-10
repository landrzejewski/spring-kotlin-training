package pl.training.payments.utils.aop

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class Atomic(
    val timeoutInMilliseconds: Int = -1
)