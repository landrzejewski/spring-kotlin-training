package pl.training.payments.adapters.output.persistence.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import pl.training.payments.domain.CardTransaction
import java.time.LocalDate

@Document(collection = "cards")
class CardDocument(
    @Id
    val id: String,
    @Indexed(unique = true)
    val number: String,
    val expiration: LocalDate,
    val currencyCode: String,
    val transactions: List<CardTransaction>
)
