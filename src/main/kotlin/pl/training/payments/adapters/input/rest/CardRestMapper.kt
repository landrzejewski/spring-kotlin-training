package pl.training.payments.adapters.input.rest

import pl.training.commons.annotations.Mapper
import pl.training.commons.model.PageSpec
import pl.training.commons.model.ResultPage
import pl.training.payments.domain.Card
import pl.training.payments.domain.CardNumber
import pl.training.payments.domain.CardTransaction
import pl.training.payments.domain.CardTransactionType.INFLOW
import pl.training.payments.domain.CardTransactionType.PAYMENT
import pl.training.payments.domain.Money
import java.util.Currency

@Mapper
class CardRestMapper {

    fun toDomain(number: String) = CardNumber(number)

    fun toDomain(cardOperationRequestDto: CardOperationRequestDto) =
        Money(cardOperationRequestDto.amount, Currency.getInstance(cardOperationRequestDto.currencyCode))

    fun toDomain(pageNumber: Int, pageSize: Int) = PageSpec(pageNumber, pageSize)

    fun toDto(resultPage: ResultPage<Card>) = ResultPage<CardDto>(
        resultPage.content.map {
            CardDto(
                it.number.value,
                it.expiration,
                it.balance.amount,
                it.currency.currencyCode
            )
        },
        resultPage.pageSpec,
        resultPage.totalPages
    )

    fun toDto(transactions: List<CardTransaction>) =
        transactions.map {
            CardOperationDto(
                it.money.amount,
                it.money.currency.currencyCode,
                when (it.type) {
                    INFLOW -> OperationTypeDto.INFLOW
                    PAYMENT -> OperationTypeDto.PAYMENT
                },
                it.timestamp.toInstant()
            )
        }

}