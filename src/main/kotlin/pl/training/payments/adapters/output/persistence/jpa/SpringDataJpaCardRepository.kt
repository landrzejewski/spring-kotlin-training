package pl.training.payments.adapters.output.persistence.jpa

import org.springframework.data.repository.CrudRepository

interface SpringDataJpaCardRepository : CrudRepository<CardEntity, String> {

    fun findByNumber(cardNumber: String): CardEntity?

}
