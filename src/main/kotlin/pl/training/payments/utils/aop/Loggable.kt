package pl.training.payments.utils.aop

import kotlin.annotation.AnnotationTarget.FUNCTION

@Target(FUNCTION)
annotation class Loggable()
