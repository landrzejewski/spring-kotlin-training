package pl.training.commons.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import pl.training.payments.domain.CardNumber
import pl.training.payments.domain.Money
import java.util.logging.Logger

@Order(10_000)
@Aspect
@Component
class CardOperationsLoggingAspect {

    private val log = Logger.getLogger(CardOperationsLoggingAspect::class.java.name)

    @Pointcut("@annotation(pl.training.commons.annotations.Loggable)")
    fun payment() {
    }

    @Before(value = "payment() && args(cardNumber,amount)", argNames = "cardNumber,amount")
    fun before(joinPoint: JoinPoint, cardNumber: CardNumber, amount: Money) {
        // val cardNumber = joinPoint.args[0] as String
        log.info("New payment request for card: $cardNumber")
    }

    @AfterReturning(value = "payment()")
    fun onSuccess() {
        log.info("Payment successful")
    }

    @AfterThrowing(value = "payment()", throwing = "runtimeException")
    fun onFailure(joinPoint: JoinPoint, runtimeException: RuntimeException) {
        log.info("Payment failed with exception: ${runtimeException.javaClass.simpleName} (method: ${joinPoint.signature})")
    }

    @After("payment()")
    fun afterCharge() {
        log.info("Payment processing complete")
    }

}