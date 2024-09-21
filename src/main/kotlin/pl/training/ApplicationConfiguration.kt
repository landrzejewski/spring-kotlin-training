package pl.training

import com.zaxxer.hikari.HikariDataSource
import jakarta.persistence.EntityManagerFactory
import org.hibernate.jpa.HibernatePersistenceProvider
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.config.PropertiesFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.core.io.ClassPathResource
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import pl.training.payments.application.CardInfoService
import pl.training.payments.application.CardOperationsService
import pl.training.payments.application.output.CardEventPublisher
import pl.training.payments.application.output.CardRepository
import pl.training.payments.application.output.TimeProvider
import java.util.Properties
import javax.sql.DataSource

@PropertySource("classpath:jdbc.properties")
@EnableJpaRepositories
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ComponentScan
@Configuration
internal open class ApplicationConfiguration {

    // @Scope("prototype")
    @Bean(name = ["cardOperationsService"], initMethod = "initialize", destroyMethod = "destroy")
    open fun cardOperationsService(
        cardRepository: CardRepository,
        @Qualifier("systemTimeProvider") timeProvider: TimeProvider,
        eventPublisher: CardEventPublisher
    ) = CardOperationsService(cardRepository, timeProvider, eventPublisher)

    @Bean
    open fun cardInfoService(cardRepository: CardRepository) = CardInfoService(cardRepository)

    @Bean
    open fun dataSource(environment: Environment): DataSource =
        HikariDataSource()
            .apply {
                username = environment.getProperty("database.username")
                password = environment.getProperty("database.password")
                jdbcUrl = environment.getProperty("database.url")
                driverClassName = environment.getProperty("database.driver")
                maximumPoolSize = environment.getProperty("database.pool-size")?.toInt() ?: 10
            }

    @Bean
    open fun jpaProperties() =
        PropertiesFactoryBean()
            .apply {
                setLocation(ClassPathResource("jpa.properties"))
            }

    @Bean
    open fun entityManagerFactory(dataSource: DataSource, jpaProperties: Properties): LocalContainerEntityManagerFactoryBean =
        LocalContainerEntityManagerFactoryBean()
            .apply {
                setDataSource(dataSource)
                setPackagesToScan("pl.training")
                setJpaProperties(jpaProperties)
                setPersistenceProviderClass(HibernatePersistenceProvider::class.java)
            }

    @Bean
    open fun transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
        return JpaTransactionManager(entityManagerFactory)
    }

}