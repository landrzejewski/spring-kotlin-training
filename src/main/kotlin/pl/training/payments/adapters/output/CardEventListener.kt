package pl.training.payments.adapters.output

import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import pl.training.payments.application.CardTransactionEvent

@Service
open class CardEventListener {

    @Async
    @EventListener
    open fun onEvent(event: CardTransactionEvent) {
        println("New ${event.type} transaction on card ${event.cardNumber}")
    }

}