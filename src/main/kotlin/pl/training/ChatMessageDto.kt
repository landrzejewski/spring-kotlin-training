package pl.training

import java.time.Instant

data class ChatMessageDto(
    val sender: String,
    val recipients: Set<String>,
    val text: String,
    val timestamp: Instant?
) {

    fun isBroadcast()= recipients.isEmpty()

}
