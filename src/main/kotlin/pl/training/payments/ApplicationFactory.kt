package pl.training.payments

import pl.training.payments.application.CardOperationsService
import pl.training.payments.application.CardInfoService
import pl.training.payments.application.input.CardOperations
import pl.training.payments.application.input.CardInfo
import pl.training.payments.application.output.CardEventPublisher
import pl.training.payments.application.output.CardRepository
import pl.training.payments.application.output.TimeProvider
import pl.training.payments.infrastructure.input.CardOperationsLoggingProxy
import pl.training.payments.infrastructure.input.CardViewModel
import pl.training.payments.infrastructure.output.ConsoleCardEventPublisher
import pl.training.payments.infrastructure.output.InMemoryCardRepository
import pl.training.payments.infrastructure.output.SystemTimeProvider

object ApplicationFactory {

    val cardRepository: CardRepository = InMemoryCardRepository()

    private fun timeProvider(): TimeProvider = SystemTimeProvider()

    private val cardEventPublisher: CardEventPublisher = ConsoleCardEventPublisher()

    private fun cardOperations(): CardOperations {
        val cardsService = CardOperationsService(cardRepository, timeProvider(), cardEventPublisher)
        return CardOperationsLoggingProxy(cardsService)
    }

    private fun cardsInfo(): CardInfo = CardInfoService(cardRepository)

    fun paymentsViewModel() = CardViewModel(cardOperations(), cardsInfo())

}
