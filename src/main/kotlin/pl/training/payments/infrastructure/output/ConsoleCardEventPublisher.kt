package pl.training.payments.infrastructure.output

import pl.training.payments.application.CardTransactionEvent
import pl.training.payments.application.output.CardEventPublisher

class ConsoleCardEventPublisher : CardEventPublisher {

    override fun publish(event: CardTransactionEvent) = println("New ${event.type} transaction on card ${event.cardNumber}")

}
