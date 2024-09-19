package pl.training.payments.application.output

import pl.training.payments.domain.Card
import pl.training.payments.domain.CardNumber
import pl.training.payments.utils.model.PageSpec
import pl.training.payments.utils.model.ResultPage

interface CardRepository {

    fun findAll(pageSpec: PageSpec): ResultPage<Card>

    fun getByNumber(cardNumber: CardNumber): Card?

    fun save(card: Card)

}
