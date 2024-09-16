package pl.training.payments.application.output

import pl.training.payments.application.CardTransactionEvent

interface CardEventPublisher {

    fun publish(event: CardTransactionEvent)

}
