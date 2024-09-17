package pl.training.payments.commons.aop


import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component

@Aspect
@Component
class MinLengthAspect {

    @Before("execution(* *(@pl.training.payments.commons.annotations.MinLength (*)))")
    @Throws(NoSuchMethodException::class)
    fun validate(joinPoint: JoinPoint) {
        applyArgumentOperator(joinPoint, MinLength::class.java) { argument: String, minLength: MinLength ->
            if (argument.length < minLength.value) {
                throw IllegalArgumentException("Value is too short, minimum length is: ${minLength.value}")
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Annotation> applyArgumentOperator(
        joinPoint: JoinPoint, annotationClass: Class<T>, operator: (String, T) -> Unit
    ) {
        val method = joinPoint.signature.declaringType.getDeclaredMethod(
            joinPoint.signature.name,
            *joinPoint.args.map { it::class.java }.toTypedArray()
        )
        val annotations = method.parameterAnnotations

        joinPoint.args.forEachIndexed { index, argument ->
            val annotation = annotations[index].find { it.annotationClass == annotationClass.kotlin } as? T
            if (annotation != null && argument is String) {
                operator(argument, annotation)
            }
        }
    }

}