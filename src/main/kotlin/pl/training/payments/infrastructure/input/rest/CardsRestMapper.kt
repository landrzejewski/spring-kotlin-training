package pl.training.payments.infrastructure.input.rest

import pl.training.payments.domain.CardNumber
import pl.training.payments.domain.Money
import pl.training.payments.utils.Mapper
import java.util.Currency

@Mapper
class CardsRestMapper {

    fun toDomain(chargeRequestDto: ChargeRequestDto): Money {
        val currency = Currency.getInstance(chargeRequestDto.currency)
        return Money.of(chargeRequestDto.amount, currency)
    }

    fun toDomain(cardNumber: String) = CardNumber(cardNumber)

}