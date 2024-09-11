package pl.training.payments.persistence.mongo

import org.springframework.stereotype.Repository
import pl.training.payments.domain.Payment
import pl.training.payments.domain.PaymentRepository
import pl.training.payments.domain.PaymentStatus

@Repository
class ReactiveMongoPaymentRepositoryAdapter(private val repository: ReactiveMongoPaymentRepository) :
    PaymentRepository {

    override fun save(payment: Payment) = repository
        .save(toDocument(payment))
        .map(::toDomain)

    override fun findAll() = repository
        .findAll()
        .map(::toDomain)

    override fun findById(id: String) = repository.findById(id)
        .map(::toDomain)

    private fun toDocument(payment: Payment) =
        PaymentDocument(payment.id, payment.value, payment.currency, payment.status.name)

    private fun toDomain(paymentDocument: PaymentDocument) =
        Payment(
            paymentDocument.id,
            paymentDocument.value,
            paymentDocument.currency,
            PaymentStatus.valueOf(paymentDocument.status)
        )

}