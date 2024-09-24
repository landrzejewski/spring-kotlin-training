package pl.training.commons.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import pl.training.commons.annotations.Retry
import java.util.logging.Logger

@Aspect
@Component
class RetryAspect {

    private val log = Logger.getLogger(RetryAspect::class.java.name)

    @Around("@annotation(retry)")
    fun tryExecute(joinPoint: ProceedingJoinPoint, retry: Retry): Any? {
        var attempt = 0
        var throwable: Throwable
        do {
            attempt++
            try {
                return joinPoint.proceed()
            } catch (currentThrowable: Throwable) {
                throwable = currentThrowable
                log.info("Execution of method \"${joinPoint.signature}\" failed with exception: ${throwable::class.simpleName} (attempt: $attempt)")
            }
        } while (attempt < retry.attempts)
        throw throwable
    }

}