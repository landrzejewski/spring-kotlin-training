package pl.training

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.POST
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.access.ExceptionTranslationFilter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.cors.CorsConfiguration
import pl.training.security.AuthenticationLoggingFilter
import pl.training.security.BasicAuthenticationEntryPoint
import pl.training.security.jwt.JwtAuthenticationFilter

/*AuthenticationManager authenticationManager // Interfejs/kontrakt dla procesu uwierzytelnienia użytkownika
     ProviderManager providerManager // Podstawowa implementacja AuthenticationManager, deleguje proces uwierzytelnienia do jednego z obiektów AuthenticationProvider
         AuthenticationProvider authenticationProvider // Interfejs/kontrakt dla obiektów realizujących uwierzytelnianie z wykorzystaniem konkretnego mechanizmu/implementacji
             DaoAuthenticationProvider daoAuthenticationProvider // Jedna z implementacji AuthenticationProvider, ładuje dane o użytkowniku wykorzystując UserDetailsService i porównuje je z tymi podanymi w czasie logowani
                 UserDetailsService userDetailsService // Interfejs/kontrakt usługi ładującej dane dotyczące użytkownika

 UsersDetailsManager usersDetailsManager Interfejs/kontrakt pochodny UserDetailsService, pozwalający na zarządzanie użytkownikami
     InMemoryUserDetailsManager inMemoryUserDetailsManager // Jedna z implementacji UsersDetailsManager, przechowuje informacje w pamięci

 PasswordEncoder passwordEncoder //Interfejs/kontrakt pozwalający na hashowanie i porównywanie haseł
     BCryptPasswordEncoder bCryptPasswordEncoder //Jedna z implementacji PasswordEncoder

 SecurityContextHolder securityContextHolder // Przechowuje/udostępnia SecurityContext
     SecurityContext securityContext // Kontener przechowujący Authentication
         Authentication authentication // Reprezentuje dane uwierzytelniające jak i uwierzytelnionego użytkownika/system
             UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken // Jedna z implementacji Authentication, zawiera login i hasło jako credentials
                 UserDetails userDetails // Interfejs/kontrakt opisujący użytkownika
                 GrantedAuthority grantedAuthority // Interfejs/kontrakt opisujący role/uprawnienia
                     SimpleGrantedAuthority simpleGrantedAuthority // Jedna z implementacji SimpleGrantedAuthority

 AuthorizationManager authorizationManager // Interfejs/kontrakt dla procesu autoryzacji
     AuthoritiesAuthorizationManager authoritiesAuthorizationManager // Jedna z implementacji AuthorizationManager (role)*/

// @EnableWebSecurity(debug = true)
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true/*, prePostEnabled = true*/)
@Configuration
class SecurityConfiguration {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    fun user() = User
        .withUsername("jan")
        .password(passwordEncoder().encode("123"))
        .roles("ADMIN")
        .authorities("read", "write")
        .build()

    /*@Bean
    fun userDetailsService() = object : UserDetailsService {

        override fun loadUserByUsername(username: String?) =
            if (!username.equals("jan")) throw UsernameNotFoundException(username) else user()

    }*/

    /*@Bean
    fun userDetailsManager(dataSource: DataSource): UserDetailsManager {
        //return InMemoryUserDetailsManager(user())
        val manager = JdbcUserDetailsManager(dataSource)
        manager.setUsersByUsernameQuery("select username, password, enabled from users where username = ?")
        manager.setAuthoritiesByUsernameQuery("select username, authority from authorities where username = ?")
        return manager
    }*/

    @Bean
    fun corsConfiguration() = CorsConfiguration().apply {
        allowedOrigins = listOf("http://localhost:8080")
        allowedHeaders = listOf("*")
        allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE")
        allowCredentials = true
    }

    @Bean
    fun securityFilterChain(
        http: HttpSecurity, corsConfiguration: CorsConfiguration,
        jwtAuthenticationFilter: JwtAuthenticationFilter
    ) = http
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        .addFilterBefore(AuthenticationLoggingFilter(), ExceptionTranslationFilter::class.java)
        .csrf { it.ignoringRequestMatchers("/api/**") }
        .cors { it.configurationSource { request -> corsConfiguration } }
        .authorizeHttpRequests {
            it
                .requestMatchers("/login.html").permitAll()
                .requestMatchers("/api/tokens").permitAll()
                .requestMatchers(POST, "/payments/cards").hasRole("ADMIN")
                //.anyRequest().access(RequestUrlAuthorizationManager())
                .anyRequest().authenticated()
        }
        // .httpBasic(withDefaults())
        .httpBasic { it
                .realmName("Training")
                .authenticationEntryPoint(BasicAuthenticationEntryPoint())
        }
        //.formLogin(withDefaults())
        .formLogin { it
                .loginPage("/login.html")
                .defaultSuccessUrl("/index.html")
            // .usernameParameter("username")
            // .passwordParameter("password")
            // .successHandler(new CustomAuthenticationSuccessHandler())
            // .failureHandler(new CustomAuthenticationFailureHandler())
        }
         .logout { it
             .logoutRequestMatcher(AntPathRequestMatcher ("/logout"))
             .logoutSuccessUrl("/login.html")
             .invalidateHttpSession(true)
         }
         .build()

}


