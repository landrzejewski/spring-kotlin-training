package pl.training.payments.persistence.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document(collection = "payments")
data class PaymentDocument(
    @Id val id: String,
    val value: BigDecimal,
    val currency: String,
    val status: String
)
