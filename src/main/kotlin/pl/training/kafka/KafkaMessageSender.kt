package pl.training.kafka

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaMessageSender(
    private val kafkaTemplate: KafkaTemplate<String, MessageDto>
) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        kafkaTemplate.send("messages", MessageDto().apply { body = "Hello" })
    }

}
