package pl.training.payments.adapters.input

import pl.training.payments.domain.CardTransaction
import pl.training.payments.domain.Money

interface CardViewModel {

    fun depositFunds(value: Double)

    fun pay(value: Double)

    fun getTransactions(): List<CardTransaction>

    fun getBalance(): Money

}
