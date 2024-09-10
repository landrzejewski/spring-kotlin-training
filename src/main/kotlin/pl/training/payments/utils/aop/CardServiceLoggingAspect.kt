package pl.training.payments.utils.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.*
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import pl.training.payments.domain.CardNumber
import pl.training.payments.domain.Money
import java.util.logging.Logger

@Order(10_000)
@Aspect
@Component
class CardServiceLoggingAspect {

    private val log: Logger = Logger.getLogger(CardServiceLoggingAspect::class.java.name)

    // @Pointcut("execution(* pl.training.pay*.*Service.char*(..))")
    // @Pointcut("execution(void pl.training.payments.application.CardsService.charge(pl.training.payments.domain.CardNumber, pl.training.payments.domain.Money))")
    @Pointcut("@annotation(pl.training.payments.utils.aop.Loggable)")
    // @Pointcut("bean(cards)")
    fun charge() {
    }

    @Before(value = "charge() && args(cardNumber,money)", argNames = "cardNumber,money")
    fun beforeCharge(joinPoint: JoinPoint, cardNumber: CardNumber, money: Money) {
        // val cardNumber = joinPoint.args[0] as String
        log.info("New charge request for card: $cardNumber")
    }

    @AfterReturning(value = "charge()")
    fun onSuccess() {
        log.info("Charge successful")
    }

    @AfterThrowing(value = "charge()", throwing = "runtimeException")
    fun onFailure(joinPoint: JoinPoint, runtimeException: RuntimeException) {
        log.info("Charge failed with exception: ${runtimeException.javaClass.simpleName} (method: ${joinPoint.signature})")
    }

    @After("charge()")
    fun afterCharge() {
        log.info("Charge processing complete")
    }

}