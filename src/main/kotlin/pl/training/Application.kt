package pl.training

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import pl.training.payments.adapters.input.CardViewModel
import pl.training.payments.adapters.input.TestCardViewModel.Companion.CARD_NUMBER
import pl.training.payments.adapters.input.TestCardViewModel.Companion.CURRENCY
import pl.training.payments.application.output.CardRepository
import pl.training.payments.domain.Card
import pl.training.payments.domain.CardId
import java.time.LocalDate

@SpringBootApplication
class Application(private val cardRepository: CardRepository, private val viewModel: CardViewModel) :
    ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        // Initialization
        val card = Card(id = CardId(), number = CARD_NUMBER, expiration = LocalDate.now().plusYears(1), currency = CURRENCY)
        cardRepository.save(card)

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

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
