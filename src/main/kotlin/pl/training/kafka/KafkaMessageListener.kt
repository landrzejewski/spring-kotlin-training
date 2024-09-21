package pl.training.kafka

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Component
class KafkaMessageListener {

    private val log: Logger = Logger.getLogger(KafkaMessageListener::class.java.name)

    @KafkaListener(topics = ["messages"]/*, containerFactory = "kafkaListenerContainerFactory"*/)
    fun onMessage(message: MessageDto) {
        log.info("New Kafka message: $message")

    }
}
