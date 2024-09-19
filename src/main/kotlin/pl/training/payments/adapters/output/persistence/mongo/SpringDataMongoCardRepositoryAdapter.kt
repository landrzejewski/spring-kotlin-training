package pl.training.payments.adapters.output.persistence.mongo

import pl.training.payments.application.output.CardRepository
import pl.training.payments.domain.Card
import pl.training.payments.domain.CardNumber
import pl.training.payments.utils.annotations.Adapter
import pl.training.payments.utils.model.PageSpec
import pl.training.payments.utils.model.ResultPage

// @Primary
@Adapter
class SpringDataMongoCardRepositoryAdapter(
    private val repository: SpringDataMongoCardRepository,
    private val mapper: SpringDataMongoCardRepositoryMapper
) : CardRepository {

    override fun findAll(pageSpec: PageSpec): ResultPage<Card> {
        val pageRequest = mapper.toDocument(pageSpec)
        val cardEntitiesPage = repository.findAll(pageRequest)
        return mapper.toDomain(cardEntitiesPage)
    }

    override fun getByNumber(cardNumber: CardNumber) =
        repository.findByNumber(cardNumber.value)?.let { mapper.toDomain(it) }

    override fun save(card: Card) {
        val cardDocument = mapper.toDocument(card)
        repository.save(cardDocument)
    }

}
