package pl.training.payments.domain

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PaymentRepository {

    fun save(payment: Payment): Mono<Payment>

    fun findAll(): Flux<Payment>

    fun findById(id: String): Mono<Payment>

}