package pl.training.payments.infrastructure.output

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import pl.training.payments.application.output.TimeProvider
import java.time.ZoneId
import java.time.ZonedDateTime

//@Primary
@Service
class FakeTimeProvider : TimeProvider {

    override fun getTimestamp() = ZonedDateTime.of(2024, 1, 1, 12, 0, 0, 0, ZoneId.systemDefault())

}
