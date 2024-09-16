package pl.training.payments.domain

@JvmInline
value class CardNumber(val value: String) {

    init {
        require(numberPattern.matches(value))
    }

    override fun toString() = value

    companion object {

        private val numberPattern = "\\d{16,19}".toRegex()

    }

}
