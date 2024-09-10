package pl.training.payments.application.output

import pl.training.payments.application.CardChargedApplicationEvent

fun interface CardsEventPublisher {

    fun publish(event: CardChargedApplicationEvent)

}
