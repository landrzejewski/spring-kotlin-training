package pl.training.payments.extras

interface SpringDataJpaOrderRepositoryExtensions {

    fun findByMinTotalValue(minValue: Double): List<OrderEntity>

}
