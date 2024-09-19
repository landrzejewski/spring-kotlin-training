package pl.training.extras

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.NamedAttributeNode
import jakarta.persistence.NamedEntityGraph
import jakarta.persistence.NamedQuery
import jakarta.persistence.OneToMany
import pl.training.extras.OrderEntity.Companion.BY_MIN_TOTAL_VALUE
import pl.training.extras.OrderEntity.Companion.WITH_ITEMS
import java.time.Instant

@NamedEntityGraph(name = WITH_ITEMS, attributeNodes = [NamedAttributeNode("items")])
@NamedQuery(name = BY_MIN_TOTAL_VALUE, query = "select o from Orders o where o.totalValue >= :minValue")
@Entity(name = "Orders")
class OrderEntity(
    @GeneratedValue
    @Id
    val id: Long?,
    val name: String,
    val created: Instant,
    val totalValue: Double,
    @JoinColumn
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val items: List<OrderItemEntity>
) {

    override fun toString() = """
    {    
        "id": $id,
        "name": "$name",
        "created": $created,
        "totalValue": $totalValue
    }
    """.trimIndent()

    companion object {

        const val BY_MIN_TOTAL_VALUE = "OrderItemEntity.BY_MIN_TOTAL_VALUE"
        const val WITH_ITEMS = "OrderItemEntity.WITH_ITEMS"

    }

}
