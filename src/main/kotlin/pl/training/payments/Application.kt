package pl.training.payments

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import pl.training.payments.application.output.CardsRepository
import pl.training.payments.domain.Card
import pl.training.payments.domain.CardId
import pl.training.payments.domain.CardNumber
import pl.training.payments.domain.Money
import pl.training.payments.domain.Money.Companion.DEFAULT_CURRENCY
import pl.training.payments.infrastructure.input.CardsViewModel
import java.math.BigDecimal
import java.time.LocalDate

@SpringBootApplication
class PaymentsApplication(private val cardsRepository: CardsRepository, private val viewModel: CardsViewModel) :
    ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        val cardId = CardId(1)
        val cardNumber = CardNumber("4237251412344005")
        val cardExpirationDate = LocalDate.now().plusYears(1)
        val cardBalance = Money(BigDecimal.valueOf(1_000), DEFAULT_CURRENCY)
        val card = Card(cardId, cardNumber, cardExpirationDate, cardBalance)

        cardsRepository.save(card)

        viewModel.charge(100.50)
        viewModel.chargeFees()

        viewModel.getTransactions()
            .forEach { println(it) }
    }

}

fun main(args: Array<String>) {
    runApplication<PaymentsApplication>(*args)
}
