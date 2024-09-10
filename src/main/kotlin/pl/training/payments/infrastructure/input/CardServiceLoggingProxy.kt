package pl.training.payments.infrastructure.input

import pl.training.payments.application.input.Cards
import pl.training.payments.domain.CardNumber
import pl.training.payments.domain.Money

class CardServiceLoggingProxy(private val cards: Cards) : Cards by cards {

    override fun charge(cardNumber: CardNumber, amount: Money) {
        println("Before charge")
        cards.charge(cardNumber, amount)
        println("After charge")
    }

}