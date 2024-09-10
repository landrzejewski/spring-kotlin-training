package pl.training.payments.infrastructure.input.rest

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.training.payments.application.input.Cards

@RequestMapping("/api/cards")
@RestController
class CardsRestController(
    private val cards: Cards,
    private val mapper: CardsRestMapper
) {

    @PostMapping("{cardNumber}/charges")
    fun charge(@PathVariable cardNumber: String, @RequestBody @Valid chargeRequest: ChargeRequestDto): ResponseEntity<Void> {
        val cardNumber = mapper.toDomain(cardNumber)
        val amount = mapper.toDomain(chargeRequest)
        cards.charge(cardNumber, amount)
        return ResponseEntity.noContent().build()
    }

}