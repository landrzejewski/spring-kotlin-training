package pl.training.kafka

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.*
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration
class KafkaConfiguration {

    /*@Bean
    fun producerFactory(): ProducerFactory<String, MessageDto> {
        val serializer = JsonSerializer<MessageDto>()
            .apply { isAddTypeInfo = true }
        val properties = hashMapOf<String, Any>(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092"
        )
        return DefaultKafkaProducerFactory(properties, StringSerializer(), serializer)
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<String, MessageDto> {
        val deserializer = JsonDeserializer(MessageDto::class.java)
            .apply { addTrustedPackages("pl.training.shop.kafka") }
        val properties = hashMapOf<String, Any>(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
            ConsumerConfig.GROUP_ID_CONFIG to "chat"
        )
        return DefaultKafkaConsumerFactory(properties, StringDeserializer(), deserializer)
    }

    @Bean
    fun mainTopic() = TopicBuilder.name("messages").build()

    @Bean
    fun kafkaTemplate(producerFactory: ProducerFactory<String, MessageDto>) =
        KafkaTemplate(producerFactory)

    @Bean
    fun kafkaListenerContainerFactory(consumerFactory: ConsumerFactory<String, MessageDto>) =
        ConcurrentKafkaListenerContainerFactory<String, MessageDto>()
            .apply { setConsumerFactory(consumerFactory) }*/

}
