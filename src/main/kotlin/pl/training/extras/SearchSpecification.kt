package pl.training.extras

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification
import pl.training.extras.SearchCriteria.Matcher.EQUAL
import pl.training.extras.SearchCriteria.Matcher.NOT_EQUAL
import pl.training.extras.SearchCriteria.Matcher.START_WITH

class CardSpecification(private val searchCriteria: Set<SearchCriteria>) : Specification<OrderEntity> {

    override fun toPredicate(
        root: Root<OrderEntity>,
        query: CriteriaQuery<*>?,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return criteriaBuilder.and(
            *searchCriteria.map { criteria -> map(criteria, root, criteriaBuilder) }.toTypedArray()
        )
    }

    private fun map(criteria: SearchCriteria, root: Root<OrderEntity>, builder: CriteriaBuilder): Predicate {
        val property = root.get<String>(criteria.propertyName)
        val value = criteria.value
        return when (criteria.matcher) {
            EQUAL -> builder.equal(property, value)
            NOT_EQUAL -> builder.notEqual(property, value)
            START_WITH -> builder.like(property, "$value%")
        }
    }

}
