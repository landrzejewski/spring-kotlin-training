package pl.training.extras

import jakarta.persistence.LockModeType
import jakarta.persistence.QueryHint
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.QueryHints
import org.springframework.data.repository.query.Param
import org.springframework.scheduling.annotation.Async
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.Future
import java.util.stream.Stream

interface SpringDataJpaOrderRepository : /*Repository<OrderEntity, Long>*/ /*CrudRepository<OrderEntity, Long>*/
    JpaRepository<OrderEntity, Long>, SpringDataJpaOrderRepositoryExtensions, JpaSpecificationExecutor<OrderEntity> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(QueryHint(name = "jakarta.persistence.lock.timeout", value = "5000"))
    fun findByTotalValue(value: Double): List<OrderEntity>

    // fun findByTotalValue(value: Double, pageable: Pageable): Page<OrderEntity>

    // LOAD - All attributes specified in entity graph will be treated as Eager, and all attribute not specified will be treated as Lazy
    // FETCH - All attributes specified in entity graph will be treated as Eager, and all attribute not specified use their default/mapped value
    // @EntityGraph(attributePaths = ["items"], type = LOAD)
    @EntityGraph(OrderEntity.WITH_ITEMS)
    @Query("select o from Orders o where o.totalValue = :value")
    fun findByTotalValue(@Param("value") value: Double, pageable: Pageable): Page<OrderEntity>

    @Query("select new pl.training.extras.OrderSummary(o.id, o.totalValue) from Orders o")
    fun findAllSummaries(): List<OrderSummary>

    @Query("select o.id as id, o.totalValue as value from Orders o")
    fun findAllProjections(): List<OrderProjection>

    @Async
    @Query("select o from Orders o")
    fun findAllAsync(): Future<List<OrderEntity>>

    @Query("select o from Orders o")
    fun findAllStream(): Stream<OrderEntity>

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Orders o set o.totalValue = o.totalValue + :value")
    fun applyDiscount(value: Double)

}