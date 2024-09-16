package pl.training.payments.application.input

import pl.training.payments.domain.CardNumber
import pl.training.payments.domain.Money

interface CardOperations {

    fun payment(cardNumber: CardNumber, amount: Money)
    fun inflow(cardNumber: CardNumber, amount: Money)

}
