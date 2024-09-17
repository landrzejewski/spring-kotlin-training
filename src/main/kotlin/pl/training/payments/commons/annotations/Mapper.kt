package pl.training.payments.commons.annotations

import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Component

@Target(AnnotationTarget.CLASS)
@Component
annotation class Mapper(
    @get:AliasFor(annotation = Component::class)
    val value: String = ""
)
