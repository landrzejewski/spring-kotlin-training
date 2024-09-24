package pl.training.payments.adapters.output

import org.springframework.context.ApplicationEventPublisher
import pl.training.payments.application.CardTransactionEvent
import pl.training.payments.application.output.CardEventPublisher

class SpringCardEventPublisher(private val publisher: ApplicationEventPublisher)
    : CardEventPublisher{

    override fun publish(event: CardTransactionEvent) {
        publisher.publishEvent(event)
    }

}