package pl.training

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.client.RestTemplate
import org.springframework.web.cors.CorsConfiguration
import pl.training.security.KeycloakGrantedAuthoritiesMapper
import pl.training.security.KeycloakJwtGrantedAuthoritiesConverter
import pl.training.security.KeycloakLogoutHandler

@Configuration
class SecurityConfiguration {

    @Bean
    fun corsConfiguration() = CorsConfiguration().apply {
        allowedOrigins = listOf("http://localhost:8080")
        allowedHeaders = listOf("*")
        allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE")
        allowCredentials = true
    }

    @Bean
    fun securityFilterChain(
        http: HttpSecurity, corsConfiguration: CorsConfiguration
    ) = http
        .csrf { it.ignoringRequestMatchers("/api/**") }
        .cors { it.configurationSource { request -> corsConfiguration } }
        .authorizeHttpRequests { it
                .anyRequest().hasRole("ADMIN")
        }
        .oauth2Login { it.userInfoEndpoint(this::userInfoCustomizer) }
        .oauth2ResourceServer { it.jwt(withDefaults()) }
        // .oauth2ResourceServer { it.jwt(this::jwtConfig) }
         .logout { it
             .logoutRequestMatcher(AntPathRequestMatcher ("/logout"))
             .logoutSuccessUrl("/login.html")
             .invalidateHttpSession(true)
             .addLogoutHandler(KeycloakLogoutHandler(RestTemplate()))
         }
         .build()


    @Bean
    fun jwtConfigurer(): JwtAuthenticationConverter {
        var jwtConverter = JwtAuthenticationConverter()
        jwtConverter.setJwtGrantedAuthoritiesConverter(KeycloakJwtGrantedAuthoritiesConverter())
        return jwtConverter
    }

    /*fun jwtConfig(OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer jwtConfigurer) {
        val jwtConverter = JwtAuthenticationConverter()
        jwtConverter.setJwtGrantedAuthoritiesConverter(new KeycloakJwtGrantedAuthoritiesConverter())
        jwtConfigurer.jwtAuthenticationConverter(jwtConverter)
    }*/

    // Client scopes -> Client scope details (roles) -> Mapper details -> Add to userinfo enabled (Keycloak Admin console)
    fun userInfoCustomizer(userInfoEndpointConfig: OAuth2LoginConfigurer<HttpSecurity>.UserInfoEndpointConfig) {
        userInfoEndpointConfig.userAuthoritiesMapper(KeycloakGrantedAuthoritiesMapper())
    }

}


