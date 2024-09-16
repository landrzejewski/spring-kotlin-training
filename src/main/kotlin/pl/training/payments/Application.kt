package pl.training.payments

import pl.training.payments.domain.Card
import pl.training.payments.domain.CardId
import pl.training.payments.infrastructure.input.CardViewModel.Companion.CARD_NUMBER
import pl.training.payments.infrastructure.input.CardViewModel.Companion.CURRENCY
import java.time.LocalDate

fun main() {
    // Initialization
    val card = Card(id = CardId(1), number = CARD_NUMBER, expiration = LocalDate.now().plusYears(1), currency = CURRENCY)
    ApplicationFactory.cardRepository.save(card)
    val viewModel = ApplicationFactory.paymentsViewModel()

    // Application logic
    viewModel.depositFunds(1000.0)
    viewModel.pay(100.0)
    viewModel.pay(50.0)
    println("Transactions for card ${card.number}:")
    viewModel.getCardTransactions().forEach { println(it) }
}
