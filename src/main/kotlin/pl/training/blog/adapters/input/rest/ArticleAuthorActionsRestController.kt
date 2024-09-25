package pl.training.blog.adapters.input.rest

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.training.blog.application.input.ArticleAuthorActions
import pl.training.commons.web.LocationUri
import java.util.UUID

@RequestMapping("articles")
@RestController
class ArticleAuthorActionsRestController(
    private val articleAuthorActions: ArticleAuthorActions,
    private val articleRestMapper: ArticleRestMapper
) {

    @PostMapping
    fun create(@RequestBody @Valid articleTemplateDto: ArticleTemplateDto): ResponseEntity<Unit> {
        val articleTemplate = articleRestMapper.toDomain(articleTemplateDto)
        val articleId = articleAuthorActions.create(articleTemplate)
        val locationUri = LocationUri.fromRequestWith(articleId)
        return ResponseEntity.created(locationUri).build()
    }

    @DeleteMapping("{article-id}")
    fun delete(@PathVariable("article-id") articleId: UUID): ResponseEntity<Unit> {
        articleAuthorActions.delete(articleId)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("{article-id}")
    fun update(@PathVariable("article-id") articleId: UUID, @RequestBody articleUpdateDto: ArticleUpdateDto): ResponseEntity<Unit> {
        val articleUpdate = articleRestMapper.toDomain(articleUpdateDto)
        articleAuthorActions.update(articleId, articleUpdate)
        return ResponseEntity.noContent().build()
    }

    @PutMapping("published/{article-id}")
    fun publish(@PathVariable("article-id") articleId: UUID): ResponseEntity<Unit> {
        articleAuthorActions.publish(articleId)
        return ResponseEntity.noContent().build()
    }

}
