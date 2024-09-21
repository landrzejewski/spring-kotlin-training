package pl.training.scheduling

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Job(private val taskScheduler: TaskScheduler) : ApplicationRunner {

    // https://spring.io/blog/2020/11/10/new-in-spring-5-3-improved-cron-expressions
    // @Scheduled(cron = "@daily")
    // @Scheduled(cron = "0 * * * * *")
    // @Scheduled(fixedRate = 5000)
    fun run() {
        println("Job started")
    }

    override fun run(args: ApplicationArguments) {
        // val scheduleFuture = taskScheduler.scheduleAtFixedRate({ run() }, Duration.ofMillis(5000))
        // val scheduleFuture = taskScheduler.schedule({ run() }, CronTrigger("0 * * * * *"))
        // scheduleFuture.cancel(false)
    }

}
