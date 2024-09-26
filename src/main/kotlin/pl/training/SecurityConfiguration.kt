package pl.training

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.GET
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.intercept.AuthorizationFilter
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter
import pl.training.security.ApiKeyAuthenticationFilter

@Configuration
class SecurityConfiguration {

    @Bean
    fun securityFilterChain(
        httpSecurity: HttpSecurity,
        apiKeyAuthenticationFilter: ApiKeyAuthenticationFilter
    ): SecurityFilterChain {
        return httpSecurity
            .addFilterAfter(apiKeyAuthenticationFilter, AuthorizationFilter::class.java)
            .csrf { it.disable() }
            .authorizeHttpRequests { config ->
                config
                    .requestMatchers(GET, "/articles/**").hasAnyRole("USER", "ADMIN")
                    .requestMatchers(GET, "/categories/**").hasAnyRole("USER", "ADMIN")
                    .anyRequest().hasRole("ADMIN")
            }
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun userDetailsManager(passwordEncoder: PasswordEncoder): UserDetailsManager {
        val admin = User.withUsername("jan")
            .password(passwordEncoder.encode("123"))
            .roles("ADMIN")
            .build()
        return InMemoryUserDetailsManager(admin)
    }

}
