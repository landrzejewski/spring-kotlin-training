package pl.training.payments.application

import pl.training.payments.application.input.CardInfo
import pl.training.payments.application.output.CardRepository
import pl.training.payments.domain.*

class CardInfoService(
    private val repository: CardRepository
) : CardInfo {

    override fun transactions(cardNumber: CardNumber) =
        repository.getByNumber(cardNumber)?.registeredTransactions() ?: throw CardNotFoundException()

}
