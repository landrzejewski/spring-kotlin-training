package pl.training.payments.domain

@JvmInline
value class CardNumber(val value: String) {

    init {
        require(NUMBER_PATTERN.matches(value))
    }

    override fun toString() = value

    companion object {

        private val NUMBER_PATTERN = "\\d{16,19}".toRegex()

    }

}
