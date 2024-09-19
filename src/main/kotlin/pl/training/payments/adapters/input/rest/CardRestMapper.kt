package pl.training.payments.adapters.input.rest

import pl.training.commons.annotations.Mapper
import pl.training.payments.domain.CardNumber
import pl.training.payments.domain.Money
import java.util.Currency

@Mapper
class CardRestMapper {

    fun toDomain(number: String) = CardNumber(number)

    fun toDomain(cardOperationRequestDto: CardOperationRequestDto) =
        Money(cardOperationRequestDto.amount, Currency.getInstance(cardOperationRequestDto.currencyCode))

}