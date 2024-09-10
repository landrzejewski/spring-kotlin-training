package pl.training.payments.infrastructure.output

import org.springframework.stereotype.Repository
import pl.training.payments.application.output.CardsRepository
import pl.training.payments.domain.Card
import pl.training.payments.domain.CardNumber

@Repository
class InMemoryCardsRepository : CardsRepository {

    private val data = mutableMapOf<CardNumber, Card>()

    override fun getByNumber(cardNumber: CardNumber) = data[cardNumber]

    override fun save(card: Card) {
        data[card.number] = card
    }

}
