package pl.training.async

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import java.util.concurrent.Executors

@EnableAsync
@Configuration
class AsyncConfiguration : AsyncConfigurer {

    override fun getAsyncExecutor() = Executors.newFixedThreadPool(5)

    @Bean
    fun importantTaskExecutor() = Executors.newFixedThreadPool(5)

}
