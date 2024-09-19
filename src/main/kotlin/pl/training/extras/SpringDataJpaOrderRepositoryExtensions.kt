package pl.training.extras

interface SpringDataJpaOrderRepositoryExtensions {

    fun findByMinTotalValue(minValue: Double): List<OrderEntity>

}
