package pl.training

import org.springframework.beans.factory.annotation.Value
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.TaskScheduler
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class SystemMessageSender(
    @Value("\${main-topic}")
    private var mainTopic: String,

    @Value("\${system-topic}")
    private var systemTopic: String,

    private val messagingTemplate: SimpMessagingTemplate,
    private val chatGlobalState: ChatGlobalState,
    private val taskScheduler: TaskScheduler
) {

    fun sendMessageToAll(text: String) {
        val messageDto = ChatMessageDto(
            text = text,
            sender = SYSTEM_SENDER,
            timestamp = Instant.now(),
            recipients = emptySet()
        )
        messagingTemplate.convertAndSend(mainTopic, messageDto)
    }

    fun sendUserInfosToAll() {
        val userInfoDtos = chatGlobalState.getAll().map { chatUser ->
            UserInfoDto(chatUser.username, chatUser.clientId)
        }
        val executionTime = Instant.now().plusMillis(DEFAULT_SYSTEM_MESSAGE_DELAY_IN_MILLIS)
        taskScheduler.schedule(
            { messagingTemplate.convertAndSend(systemTopic, userInfoDtos) },
            executionTime
        )
    }

    companion object {
        private const val SYSTEM_SENDER = "System"
        private const val DEFAULT_SYSTEM_MESSAGE_DELAY_IN_MILLIS = 1_000L
    }

}
