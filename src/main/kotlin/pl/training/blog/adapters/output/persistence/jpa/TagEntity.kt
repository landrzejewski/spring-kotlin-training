package pl.training.blog.adapters.output.persistence.jpa

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Column

@Entity(name = "Tags")
class TagEntity(
    @Id
    @Column(nullable = false)
    val name: String
)
