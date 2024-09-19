package pl.training.payments.utils.model

data class ResultPage<T>(
    val content: List<T>,
    val pageSpec: PageSpec,
    val totalPages: Int
)
