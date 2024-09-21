package pl.training.async

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class TaskRunner(private val task: AsyncLogStatusTask) : ApplicationRunner {

    private val log = LoggerFactory.getLogger(TaskRunner::class.java)

    override fun run(args: ApplicationArguments) {
        log.info("Executing async task (${Thread.currentThread().name})")
        task.task()
        log.info("After task execution (${Thread.currentThread().name})")

        log.info("Executing async task (${Thread.currentThread().name})")
        val resultFuture = task.taskWithResult()
        log.info("Is finished: ${resultFuture.isDone}")
        log.info("After task execution (${Thread.currentThread().name})")
        log.info("Result: ${resultFuture.get()}")
    }

}
