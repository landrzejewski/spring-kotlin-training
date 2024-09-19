package pl.training.payments.adapters.output.persistence.jpa

import org.springframework.context.annotation.Primary
import pl.training.payments.application.output.CardRepository
import pl.training.payments.domain.Card
import pl.training.payments.domain.CardNumber
import pl.training.payments.utils.annotations.Adapter

@Primary
@Adapter
class SpringDataCardRepositoryAdapter(
    private val repository: SpringDataCardRepository,
    private val mapper: SpringDataCardRepositoryMapper
) : CardRepository {

    override fun getByNumber(cardNumber: CardNumber): Card? {
        val number = mapper.toEntity(cardNumber)
        return repository.findByNumber(number)?.let { mapper.toDomain(it) }
    }

    override fun save(card: Card) {
        val cardEntity = mapper.toEntity(card)
        repository.save(cardEntity)
    }

}