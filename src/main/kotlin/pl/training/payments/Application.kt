package pl.training.payments

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import pl.training.payments.application.output.CardsRepository
import pl.training.payments.domain.Card
import pl.training.payments.domain.CardId
import pl.training.payments.domain.CardNumber
import pl.training.payments.domain.Money
import pl.training.payments.domain.Money.Companion.DEFAULT_CURRENCY
import pl.training.payments.infrastructure.input.CardsViewModel
import java.math.BigDecimal
import java.time.LocalDate

fun main() {
    AnnotationConfigApplicationContext(ApplicationConfiguration::class.java).use { context ->
        val cardId = CardId(1)
        val cardNumber = CardNumber("4237251412344005")
        val cardExpirationDate = LocalDate.now().plusYears(1)
        val cardBalance = Money(BigDecimal.valueOf(1_000), DEFAULT_CURRENCY)
        val card = Card(cardId, cardNumber, cardExpirationDate, cardBalance)

        val cardsRepository = context.getBean(CardsRepository::class.java)
        cardsRepository.save(card)

        val viewModel = context.getBean(CardsViewModel::class.java)
        viewModel.charge(100.50)
        viewModel.chargeFees()

        viewModel.getTransactions()
            .forEach { println(it) }
    }

}
