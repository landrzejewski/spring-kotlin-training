package pl.training.payments.adapters.output.persistence.jpa

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import pl.training.payments.domain.Card
import pl.training.payments.domain.CardId
import pl.training.payments.domain.CardNumber
import pl.training.payments.domain.CardTransaction
import pl.training.commons.annotations.Mapper
import pl.training.commons.model.PageSpec
import pl.training.commons.model.ResultPage
import java.util.Currency

@Mapper
class SpringDataJpaCardRepositoryMapper {

    fun toEntity(card: Card) = CardEntity(
        card.id.value,
        card.number.value,
        card.expiration,
        card.balance.currency.currencyCode,
        toJson(card.registeredTransactions())
    )

    fun toDomain(cardEntity: CardEntity): Card {
        val card = Card(
            CardId(cardEntity.id),
            CardNumber(cardEntity.number),
            cardEntity.expiration,
            Currency.getInstance(cardEntity.currencyCode)
        )
        if (cardEntity.transactions.isNotEmpty()) {
            fromJson(cardEntity.transactions).forEach { card.registerTransaction(it) }
        }
        return card
    }

    fun toEntity(pageSpec: PageSpec) = PageRequest.of(pageSpec.index, pageSpec.size)

    fun toDomain(page: Page<CardEntity>) = ResultPage(
        page.content.map { toDomain(it) },
        PageSpec(page.number, page.size),
        page.totalPages
    )

    private fun toJson(transactions: List<CardTransaction>) =
        JSON_MAPPER.writeValueAsString(transactions)

    private fun fromJson(json: String) =
        JSON_MAPPER.readValue(json, object : TypeReference<List<CardTransaction>>() {})

    companion object {

        private val JSON_MAPPER = ObjectMapper()
            .registerKotlinModule()
            .registerModule(JavaTimeModule())
            .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)

    }

}
