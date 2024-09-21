package pl.training.jms

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Component

@Component
class JmsMessageSender(
    private val jmsTemplate: JmsTemplate
) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        // jmsTemplate.send("messages") { session -> session.createObjectMessage(MessageDto("Hello")) }
        jmsTemplate.convertAndSend("messages", MessageDto("Hello"))
    }

}
