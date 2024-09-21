package pl.training.jms

import java.io.Serializable

data class MessageDto(
    val body: String
) : Serializable
