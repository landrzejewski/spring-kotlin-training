package pl.training.payments.utils.annotations

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Timer(val timeUnit: TimeUnit = TimeUnit.NS) {
    enum class TimeUnit {
        NS, MS
    }
}