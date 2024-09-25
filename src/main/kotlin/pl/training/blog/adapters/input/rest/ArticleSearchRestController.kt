package pl.training.blog.adapters.input.rest

import pl.training.blog.application.input.ArticleSearch
import pl.training.commons.model.PageSpec
import pl.training.commons.model.ResultPage
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class ArticleSearchRestController(
    private val articleSearch: ArticleSearch,
    private val articleRestMapper: ArticleRestMapper
) {

    @GetMapping("articles/{article-id}")
    fun findById(@PathVariable("article-id") articleId: UUID): ResponseEntity<ArticleDto> {
        val article = articleSearch.findByUid(articleId)
        val articleDto = articleRestMapper.toDto(article)
        return ResponseEntity.ok(articleDto)
    }

    @GetMapping("categories/{category-name}/articles")
    fun findBy(
        @PathVariable("category-name") categoryName: String,
        @RequestParam(required = false, name = "tag") tagNames: Set<String>,
        @RequestParam(required = false, defaultValue = "0") pageNumber: Int,
        @RequestParam(required = false, defaultValue = "10") pageSize: Int
    ): ResponseEntity<ResultPage<ArticleViewDto>> {
        val category = articleRestMapper.toDomainCategory(categoryName)
        val tags = articleRestMapper.toDomain(tagNames)
        val pageSpec = PageSpec(pageNumber, pageSize)
        val resultPage = articleSearch.findByCategoryAndTags(category, tags, pageSpec)
        val resultPageDto = articleRestMapper.toDto(resultPage)
        return ResponseEntity.ok(resultPageDto)
    }

}
