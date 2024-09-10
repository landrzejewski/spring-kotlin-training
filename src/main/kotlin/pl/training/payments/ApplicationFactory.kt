package pl.training.payments

import pl.training.payments.infrastructure.input.CardServiceLoggingProxy
import pl.training.payments.application.CardsService
import pl.training.payments.application.input.Cards
import pl.training.payments.application.output.CardsEventPublisher
import pl.training.payments.application.output.CardsRepository
import pl.training.payments.application.output.TimeProvider
import pl.training.payments.infrastructure.input.CardsViewModel
import pl.training.payments.infrastructure.output.ConsoleCardsEventPublisher
import pl.training.payments.infrastructure.output.InMemoryCardsRepository
import pl.training.payments.infrastructure.output.SystemTimeProvider

object ApplicationFactory {

    val cardsRepository: CardsRepository = InMemoryCardsRepository()

    private fun timeProvider(): TimeProvider = SystemTimeProvider()

    private val cardsEventPublisher: CardsEventPublisher = ConsoleCardsEventPublisher()

    private fun cards(): Cards {
        val cardsService = CardsService(cardsRepository, timeProvider(), cardsEventPublisher)
        return CardServiceLoggingProxy(cardsService)
    }

    fun paymentsViewModel() = CardsViewModel(cards())

}
