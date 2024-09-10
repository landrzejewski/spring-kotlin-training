package pl.training.payments.infrastructure.output.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataCardsRepository : JpaRepository<CardEntity, String> {

    fun findByNumber(cardNumber: String): CardEntity?

}