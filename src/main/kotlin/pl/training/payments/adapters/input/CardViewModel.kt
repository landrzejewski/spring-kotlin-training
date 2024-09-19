package pl.training.payments.adapters.input

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import pl.training.payments.application.input.CardInfo
import pl.training.payments.application.input.CardOperations
import pl.training.payments.domain.CardNumber
import pl.training.payments.domain.Money
import pl.training.payments.utils.model.PageSpec
import java.util.Currency

@Transactional
@Component
class CardViewModel(
    private val cardOperations: CardOperations,
    private val cardInfo: CardInfo
) {

    fun depositFunds(value: Double) = cardOperations.inflow(CARD_NUMBER, Money(value, CURRENCY))

    fun pay(value: Double) = cardOperations.payment(CARD_NUMBER, Money(value, CURRENCY))

    fun getTransactions() = cardInfo.transactions(CARD_NUMBER)

    fun getBalance() = cardInfo.balance(CARD_NUMBER)

    fun getCards(pageSpec: PageSpec) = cardInfo.cards(pageSpec)

    companion object {

        val CURRENCY: Currency = Currency.getInstance("PLN")
        val CARD_NUMBER = CardNumber("4237251412344005")

    }

}
