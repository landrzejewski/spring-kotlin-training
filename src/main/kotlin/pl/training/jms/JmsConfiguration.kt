package pl.training.jms

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.config.DefaultJmsListenerContainerFactory
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.connection.CachingConnectionFactory
import jakarta.jms.ConnectionFactory
import jakarta.jms.JMSException

@Configuration
class JmsConfiguration {

    /*
    @Bean
    fun connectionFactory(): ConnectionFactory {
        val connectionFactory = ActiveMQConnectionFactory()
        connectionFactory.brokerURL = "tcp://localhost:61616"
        return CachingConnectionFactory(connectionFactory)
    }

    @Bean
    fun jmsTemplate(connectionFactory: ConnectionFactory): JmsTemplate {
        val template = JmsTemplate(connectionFactory)
        template.isPubSubDomain = true // topic
        return template
    }

    @Bean
    fun trainingJmsContainerFactory(connectionFactory: ConnectionFactory): DefaultJmsListenerContainerFactory {
        val container = DefaultJmsListenerContainerFactory()
        container.connectionFactory = connectionFactory
        container.concurrency = "1-10"
        container.isPubSubDomain = true // topic
        return container
    }
    */

}
