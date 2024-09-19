package pl.training.payments.utils.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import pl.training.payments.utils.annotations.Lock
import pl.training.payments.utils.annotations.Lock.LockType.WRITE
import java.util.concurrent.locks.ReentrantReadWriteLock

@Aspect
@Component
class LockAspect {

    @Around("@annotation(lock)")
    fun lock(joinPoint: ProceedingJoinPoint, lock: Lock): Any {
        val newLock = ReentrantReadWriteLock()
        val targetLock = if (lock.type == WRITE) newLock.writeLock() else newLock.readLock()
        targetLock.lock()
        return try {
            joinPoint.proceed()
        } finally {
            targetLock.unlock()
        }
    }
}