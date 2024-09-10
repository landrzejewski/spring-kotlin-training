package pl.training.payments.infrastructure.output

import pl.training.payments.application.CardChargedApplicationEvent
import pl.training.payments.application.output.CardsEventPublisher

class ConsoleCardsEventPublisher : CardsEventPublisher {

    override fun publish(event: CardChargedApplicationEvent) = println("Card ${event.cardNumber} charged")

}
