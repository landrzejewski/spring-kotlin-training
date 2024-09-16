package pl.training.payments.infrastructure.input

import pl.training.payments.application.input.CardOperations
import pl.training.payments.domain.CardNumber
import pl.training.payments.domain.Money

class CardOperationsLoggingProxy(private val cardOperations: CardOperations) : CardOperations by cardOperations {

    override fun payment(cardNumber: CardNumber, amount: Money) {
        println("\n------------ Before payment transaction on card $cardNumber ------------")
        cardOperations.payment(cardNumber, amount)
        println("------------ After payment transaction on card $cardNumber  ------------\n")
    }

}