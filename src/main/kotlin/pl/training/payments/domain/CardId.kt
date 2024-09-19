package pl.training.payments.domain

import java.util.UUID

@JvmInline
value class CardId(val value: String = UUID.randomUUID().toString())
