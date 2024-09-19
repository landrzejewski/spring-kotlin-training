package pl.training.payments.adapters.input.rest

import java.math.BigDecimal
import java.time.Instant

class CardOperationDto(
    val amount: BigDecimal,
    val currencyCode: String,
    val operationTypeDto: OperationTypeDto,
    val timestamp: Instant
)