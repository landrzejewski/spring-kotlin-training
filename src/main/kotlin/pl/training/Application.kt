package pl.training

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import pl.training.payments.adapters.input.CardViewModel
import pl.training.payments.adapters.input.TestCardViewModel.Companion.CARD_NUMBER
import pl.training.payments.adapters.input.TestCardViewModel.Companion.CURRENCY
import pl.training.payments.application.output.CardRepository
import pl.training.payments.domain.Card
import pl.training.payments.domain.CardId
import java.time.LocalDate

fun main() {
    AnnotationConfigApplicationContext(ApplicationConfiguration::class.java).use { context ->
        // Initialization
        val card = Card(id = CardId(), number = CARD_NUMBER, expiration = LocalDate.now().plusYears(1), currency = CURRENCY)
        context.getBean(CardRepository::class.java).save(card)

        val viewModel = context.getBean(CardViewModel::class.java)

        // Application logic
        viewModel.depositFunds(1000.0)
        viewModel.pay(100.0)
        viewModel.pay(50.0)

        println("---------------------------------- Summary ----------------------------------")
        println("Transactions:")
        viewModel.getTransactions().forEach { println(it) }
        println("Balance: ${viewModel.getBalance()}")
    }

}
