package pl.training.payments.infrastructure.input

import pl.training.payments.application.input.Cards
import pl.training.payments.domain.CardNumber
import pl.training.payments.domain.Money

class CardsViewModel(val cards: Cards) {

    private val cardNumber = CardNumber("4237251412344005")

    fun charge(value: Double) = cards.charge(cardNumber, Money.of(value))

    fun chargeFees() = cards.chargeFees(cardNumber)

    fun getTransactions() = cards.getTransactions(cardNumber)

}
