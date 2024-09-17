package pl.training.payments.util.annotations

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component

@Target(AnnotationTarget.CLASS)
@Qualifier
@Lazy
@Component
annotation class LazyGenerator()
