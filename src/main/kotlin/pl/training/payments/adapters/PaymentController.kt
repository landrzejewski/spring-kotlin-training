package pl.training.payments.adapters

import pl.training.payments.domain.Payment
import pl.training.payments.domain.PaymentService
import pl.training.payments.domain.PaymentStatus
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RequestMapping("/api/payments")
@RestController
class PaymentController(private val paymentService: PaymentService) {

    @PostMapping
    fun addPayment(@RequestBody paymentDto: PaymentDto) =
        paymentService.addPayment(toDomain(paymentDto)).map(::toDto)

    @GetMapping
    fun getAllPayments() = paymentService.getAllPayments().map(::toDto)

    @GetMapping("/{id}")
    fun getPaymentStatus(@PathVariable id: String) = paymentService.getPaymentStatus(id).map { it.name }

    @GetMapping("/confirmed")
    fun getConfirmedPaymentsInCurrency(@RequestParam(defaultValue = "PLN", required = false) currency: String) =
        paymentService.getConfirmedPaymentsInCurrency(currency)

    private fun toDto(payment: Payment) =
        PaymentDto(payment.id, payment.value.toDouble(), payment.currency, payment.status.name)

    private fun toDomain(paymentDto: PaymentDto) =
        Payment(
            paymentDto.id,
            BigDecimal.valueOf(paymentDto.value),
            paymentDto.currency,
            PaymentStatus.valueOf(paymentDto.status)
        )

}