package pl.training.payments.utils

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Lazy
import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Component

@Target(AnnotationTarget.CLASS)
@Qualifier
@Component
@Lazy
annotation class Mapper(
    @get:AliasFor(annotation = Component::class)
    val value: String = ""
)