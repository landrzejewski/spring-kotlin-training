package pl.training.payments.application.input

import pl.training.payments.domain.CardNumber
import pl.training.payments.domain.CardTransaction
import pl.training.payments.domain.Money

interface Cards {

    open fun charge(cardNumber: CardNumber, amount: Money)
    fun chargeFees(cardNumber: CardNumber)
    fun getTransactions(cardNumber: CardNumber): List<CardTransaction>

}
