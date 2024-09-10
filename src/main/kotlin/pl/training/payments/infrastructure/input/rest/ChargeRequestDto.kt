package pl.training.payments.infrastructure.input.rest

import jakarta.validation.constraints.Pattern
import java.math.BigDecimal

class ChargeRequestDto(
    val amount: BigDecimal,
    @field:Pattern(regexp = "^[a-zA-Z]*$") val currency: String
)