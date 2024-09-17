package pl.training.payments.application

import pl.training.payments.application.input.CardInfo
import pl.training.payments.application.output.CardRepository
import pl.training.payments.domain.*

open class CardInfoService(
    private val repository: CardRepository
) : CardInfo {

    override fun transactions(cardNumber: CardNumber) = getCard(cardNumber).registeredTransactions()

    override fun balance(cardNumber: CardNumber) = getCard(cardNumber).balance

    private fun getCard(cardNumber: CardNumber) = repository.getByNumber(cardNumber) ?: throw CardNotFoundException()

}
