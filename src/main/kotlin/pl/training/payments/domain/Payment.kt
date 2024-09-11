package pl.training.payments.domain

import java.math.BigDecimal

data class Payment(
    val id: String,
    val value: BigDecimal,
    val currency: String,
    val status: PaymentStatus
)