package pl.training.commons.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import pl.training.commons.annotations.Timer
import pl.training.commons.annotations.Timer.TimeUnit
import java.util.logging.Logger

@Aspect
@Component
@Order(100_000)
class TimerAspect : Ordered {

    private val log = Logger.getLogger(TimerAspect::class.java.name)

    @Around("@annotation(timer)")
    fun measure(joinPoint: ProceedingJoinPoint, timer: Timer): Any? {
        val timeUnit = timer.timeUnit
        val startTime = getTime(timeUnit)
        val result = joinPoint.proceed()
        val endTime = getTime(timeUnit)
        val totalTime = endTime - startTime
        log.info("Execution time for method ${joinPoint.signature}: $totalTime ${timeUnit.name.lowercase()}")
        return result
    }

    private fun getTime(timeUnit: TimeUnit): Long {
        return if (timeUnit == TimeUnit.NS) System.nanoTime() else System.currentTimeMillis()
    }

    override fun getOrder(): Int {
        return 100
    }

}