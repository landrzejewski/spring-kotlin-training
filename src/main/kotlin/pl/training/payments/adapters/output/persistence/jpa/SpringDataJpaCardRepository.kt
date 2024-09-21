package pl.training.payments.adapters.output.persistence.jpa

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository

interface SpringDataJpaCardRepository : CrudRepository<CardEntity, String> {

    fun findAll(pageable: Pageable): Page<CardEntity>

    fun findByNumber(cardNumber: String): CardEntity?

}
