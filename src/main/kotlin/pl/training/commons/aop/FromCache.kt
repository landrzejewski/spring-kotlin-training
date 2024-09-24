package pl.training.commons.aop

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class FromCache(
    val value: String,
    val capacity: Int = 100
)
