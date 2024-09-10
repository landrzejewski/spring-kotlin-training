package pl.training.payments.infrastructure.output.persistence.jpa

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository
import pl.training.payments.application.output.CardsRepository
import pl.training.payments.domain.Card
import pl.training.payments.domain.CardNumber

@Primary
@Repository
class SpringDataCardsRepositoryAdapter(
    private val repository: SpringDataCardsRepository,
    private val mapper: SpringDataCardsRepositoryMapper
) : CardsRepository {

    override fun getByNumber(cardNumber: CardNumber): Card? {
        val number = mapper.toEntity(cardNumber)
        return repository.findByNumber(number)?.let {
            mapper.toDomain(it)
        }
    }

    override fun save(card: Card) {
        val cardEntity = mapper.toEntity(card)
        repository.save(cardEntity)
    }

}