package pl.training.payments.adapters.output.persistence.jpa

import org.springframework.context.annotation.Primary
import pl.training.payments.application.output.CardRepository
import pl.training.payments.domain.Card
import pl.training.payments.domain.CardNumber
import pl.training.commons.annotations.Adapter
import pl.training.commons.model.PageSpec
import pl.training.commons.model.ResultPage

@Primary
@Adapter
class SpringDataJpaCardRepositoryAdapter(
    private val repository: SpringDataJpaCardRepository,
    private val mapper: SpringDataJpaCardRepositoryMapper
) : CardRepository {

    override fun findAll(pageSpec: PageSpec): ResultPage<Card> {
        val pageRequest = mapper.toEntity(pageSpec)
        val cardEntitiesPage = repository.findAll(pageRequest)
        return mapper.toDomain(cardEntitiesPage)
    }

    override fun getByNumber(cardNumber: CardNumber) =
        repository.findByNumber(cardNumber.value)?.let { mapper.toDomain(it) }

    override fun save(card: Card) {
        val cardEntity = mapper.toEntity(card)
        repository.save(cardEntity)
    }

}
