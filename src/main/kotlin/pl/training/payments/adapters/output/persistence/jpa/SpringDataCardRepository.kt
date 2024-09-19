package pl.training.payments.adapters.output.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataCardRepository : JpaRepository<CardEntity, String> {

    fun findByNumber(cardNumber: String): CardEntity?

}