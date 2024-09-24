package pl.training.blog.adapters.output.persistence.jpa

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import pl.training.blog.application.ArticleView
import java.util.UUID

interface SpringDataArticleRepository : JpaRepository<ArticleEntity, UUID> {

    fun existsByTitle(title: String): Boolean

    @Query("select new pl.training.blog.application.ArticleView(a.id, a.title, a.author) from Articles a where a.category = :category")
    fun findByCategory(category: String, pageable: Pageable): Page<ArticleView>

    @Query("select new pl.training.blog.application.ArticleView(a.id, a.title, a.author) from Articles a join a.tags t where t.name in :tags group by a having count (a) = :count")
    fun findByTags(tags: Set<String>, count: Int, pageable: Pageable): Page<ArticleView>

    @Query("select new pl.training.blog.application.ArticleView(a.id, a.title, a.author) from Articles a join a.tags t where a.category = :category and t.name in :tags group by a having count (a) = :count")
    fun findByCategoryAndTags(category: String, tags: Set<String>, count: Int, pageable: Pageable): Page<ArticleView>

}
