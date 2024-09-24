package pl.training.blog.adapters.output.persistence.jpa

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.time.Instant
import jakarta.persistence.Column

@Entity(name = "Comments")
class CommentEntity(
    @Id
    @GeneratedValue
    val id: Long?,
    @Column(nullable = false)
    val text: String,
    val created: Instant,
    val author: String
)
