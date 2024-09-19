package pl.training.payments.adapters.input.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.training.commons.model.ResultPage
import pl.training.payments.application.input.CardInfo

@RequestMapping("api/cards")
@RestController
class CardInfoRestController(
    private val cardInfo: CardInfo,
    private val mapper: CardRestMapper
) {

    @GetMapping
    fun getCards(
        @RequestParam(required = false, defaultValue = "0") pageNumber: Int,
        @RequestParam(required = false, defaultValue = "10") pageSize: Int
    ): ResponseEntity<ResultPage<CardDto>> {
        val pageSpec = mapper.toDomain(pageNumber, pageSize)
        val cardsPage = cardInfo.cards(pageSpec)
        val cardsPageDto = mapper.toDto(cardsPage)
        return ResponseEntity.ok(cardsPageDto)
    }

    @GetMapping("{number:\\d{16,19}}/operations")
    fun getOperations(@PathVariable number: String): ResponseEntity<List<CardOperationDto>> {
        val cardNumber = mapper.toDomain(number)
        val transactions = cardInfo.transactions(cardNumber)
        val operationDtos = mapper.toDto(transactions)
        return ResponseEntity.ok(operationDtos)
    }

}