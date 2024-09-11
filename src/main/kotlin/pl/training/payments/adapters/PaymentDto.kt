package pl.training.payments.adapters

data class PaymentDto(
    val id: String,
    val value: Double,
    val currency: String,
    val status: String
)