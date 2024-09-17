package pl.training.payments.application

import pl.training.payments.application.input.CardOperations
import pl.training.payments.application.output.CardEventPublisher
import pl.training.payments.application.output.CardRepository
import pl.training.payments.application.output.TimeProvider
import pl.training.payments.domain.*
import pl.training.payments.domain.CardTransactionType.INFLOW
import pl.training.payments.domain.CardTransactionType.PAYMENT
import java.util.function.Consumer

open class CardOperationsService(
    private val repository: CardRepository,
    private val timeProvider: TimeProvider,
    private val eventPublisher: CardEventPublisher
) : CardOperations {

    override fun inflow(cardNumber: CardNumber, amount: Money) =
        addTransaction(cardNumber, CardTransaction(timeProvider.getTimestamp(), amount, INFLOW))

    override fun payment(cardNumber: CardNumber, amount: Money) =
        addTransaction(cardNumber, CardTransaction(timeProvider.getTimestamp(), amount, PAYMENT))

    private fun addTransaction(cardNumber: CardNumber, transaction: CardTransaction) {
        val card = repository.getByNumber(cardNumber) ?: throw CardNotFoundException()
        val eventListener = createCardEventListener()
        card.addEventsListener(eventListener)
        card.registerTransaction(transaction)
        card.removeEventsListener(eventListener)
        repository.save(card)
    }

    private fun createCardEventListener() = Consumer<CardTransactionRegistered> {
        val appEvent = CardTransactionEvent(it.number.toString(), it.transaction.type.name)
        eventPublisher.publish(appEvent)
    }

}
