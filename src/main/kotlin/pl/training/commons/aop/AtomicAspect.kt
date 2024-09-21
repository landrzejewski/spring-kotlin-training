package pl.training.commons.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.support.DefaultTransactionDefinition
import pl.training.commons.annotations.Atomic

@Aspect
@Component
class AtomicAspect(
    private val platformTransactionManager: PlatformTransactionManager
) {

    @Around("@annotation(pl.training.commons.annotations.Atomic) || within(@pl.training.commons.annotations.Atomic *)")
    fun runWithTransaction(joinPoint: ProceedingJoinPoint): Any? {
        val annotation = Annotations.findAnnotation(joinPoint, Atomic::class.java)
        val transactionDefinition = if (annotation != null) transactionDefinition(annotation) else DefaultTransactionDefinition()
        val transactionStatus = platformTransactionManager.getTransaction(transactionDefinition)
        return try {
            val result = joinPoint.proceed()
            platformTransactionManager.commit(transactionStatus)
            result
        } catch (throwable: Throwable) {
            transactionStatus.setRollbackOnly()
            throw throwable
        }
    }

    private fun transactionDefinition(atomic: Atomic): TransactionDefinition {
        val transactionDefinition = DefaultTransactionDefinition()
        transactionDefinition.timeout = atomic.timeoutInMilliseconds
        return transactionDefinition
    }

}