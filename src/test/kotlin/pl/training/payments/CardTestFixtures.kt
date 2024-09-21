package pl.training.payments

import pl.training.payments.adapters.output.persistence.jpa.CardEntity
import pl.training.payments.domain.Card
import pl.training.payments.domain.CardId
import pl.training.payments.domain.CardNumber
import pl.training.payments.domain.Money
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.Currency

object CardTestFixtures {

    val testTimestamp = ZonedDateTime.now()
    val testCurrency = Currency.getInstance("PLN")
    val testMoney = Money(100.0, testCurrency)
    val testCardId = CardId()
    val testCardNumber = CardNumber("4237251412344005")
    val testExpirationDate =LocalDate.now().plusYears(1)
    fun validCard() = Card(testCardId, testCardNumber, testExpirationDate , testCurrency)
    fun validCardEntity() = CardEntity(testCardId.value, testCardNumber.value, testExpirationDate, testCurrency.currencyCode, "")

}