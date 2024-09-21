package pl.training

import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionDisconnectEvent

@Component
class WebSocketDisconnectListener(
    private val systemMessageSender: SystemMessageSender,
    private val chatGlobalState: ChatGlobalState
) {

    private val log = LoggerFactory.getLogger(WebSocketDisconnectListener::class.java)

    @EventListener
    fun onDisconnect(event: SessionDisconnectEvent) {
        val socketId = WebSocketUtils.getSocketId(event)
        chatGlobalState.get(socketId)?.let { chatUser ->
            chatGlobalState.remove(socketId)
            log.info("Socket with id: $socketId is disconnected (username: ${chatUser.username})")
            systemMessageSender.sendMessageToAll("User ${chatUser.username} is disconnected")
        }
        systemMessageSender.sendUserInfosToAll()
    }

}
