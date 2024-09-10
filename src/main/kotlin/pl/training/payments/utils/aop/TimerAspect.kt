package pl.training.payments.utils.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Aspect
@Component
@Order(100_000)
class TimerAspect : Ordered {

    private val log: Logger = Logger.getLogger(TimerAspect::class.java.name)

    @Around("@annotation(timer)")
    @Throws(Throwable::class)
    fun measure(joinPoint: ProceedingJoinPoint, timer: Timer): Any? {
        val timeUnit = timer.timeUnit
        val startTime = getTime(timeUnit)
        val result = joinPoint.proceed()
        val endTime = getTime(timeUnit)
        val totalTime = endTime - startTime
        log.info("Method ${joinPoint.signature} executes in $totalTime ${timeUnit.name.lowercase()}")
        return result
    }

    private fun getTime(timeUnit: Timer.TimeUnit): Long {
        return if (timeUnit == Timer.TimeUnit.NS) System.nanoTime() else System.currentTimeMillis()
    }

    override fun getOrder(): Int {
        return 100
    }

}
