package pl.training.payments.adapters.input

import pl.training.payments.application.input.CardOperations
import pl.training.payments.domain.CardNumber
import pl.training.payments.domain.Money

class CardOperationsLoggingProxy(private val cardOperations: CardOperations) : CardOperations by cardOperations {

    override fun payment(cardNumber: CardNumber, amount: Money) {
        println("\n------------ Before PAYMENT transaction on card $cardNumber ------------")
        cardOperations.payment(cardNumber, amount)
        println("------------ After PAYMENT transaction on card $cardNumber  ------------\n")
    }

}