package pl.training.payments.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pl.training.payments.CardTestFixtures.testMoney
import pl.training.payments.CardTestFixtures.testTimestamp
import pl.training.payments.CardTestFixtures.validCard
import pl.training.payments.domain.CardTransactionType.INFLOW
import pl.training.payments.domain.CardTransactionType.PAYMENT


class CardTest {

    private val card = validCard()

    @Test
    fun `when register inflow transaction should increase the balance`() {
        val inflow = CardTransaction(testTimestamp, testMoney, INFLOW)
        card.registerTransaction(inflow)
        assertEquals(testMoney, card.balance)
    }

    @Test
    fun `given not enough funds on the card when register payment transaction should throw InsufficientFundsException`() {
        val payment = CardTransaction(testTimestamp, testMoney, PAYMENT)
        assertThrows<InsufficientFundsException> { card.registerTransaction(payment) }
    }

}