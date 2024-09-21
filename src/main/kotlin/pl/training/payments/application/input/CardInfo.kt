package pl.training.payments.application.input

import pl.training.payments.domain.Card
import pl.training.payments.domain.CardNumber
import pl.training.payments.domain.CardTransaction
import pl.training.payments.domain.Money
import pl.training.commons.model.PageSpec
import pl.training.commons.model.ResultPage

interface CardInfo {

    fun cards(pageSpec: PageSpec): ResultPage<Card>

    fun transactions(cardNumber: CardNumber): List<CardTransaction>

    fun balance(cardNumber: CardNumber): Money

}
