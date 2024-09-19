package pl.training.payments.adapters.output.persistence.mongo

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import pl.training.payments.domain.Card
import pl.training.payments.domain.CardId
import pl.training.payments.domain.CardNumber
import pl.training.commons.annotations.Mapper
import pl.training.commons.model.PageSpec
import pl.training.commons.model.ResultPage
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


    fun toDocument(pageSpec: PageSpec) = PageRequest.of(pageSpec.index, pageSpec.size)

    fun toDomain(page: Page<CardDocument>) = ResultPage(
        page.content.map { toDomain(it) },
        PageSpec(page.number, page.size),
        page.totalPages
    )

}
