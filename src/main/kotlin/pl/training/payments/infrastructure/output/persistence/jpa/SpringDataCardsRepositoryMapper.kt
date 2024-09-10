package pl.training.payments.infrastructure.output.persistence.jpa

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import pl.training.payments.domain.Card
import pl.training.payments.domain.CardId
import pl.training.payments.domain.CardNumber
import pl.training.payments.domain.CardTransaction
import pl.training.payments.domain.Money
import pl.training.payments.utils.Mapper
import java.util.Currency

@Mapper
class SpringDataCardsRepositoryMapper {

    fun toEntity(cardNumber: CardNumber) = cardNumber.toString()

    fun toEntity(card: Card) = CardEntity(
        card.id.value,
        toEntity(card.number),
        card.expiration,
        card.balance.amount,
        card.balance.currency.currencyCode,
        toJson(card.transactions)
    )

    fun toDomain(cardEntity: CardEntity) = Card(
        CardId(cardEntity.id),
        CardNumber(cardEntity.number),
        cardEntity.expiration,
        Money(cardEntity.balance, Currency.getInstance(cardEntity.currency)),
        fromJson(cardEntity.transactions)
    )

    private fun toJson(transactions: MutableList<CardTransaction>) = ObjectMapper()
        .registerKotlinModule()
        .registerModule(JavaTimeModule())
        .writeValueAsString(transactions)

    private fun fromJson(json: String) = ObjectMapper()
        .registerKotlinModule()
        .registerModule(JavaTimeModule())
        .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
        .readValue(json, object : TypeReference<MutableList<CardTransaction>>() {})

}