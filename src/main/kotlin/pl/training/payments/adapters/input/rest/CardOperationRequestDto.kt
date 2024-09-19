package pl.training.payments.adapters.input.rest

import java.math.BigDecimal

class CardOperationRequestDto(
    val operationType: OperationType,
    val amount: BigDecimal,
    val currencyCode: String
) {

    enum class OperationType {
        INFLOW, PAYMENT
    }

}
