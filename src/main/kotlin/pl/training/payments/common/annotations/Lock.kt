package pl.training.payments.common.annotations

import kotlin.annotation.AnnotationTarget.FUNCTION

@Target(FUNCTION)
annotation class Lock(val type: LockType = LockType.WRITE) {
    enum class LockType {
        READ, WRITE
    }
}
