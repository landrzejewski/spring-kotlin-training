package pl.training.payments

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.training.payments.application.CardsService
import pl.training.payments.application.input.Cards
import pl.training.payments.application.output.CardsEventPublisher
import pl.training.payments.application.output.CardsRepository
import pl.training.payments.application.output.TimeProvider

@Configuration
class ApplicationConfiguration {

    // @Scope("prototype")
    @Bean(name = ["cards"], initMethod = "initialize", destroyMethod = "destroy")
    fun cardsService(
        cardsRepository: CardsRepository,
        @Qualifier("systemTimeProvider") timeProvider: TimeProvider,
        eventPublisher: CardsEventPublisher
    ): Cards = CardsService(cardsRepository, timeProvider, eventPublisher)

}