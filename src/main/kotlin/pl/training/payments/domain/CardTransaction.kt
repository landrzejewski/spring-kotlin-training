package pl.training.payments.domain

import pl.training.payments.domain.CardTransactionType.PAYMENT
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.Currency

data class CardTransaction(val timestamp: ZonedDateTime, val money: Money, val type: CardTransactionType) {

    fun hasCurrency(currency: Currency) = money.currency == currency

    fun isBefore(localDate: LocalDate) = timestamp.toLocalDate().isBefore(localDate)

    fun isPayment() = type == PAYMENT

}
