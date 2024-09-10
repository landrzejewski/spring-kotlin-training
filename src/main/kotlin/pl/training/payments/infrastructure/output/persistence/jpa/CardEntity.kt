package pl.training.payments.infrastructure.output.persistence.jpa

import jakarta.persistence.Basic
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType.EAGER
import jakarta.persistence.Id
import jakarta.persistence.Lob
import java.math.BigDecimal
import java.time.LocalDate

@Entity(name = "Card")
class CardEntity(
    @Id
    val id: Long,
    @Column(nullable = false, name = "card_number")
    val number: String,
    val expiration: LocalDate,
    val balance: BigDecimal,
    val currency: String,
    @Lob
    @Basic(fetch = EAGER)
    val transactions: String
)