package pl.training.payments.adapters.input.rest

import java.math.BigDecimal
import java.time.LocalDate

class CardDto(
    val number: String,
    val expiration: LocalDate,
    val balance: BigDecimal,
    val currencyCode: String
)
