package pl.training

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import pl.training.payments.application.output.CardRepository
import pl.training.payments.domain.Card
import pl.training.payments.domain.CardId
import pl.training.payments.domain.CardNumber
import java.time.LocalDate
import java.util.Currency

@SpringBootApplication
class Application(private val cardRepository: CardRepository) :
    ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        // Initialization
        val cardNumber = CardNumber("4237251412344005")
        val currency = Currency.getInstance("PLN")
        val card = Card(id = CardId(), number = cardNumber, expiration = LocalDate.now().plusYears(1), currency = currency)
        cardRepository.save(card)
    }

}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
