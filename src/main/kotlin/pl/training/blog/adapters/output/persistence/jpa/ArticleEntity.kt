package pl.training.blog.adapters.output.persistence.jpa

import jakarta.persistence.CascadeType.*
import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity(name = "Articles")
class ArticleEntity(
    @Id
    val id: UUID,
    @Column(nullable = false, length = 200)
    val title: String,
    val author: String?,
    @Lob
    @Column(length = 4096)
    val content: String?,
    val category: String?,
    val status: String?,
    @ManyToMany(cascade = [PERSIST, MERGE], fetch = FetchType.EAGER)
    val tags: Set<TagEntity> = emptySet(),
    val created: Instant,
    val published: Instant?,
    val likes: Int,
    val dislikes: Int,
    @JoinColumn(name = "article_id")
    @OneToMany(cascade = [ALL], fetch = FetchType.EAGER)
    val comments: List<CommentEntity>
)
