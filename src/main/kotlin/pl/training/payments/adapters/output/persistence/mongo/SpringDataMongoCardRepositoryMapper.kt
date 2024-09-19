package pl.training.payments.adapters.output.persistence.mongo

import pl.training.payments.domain.Card
import pl.training.payments.domain.CardId
import pl.training.payments.domain.CardNumber
import pl.training.payments.utils.annotations.Mapper
import java.util.Currency

@Mapper
class SpringDataMongoCardRepositoryMapper {

    fun toDocument(card: Card) = CardDocument(
        card.id.value,
        card.number.value,
        card.expiration,
        card.balance.currency.currencyCode,
        card.registeredTransactions()
    )

    fun toDomain(cardDocument: CardDocument): Card {
        val card = Card(
            CardId(cardDocument.id),
            CardNumber(cardDocument.number),
            cardDocument.expiration,
            Currency.getInstance(cardDocument.currencyCode)
        )
        cardDocument.transactions.forEach { card.registerTransaction(it) }
        return card
    }

}
