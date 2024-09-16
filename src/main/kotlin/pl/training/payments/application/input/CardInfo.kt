package pl.training.payments.application.input

import pl.training.payments.domain.CardNumber
import pl.training.payments.domain.CardTransaction

interface CardInfo {

    fun transactions(cardNumber: CardNumber): List<CardTransaction>

}
