package pl.training.payments.adapters.output.persistence.mongo

import org.springframework.data.mongodb.repository.MongoRepository

interface SpringDataMongoCardRepository : MongoRepository<CardDocument, String> {

    fun findByNumber(cardNumber: String): CardDocument?

}
