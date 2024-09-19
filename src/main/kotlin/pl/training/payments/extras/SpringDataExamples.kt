package pl.training.payments.extras

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional
import pl.training.payments.extras.SearchCriteria.Matcher.EQUAL
import pl.training.payments.extras.SearchCriteria.Matcher.START_WITH
import java.time.Instant

@Transactional
// @Component
class SpringDataExamples(private val repository: SpringDataJpaOrderRepository) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        val id = createExampleOrder()

        println("- findByTotalValue ----------------------------------------------------------")
        println(repository.findByTotalValue(1000.0))
        println("-----------------------------------------------------------------------------")

        println("- findByTotalValue with paging ----------------------------------------------")
        repository.findByTotalValue(1000.0, PageRequest.of(0, 10))
            .forEach { println(it) }
        println("-----------------------------------------------------------------------------")

        println("- findByMinTotalValue -------------------------------------------------------")
        repository.findByMinTotalValue(500.0)
            .forEach { println(it) }
        println("-----------------------------------------------------------------------------")

        println("- findAllSummaries ----------------------------------------------------------")
        repository.findAllSummaries()
            .forEach { println(it) }
        println("-----------------------------------------------------------------------------")

        println("- findAllProjections --------------------------------------------------------")
        repository.findAllProjections()
            .forEach { println(it.getInfo()) }
        println("-----------------------------------------------------------------------------")

        println("- findAllAsync --------------------------------------------------------------")
        val ordersFuture = repository.findAllAsync()
        println("Is done: ${ordersFuture.isDone}")
        ordersFuture.get()
            .forEach { println(it) }
        println("-----------------------------------------------------------------------------")

        println("- findAllStream -------------------------------------------------------------")
        repository.findAllStream()
            .forEach { println(it) }
        println("-----------------------------------------------------------------------------")

        println("- findByExample -------------------------------------------------------------")
        val exampleEntity = OrderEntity(
            id,
            "Test",
            Instant.now(),
            1000.0,
            emptyList()
        )
        val matcher = ExampleMatcher.matching()
            .withIgnorePaths("created")
            .withIgnorePaths("items")
            .withIgnoreCase()
            .withIgnoreNullValues();
        val example = Example.of(exampleEntity, matcher)
        repository.findAll(example)
            .forEach { println(it) }
        println("-----------------------------------------------------------------------------")

        println("- findBySpecification -------------------------------------------------------")
        val specification = CardSpecification(setOf(
            SearchCriteria("name", "Te", START_WITH),
            SearchCriteria("totalValue", 1000, EQUAL)
        ))
        repository.findAll(specification)
            .forEach { println(it) }
        println("-----------------------------------------------------------------------------")

        repository.applyDiscount(100.0)
    }

    private fun createExampleOrder(): Long? {
        val orderEntity = OrderEntity(
            null,
            "Test",
            Instant.now(),
            1000.0,
            listOf(
                OrderItemEntity(null, "Item 1", 1, 100.0),
                OrderItemEntity(null, "Item 2", 2, 900.0)
            )
        )
        return repository.save(orderEntity).id
    }

}