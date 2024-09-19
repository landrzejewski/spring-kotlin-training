package pl.training.payments.adapters.output.persistence.jpa

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import pl.training.payments.domain.Card
import pl.training.payments.domain.CardId
import pl.training.payments.domain.CardNumber
import pl.training.payments.domain.CardTransaction
import pl.training.payments.utils.annotations.Mapper
import java.util.Currency

@Mapper
class SpringDataCardRepositoryMapper {

    fun toEntity(cardNumber: CardNumber) = cardNumber.toString()

    fun toEntity(card: Card) = CardEntity(
        card.id.value,
        toEntity(card.number),
        card.expiration,
        card.balance.amount,
        card.balance.currency.currencyCode,
        toJson(card.registeredTransactions())
    )

    fun toDomain(cardEntity: CardEntity): Card {
        val card = Card(
            CardId(cardEntity.id),
            CardNumber(cardEntity.number),
            cardEntity.expiration,
            toDomain(cardEntity.currencyCode)
        )
        fromJson(cardEntity.transactions).forEach {
            card.registerTransaction(it)
        }
        return card
    }

    private fun toDomain(currencyCode: String) = Currency.getInstance(currencyCode)

    private fun toJson(transactions: List<CardTransaction>) =
        JSON_MAPPER.writeValueAsString(transactions)

    private fun fromJson(json: String) =
        JSON_MAPPER.readValue(json, object : TypeReference<MutableList<CardTransaction>>() {})

    companion object {

        private val JSON_MAPPER = ObjectMapper()
            .registerKotlinModule()
            .registerModule(JavaTimeModule())
            .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)

    }

}