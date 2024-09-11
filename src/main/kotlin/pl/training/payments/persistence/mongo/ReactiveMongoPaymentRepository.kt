package pl.training.payments.persistence.mongo

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface ReactiveMongoPaymentRepository : ReactiveMongoRepository<PaymentDocument, String>