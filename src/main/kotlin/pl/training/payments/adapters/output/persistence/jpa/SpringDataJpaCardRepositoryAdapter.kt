package pl.training.payments.adapters.output.persistence.jpa

import pl.training.payments.application.output.CardRepository
import pl.training.payments.domain.Card
import pl.training.payments.domain.CardNumber
import pl.training.payments.utils.annotations.Adapter

@Adapter
class SpringDataJpaCardRepositoryAdapter(
    private val repository: SpringDataJpaCardRepository,
    private val mapper: SpringDataJpaCardRepositoryMapper
) : CardRepository {

    override fun getByNumber(cardNumber: CardNumber) =
        repository.findByNumber(cardNumber.value)?.let { mapper.toDomain(it) }

    override fun save(card: Card) {
        val cardEntity = mapper.toEntity(card)
        repository.save(cardEntity)
    }

}
