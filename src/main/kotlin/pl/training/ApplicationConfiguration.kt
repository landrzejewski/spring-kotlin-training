package pl.training

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import pl.training.commons.converters.ZonedDateTimeReadConverter
import pl.training.commons.converters.ZonedDateTimeWriteConverter
import pl.training.payments.application.CardInfoService
import pl.training.payments.application.CardOperationsService
import pl.training.payments.application.output.CardEventPublisher
import pl.training.payments.application.output.CardRepository
import pl.training.payments.application.output.TimeProvider
import pl.training.security.jwt.JwtAuthenticationProvider
import pl.training.security.jwt.JwtService

@Configuration
class ApplicationConfiguration : WebMvcConfigurer {

    // @Scope("prototype")
    @Bean(name = ["cardOperationsService"], initMethod = "initialize", destroyMethod = "destroy")
    fun cardOperationsService(
        cardRepository: CardRepository,
        @Qualifier("systemTimeProvider") timeProvider: TimeProvider,
        eventPublisher: CardEventPublisher
    ) = CardOperationsService(cardRepository, timeProvider, eventPublisher)

    @Bean
    fun cardInfoService(cardRepository: CardRepository) = CardInfoService(cardRepository)

    @Bean
    fun customConversions() =
        MongoCustomConversions(listOf(ZonedDateTimeReadConverter(), ZonedDateTimeWriteConverter()))

    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("login.html").setViewName("login-form")
    }

    @Autowired
    fun configure(
        builder: AuthenticationManagerBuilder,
        userDetailsService: UserDetailsService,
        passwordEncoder: PasswordEncoder,
        jwtService: JwtService
    ) {
        var daoAuthenticationProvider = DaoAuthenticationProvider()
        daoAuthenticationProvider.setUserDetailsService(userDetailsService)
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder)
        builder.authenticationProvider(daoAuthenticationProvider)

        builder.authenticationProvider(JwtAuthenticationProvider(jwtService))
    }

}