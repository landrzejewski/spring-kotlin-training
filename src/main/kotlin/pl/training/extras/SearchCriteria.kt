package pl.training.extras

data class SearchCriteria(
    val propertyName: String,
    val value: Any?,
    val matcher: Matcher
) {
    enum class Matcher {
        EQUAL, NOT_EQUAL, START_WITH
    }
}
