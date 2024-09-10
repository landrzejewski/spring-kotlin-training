package pl.training.payments.application

import pl.training.payments.application.input.Cards
import pl.training.payments.application.output.CardsEventPublisher
import pl.training.payments.application.output.CardsRepository
import pl.training.payments.application.output.TimeProvider
import pl.training.payments.domain.*
import pl.training.payments.domain.CardTransactionType.FEE
import java.util.function.Consumer

class CardsService(
    private val repository: CardsRepository,
    private val timeProvider: TimeProvider,
    private val eventPublisher: CardsEventPublisher
) : Cards {

    override fun charge(cardNumber: CardNumber, amount: Money) = processOperation(cardNumber) {
        it.addEventsListener(createEventListener())
        CardTransaction(timeProvider.getTimestamp(), amount)
    }

    override fun chargeFees(cardNumber: CardNumber) = processOperation(cardNumber) {
        val fees = CardTransactionBasedFees(it.transactions).execute()
        CardTransaction(timeProvider.getTimestamp(), fees, FEE)
    }

    override fun getTransactions(cardNumber: CardNumber) = getCard(cardNumber).transactions

    private fun processOperation(cardNumber: CardNumber, operation: (Card) -> CardTransaction) {
        val card = getCard(cardNumber)
        val transaction = operation(card)
        card.addTransaction(transaction)
        repository.save(card)
    }

    private fun getCard(cardNumber: CardNumber) = repository.getByNumber(cardNumber) ?: throw CardNotFoundException()

    private fun createEventListener(): Consumer<CardCharged> {
        return Consumer<CardCharged> {
            val appEvent = CardChargedApplicationEvent(it.number.toString())
            eventPublisher.publish(appEvent)
        }
    }

}
