package pl.training.extras

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity(name = "OrderItem")
class OrderItemEntity(
    @GeneratedValue
    @Id
    val id: Long?,
    val name: String,
    val quantity: Int,
    val price: Double
)
