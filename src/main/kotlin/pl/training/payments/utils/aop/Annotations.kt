package pl.training.payments.utils.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import java.lang.reflect.Method
import kotlin.reflect.KClass

object Annotations {

    fun <T : Annotation> getClassAnnotation(joinPoint: ProceedingJoinPoint, type: KClass<T>): T? {
        return joinPoint.target.javaClass.getAnnotation(type.java)
    }

    fun <T : Annotation> getMethodAnnotation(joinPoint: ProceedingJoinPoint, type: KClass<T>): T? {
        return getTargetMethod(joinPoint).getAnnotation(type.java)
    }

    @Throws(NoSuchMethodException::class)
    fun getTargetMethod(joinPoint: JoinPoint): Method {
        val signature = joinPoint.signature as MethodSignature
        val methodName = signature.method.name
        val parameterTypes = signature.method.parameterTypes
        return joinPoint.target.javaClass.getMethod(methodName, *parameterTypes)
    }

    @Throws(NoSuchMethodException::class)
    fun <T : Annotation> findAnnotation(joinPoint: ProceedingJoinPoint, type: KClass<T>): T? {
        return getMethodAnnotation(joinPoint, type) ?: getClassAnnotation(joinPoint, type)
    }

    fun <T : Annotation> findAnnotation(annotations: Array<Annotation>, type: KClass<T>): T? {
        return annotations.filterIsInstance(type.java).firstOrNull()
    }

    @Suppress("UNCHECKED_CAST")
    @Throws(NoSuchMethodException::class)
    fun <P, A : Annotation> applyArgumentOperator(
        joinPoint: JoinPoint,
        annotationType: KClass<A>,
        argumentOperator: ArgumentOperator<P, A>
    ) {
        val arguments = joinPoint.args
        val argumentsAnnotations = getTargetMethod(joinPoint).parameterAnnotations
        for (index in arguments.indices) {
            val argument = arguments[index] as P
            findAnnotation(argumentsAnnotations[index], annotationType)?.let { annotation ->
                argumentOperator.apply(argument, annotation)
            }
        }
    }

    fun interface ArgumentOperator<P, A : Annotation> {
        fun apply(parameterType: P, annotationType: A)
    }

}