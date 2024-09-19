package pl.training.payments.adapters.output.persistence.jpa

import jakarta.persistence.Basic
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType.EAGER
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Lob
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDate

@Entity(name = "Card")
@Table(indexes = [Index(name = "card_number", columnList = "number")])
class CardEntity(
    @GeneratedValue
    @Id
    val id: Long,
    @Column(nullable = false)
    val number: String,
    val expiration: LocalDate,
    val balance: BigDecimal,
    val currencyCode: String,
    @Lob
    @Basic(fetch = EAGER)
    val transactions: String
)