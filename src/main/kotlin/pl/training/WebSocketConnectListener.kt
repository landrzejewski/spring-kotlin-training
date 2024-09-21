package pl.training

import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectEvent

@Component
class WebSocketConnectListener(
    private val systemMessageSender: SystemMessageSender,
    private val chatGlobalState: ChatGlobalState
) {

    private companion object {
        const val USERNAME_HEADER = "username"
        const val CLIENT_ID_HEADER = "clientId"
        const val PRIVATE_CLIENT_ID_HEADER = "privateClientId"
    }

    private val log = LoggerFactory.getLogger(WebSocketConnectListener::class.java)

    @EventListener
    fun onConnect(event: SessionConnectEvent) {
        val socketId = WebSocketUtils.getSocketId(event)
        val chatUser = createChatUser(event)
        chatGlobalState.put(socketId, chatUser)
        log.info("Socket with id: $socketId is connected (username: ${chatUser.username})")
        systemMessageSender.sendMessageToAll("User ${chatUser.username} is connected")
        systemMessageSender.sendUserInfosToAll()
    }

    private fun createChatUser(event: SessionConnectEvent): ChatUser {
        val username = WebSocketUtils.getNativeHeader(event, USERNAME_HEADER)
        val clientId = WebSocketUtils.getNativeHeader(event, CLIENT_ID_HEADER)
        val privateClientId = WebSocketUtils.getNativeHeader(event, PRIVATE_CLIENT_ID_HEADER)
        return ChatUser(username, clientId, privateClientId)
    }

}
