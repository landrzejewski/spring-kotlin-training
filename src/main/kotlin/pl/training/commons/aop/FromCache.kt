package pl.training.commons.aop

@Target(AnnotationTarget.FUNCTION)
annotation class FromCache(
    val value: String,
    val capacity: Int = 100
)
