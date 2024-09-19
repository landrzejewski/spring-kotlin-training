package pl.training.extras

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext

class SpringDataJpaOrderRepositoryImpl : SpringDataJpaOrderRepositoryExtensions {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findByMinTotalValue(minValue: Double): List<OrderEntity> {
        return entityManager.createNamedQuery(OrderEntity.BY_MIN_TOTAL_VALUE, OrderEntity::class.java)
            .setParameter("minValue", minValue)
            .resultList
    }


}