package pl.training.payments.domain

data class CardTransactionRegistered(val number: CardNumber, val transaction: CardTransaction)
