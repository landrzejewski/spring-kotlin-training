package pl.training

import pl.training.payments.adapters.input.CardViewModel.Companion.CARD_NUMBER
import pl.training.payments.adapters.input.CardViewModel.Companion.CURRENCY
import pl.training.payments.domain.Card
import pl.training.payments.domain.CardId
import java.time.LocalDate

fun main() {
    // Initialization
    val card = Card(id = CardId(), number = CARD_NUMBER, expiration = LocalDate.now().plusYears(1), currency = CURRENCY)
    ApplicationConfiguration.cardRepository.save(card)
    val viewModel = ApplicationConfiguration.paymentsViewModel()

    // Application logic
    viewModel.depositFunds(1000.0)
    viewModel.pay(100.0)
    viewModel.pay(50.0)

    println("---------------------------------- Summary ----------------------------------")
    println("Transactions:")
    viewModel.getTransactions().forEach { println(it) }
    println("Balance: ${viewModel.getBalance()}")
}
