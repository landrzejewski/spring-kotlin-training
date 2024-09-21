package pl.training.payments.adapters.output.persistence.jpa

import jakarta.persistence.Basic
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType.EAGER
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Lob
import jakarta.persistence.Table
import java.time.LocalDate
import java.util.Objects

@Entity(name = "Card")
@Table(indexes = [Index(name = "card_number", columnList = "number")])
class CardEntity(
    @Id
    val id: String,
    @Column(unique = true)
    val number: String,
    val expiration: LocalDate,
    val currencyCode: String,
    @Lob
    @Basic(fetch = EAGER)
    val transactions: String
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other::class.java) return false
        val otherEntity = other as CardEntity
        return Objects.equals(id, otherEntity.id)
    }

    override fun hashCode() = Objects.hash(id)

}
