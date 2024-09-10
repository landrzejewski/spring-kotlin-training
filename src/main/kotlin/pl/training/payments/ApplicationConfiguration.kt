package pl.training.payments

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.training.payments.application.CardsService
import pl.training.payments.application.input.Cards
import pl.training.payments.application.output.CardsEventPublisher
import pl.training.payments.application.output.CardsRepository
import pl.training.payments.application.output.TimeProvider

@Configuration
class ApplicationConfiguration {

    @Bean
    fun cardsService(
        cardsRepository: CardsRepository,
        timeProvider: TimeProvider,
        eventPublisher: CardsEventPublisher
    ): Cards = CardsService(cardsRepository, timeProvider, eventPublisher)

}