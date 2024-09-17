package pl.training.payments.domain

import java.math.BigDecimal
import java.util.*

data class Money(val amount: BigDecimal, val currency: Currency) {

    init {
        require(amount >= BigDecimal.ZERO)
    }

    constructor(amount: Double, currency: Currency) : this(BigDecimal.valueOf(amount), currency)

    fun add(money: Money): Money {
        checkCurrencyCompatibility(money)
        return Money(amount + money.amount, currency)
    }

    fun subtract(money: Money): Money {
        checkCurrencyCompatibility(money)
        return Money(amount - money.amount, currency)
    }

    fun isGreaterOrEqual(money: Money): Boolean {
        checkCurrencyCompatibility(money)
        return amount.compareTo(money.amount) > -1
    }

    private fun checkCurrencyCompatibility(money: Money) = require(currency == money.currency)

    operator fun plus(money: Money) = add(money)

    operator fun minus(money: Money) = subtract(money)

}
