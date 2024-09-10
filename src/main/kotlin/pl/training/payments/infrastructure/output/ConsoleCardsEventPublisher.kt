package pl.training.payments.infrastructure.output

import org.springframework.stereotype.Service
import pl.training.payments.application.CardChargedApplicationEvent
import pl.training.payments.application.output.CardsEventPublisher

@Service
class ConsoleCardsEventPublisher : CardsEventPublisher {

    override fun publish(event: CardChargedApplicationEvent) = println("Card ${event.cardNumber} charged")

}
