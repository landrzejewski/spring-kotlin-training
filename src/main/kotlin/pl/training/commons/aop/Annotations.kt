package pl.training.commons.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import java.lang.reflect.Method

object Annotations {

    fun <T : Annotation> getClassAnnotation(joinPoint: ProceedingJoinPoint, type: Class<T>): T? {
        return joinPoint.target.javaClass.getAnnotation(type)
    }

    fun <T : Annotation> getMethodAnnotation(joinPoint: ProceedingJoinPoint, type: Class<T>): T? {
        return getTargetMethod(joinPoint).getAnnotation(type)
    }

    fun getTargetMethod(joinPoint: JoinPoint): Method {
        val signature = joinPoint.signature as MethodSignature
        val methodName = signature.method.name
        val parameterTypes = signature.method.parameterTypes
        return joinPoint.target.javaClass.getMethod(methodName, *parameterTypes)
    }

    fun <T : Annotation> findAnnotation(joinPoint: ProceedingJoinPoint, type: Class<T>): T? {
        return getMethodAnnotation(joinPoint, type) ?: getClassAnnotation(joinPoint, type)
    }

    fun <T : Annotation> findAnnotation(annotations: Array<Annotation>, type: Class<T>): T? {
        return annotations.filterIsInstance(type).firstOrNull()
    }

    @Suppress("UNCHECKED_CAST")
    fun <P, A : Annotation> applyArgumentOperator(
        joinPoint: JoinPoint,
        annotationType: Class<A>,
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