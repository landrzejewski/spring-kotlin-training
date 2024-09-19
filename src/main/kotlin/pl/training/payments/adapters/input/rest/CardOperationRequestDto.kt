package pl.training.payments.adapters.input.rest

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Pattern
import pl.training.commons.model.validation.MonetaryAmount
import java.math.BigDecimal

class CardOperationRequestDto(
    @field:MonetaryAmount @field:Min(100) val amount: BigDecimal,
    @field:Pattern(regexp = "^[a-zA-Z]*$") val currencyCode: String,
    val operationType: OperationTypeDto
)
