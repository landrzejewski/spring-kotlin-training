package pl.training

import org.springframework.messaging.Message
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent

object WebSocketUtils {

    private const val SIMP_CONNECT_MESSAGE_HEADER = "simpConnectMessage"

    fun getSocketId(event: AbstractSubProtocolEvent) =
        stompHeaderAccessor(event).sessionId.orEmpty()

    fun getAttributes(event: AbstractSubProtocolEvent) =
        stompHeaderAccessor(event).sessionAttributes ?: emptyMap()

    fun getNativeAttributes(event: AbstractSubProtocolEvent): Map<String, Any> {
        val simpConnectMessage = getStompMessage(event)
        return StompHeaderAccessor.wrap(simpConnectMessage).sessionAttributes ?: emptyMap()
    }

    fun getNativeHeader(event: AbstractSubProtocolEvent, headerName: String): String {
        val simpConnectMessage = event.message
        return StompHeaderAccessor.wrap(simpConnectMessage).getFirstNativeHeader(headerName) ?: ""
    }

    fun stompHeaderAccessor(event: AbstractSubProtocolEvent) =
        StompHeaderAccessor.wrap(event.message)

    fun getStompMessage(event: AbstractSubProtocolEvent) =
        stompHeaderAccessor(event).getHeader(SIMP_CONNECT_MESSAGE_HEADER) as Message<*>

}
