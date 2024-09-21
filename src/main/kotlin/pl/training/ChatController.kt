package pl.training

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import java.time.Instant

@Controller
class ChatController(
    private val messagingTemplate: SimpMessagingTemplate,
    private val chatGlobalState: ChatGlobalState
) {

    private val log = LoggerFactory.getLogger(ChatController::class.java)

    @Value("\${main-topic}")
    lateinit var mainTopic: String

    @Value("\${private-topic-prefix}")
    lateinit var privateTopicPrefix: String

    @MessageMapping("/chat")
    fun onMessage(chatMessageDto: ChatMessageDto, @Header("simpSessionId") socketId: String) {
        val message = chatMessageDto.copy(timestamp = Instant.now())

        if (message.isBroadcast()) {
            messagingTemplate.convertAndSend(mainTopic, message)
        } else {
            chatGlobalState.get(socketId)?.let { chatUser ->
                sendMessage(chatUser, message)
            }
            chatGlobalState.getAll()
                .filter { chatUser -> message.recipients.contains(chatUser.clientId) }
                .forEach { chatUser -> sendMessage(chatUser, message) }
        }
    }

    private fun sendMessage(chatUser: ChatUser, chatMessageDto: ChatMessageDto) {
        messagingTemplate.convertAndSend("${privateTopicPrefix}${chatUser.privateClientId}", chatMessageDto)
    }

    /*@MessageMapping("/chat")
    @SendTo("/main")
    fun onMessage(chatMessageDto: ChatMessageDto): ChatMessageDto {
        return chatMessageDto.withTimestamp(Instant.now());
    }*/

}
