package pl.training.async

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future

@Component
class AsyncLogStatusTask {

    private val log = LoggerFactory.getLogger(AsyncLogStatusTask::class.java)

    @Async
    fun task() {
        Thread.sleep(1_000)
        log.info("Current status: ok (${Instant.now()}, ${Thread.currentThread().name})")
    }

    @Async("importantTaskExecutor")
    fun taskWithResult(): Future<String> {
        Thread.sleep(1_000)
        log.info("Current status: ok (${Instant.now()}, ${Thread.currentThread().name})")
        return CompletableFuture.completedFuture("OK")
    }

}
