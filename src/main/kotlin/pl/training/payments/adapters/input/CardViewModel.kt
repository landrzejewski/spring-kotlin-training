package pl.training.payments.adapters.input

import pl.training.payments.application.input.CardInfo
import pl.training.payments.application.input.CardOperations
import pl.training.payments.domain.CardNumber
import pl.training.payments.domain.Money
import java.util.Currency

class CardViewModel(
    private val cardOperations: CardOperations,
    private val cardInfo: CardInfo
) {

    fun depositFunds(value: Double) = cardOperations.inflow(CARD_NUMBER, Money(value, CURRENCY))

    fun pay(value: Double) = cardOperations.payment(CARD_NUMBER, Money(value, CURRENCY))

    fun getTransactions() = cardInfo.transactions(CARD_NUMBER)

    fun getBalance() = cardInfo.balance(CARD_NUMBER)

    companion object {

        val CURRENCY: Currency = Currency.getInstance("PLN")
        val CARD_NUMBER = CardNumber("4237251412344005")

    }

}
