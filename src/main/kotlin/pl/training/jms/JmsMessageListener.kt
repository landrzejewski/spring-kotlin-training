package pl.training.jms

import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Component
class JmsMessageListener {

    private val log: Logger = Logger.getLogger(JmsMessageListener::class.java.name)

    @JmsListener(destination = "messages")
    fun onMessage(message: MessageDto) {
        log.info("New JMS message: $message")
    }

}
