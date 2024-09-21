package pl.training.payments.adapters.input

import pl.training.commons.model.PageSpec
import pl.training.commons.model.ResultPage
import pl.training.payments.domain.Card
import pl.training.payments.domain.CardTransaction
import pl.training.payments.domain.Money

interface CardViewModel {

    fun depositFunds(value: Double)

    fun pay(value: Double)

    fun getTransactions(): List<CardTransaction>

    fun getBalance(): Money

    fun getCards(pageSpec: PageSpec): ResultPage<Card>

}