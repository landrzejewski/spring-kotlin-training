package pl.training.payments.utils.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component
import pl.training.payments.utils.annotations.MinLength
import pl.training.payments.utils.aop.Annotations.applyArgumentOperator

@Aspect
@Component
class MinLengthAspect {

    @Before("execution(* *(@pl.training.payments.utils.annotations.MinLength (*)))")
    fun validate(joinPoint: JoinPoint) {
        applyArgumentOperator(joinPoint, MinLength::class.java) { argument: String, minLength: MinLength ->
            if (argument.length < minLength.value) {
                throw IllegalArgumentException("Value is too short, minimum length is: ${minLength.value}")
            }
        }
    }

}
