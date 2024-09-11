package pl.training.payments.domain

import org.springframework.stereotype.Service
import pl.training.payments.domain.PaymentStatus.CONFIRMED
import reactor.core.publisher.Flux
import java.math.BigDecimal
import java.util.Random
import java.util.UUID

@Service
class PaymentService(private val repository: PaymentRepository) {

    fun addPayment(payment: Payment) = repository.save(
        payment.copy(
            id = generateId(),
            status = CONFIRMED
        )
    )

    fun getAllPayments() = repository.findAll()

    fun getPaymentStatus(paymentId: String) = repository.findById(paymentId)
        .map(Payment::status)

    fun getConfirmedPaymentsInCurrency(currency: String) = Flux.zip(getAllPayments(), exchangeRate())
        .map { it.t1.copy(value = it.t1.value, currency = currency) }
        .filter { it.status == CONFIRMED }

    private fun exchangeRate() = Flux.generate { it ->
        it.next(
            BigDecimal.valueOf(RANDOM.nextDouble(0.0, 0.1) * if (RANDOM.nextBoolean()) 1 else -1) + BASE_EXCHANGE_RATE
        )
    }

    private fun generateId() = UUID.randomUUID().toString()

    companion object {

        val BASE_EXCHANGE_RATE = BigDecimal.valueOf(4.3)
        val RANDOM = Random()

    }

}