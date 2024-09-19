package pl.training.payments.adapters.input.rest

import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.training.payments.adapters.input.rest.OperationTypeDto.INFLOW
import pl.training.payments.adapters.input.rest.OperationTypeDto.PAYMENT
import pl.training.payments.application.input.CardOperations

@RequestMapping("api/cards")
@RestController
class CardOperationsRestController(
    private val cardOperations: CardOperations,
    private val mapper: CardRestMapper
) {

    @PostMapping("{number:\\d{16,19}}/operations")
    fun addOperation(
        @PathVariable number: String,
        /*@Valid*/ @Validated @RequestBody cardOperationRequestDto: CardOperationRequestDto
    ): ResponseEntity<Unit> {
        val cardNumber = mapper.toDomain(number)
        val amount = mapper.toDomain(cardOperationRequestDto)
        when (cardOperationRequestDto.operationType) {
            INFLOW -> cardOperations.inflow(cardNumber, amount)
            PAYMENT -> cardOperations.payment(cardNumber, amount)
        }
        return ResponseEntity.noContent().build()
    }

}