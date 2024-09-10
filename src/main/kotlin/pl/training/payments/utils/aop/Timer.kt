package pl.training.payments.utils.aop

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Timer(
    val timeUnit: TimeUnit = TimeUnit.NS
) {
    enum class TimeUnit {
        NS, MS
    }
}