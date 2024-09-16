package pl.training.payments.domain

import java.time.LocalDate
import java.util.Currency
import java.util.function.Consumer

data class Card(
    val id: CardId,
    val number: CardNumber,
    val expiration: LocalDate,
    val currency: Currency
) {

    val balance
        get() = Money(transactions.sumOf { it.money.amount }, currency)

    fun registeredTransactions(): List<CardTransaction> = transactions

    private val transactions = mutableListOf<CardTransaction>()
    private val eventListeners = mutableListOf<Consumer<CardTransactionRegistered>>()

    fun registerTransaction(transaction: CardTransaction) {
        validate(transaction)
        add(transaction)
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

    private fun add(transaction: CardTransaction) = transactions.add(transaction)

    private fun publish(transaction: CardTransaction) {
        val event = CardTransactionRegistered(number, transaction)
        eventListeners.forEach { it.accept(event) }
    }

    fun addEventsListener(listener: Consumer<CardTransactionRegistered>) = eventListeners.add(listener)

    fun removeEventsListener(listener: Consumer<CardTransactionRegistered>) = eventListeners.remove(listener)

}
