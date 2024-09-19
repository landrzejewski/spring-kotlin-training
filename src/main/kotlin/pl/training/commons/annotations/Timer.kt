package pl.training.commons.annotations

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Timer(val timeUnit: TimeUnit = TimeUnit.NS) {
    enum class TimeUnit {
        NS, MS
    }
}