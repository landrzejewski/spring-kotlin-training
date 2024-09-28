package pl.training.payments.domain

import pl.training.payments.domain.CardTransactionType.INFLOW
import pl.training.payments.domain.CardTransactionType.PAYMENT
import java.math.BigDecimal.ZERO
import java.time.LocalDate
import java.util.Currency
import java.util.function.Consumer

class Card(
    val id: CardId,
    val number: CardNumber,
    val expiration: LocalDate,
    val currency: Currency
) {

    var balance = Money(ZERO, currency)
        private set

    fun registeredTransactions() = transactions.toList()

    private val transactions = mutableListOf<CardTransaction>()
    private val eventListeners = mutableListOf<Consumer<CardTransactionRegistered>>()

    fun registerTransaction(transaction: CardTransaction) {
        validate(transaction)
        commit(transaction)
        publish(transaction)
    }

    private fun validate(transaction: CardTransaction) {
        if (!transaction.hasCurrency(currency)) {
            throw CurrencyMismatchException()
        }
        if (!transaction.isBefore(expiration)) {
            throw CardExpiredException()
        }
        if (transaction.isPayment() && !balance.isGreaterOrEqual(transaction.money)) {
            throw InsufficientFundsException()
        }
    }

    private fun commit(transaction: CardTransaction) {
        transactions.add(transaction)
        balance = when (transaction.type) {
            INFLOW -> balance + transaction.money
            PAYMENT -> balance - transaction.money
        }
    }

    private fun publish(transaction: CardTransaction) {
        val event = CardTransactionRegistered(number, transaction)
        eventListeners.forEach { it.accept(event) }
    }

    fun addEventsListener(listener: Consumer<CardTransactionRegistered>) = eventListeners.add(listener)

    fun removeEventsListener(listener: Consumer<CardTransactionRegistered>) = eventListeners.remove(listener)

}
