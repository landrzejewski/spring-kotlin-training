package pl.training.payments.application

data class CardTransactionEvent(val cardNumber: String, val type: String)
