package pl.training.payments.infrastructure.output

import org.springframework.stereotype.Service
import pl.training.payments.application.output.TimeProvider
import java.time.ZonedDateTime

@Service
class SystemTimeProvider : TimeProvider {

    override fun getTimestamp() = ZonedDateTime.now()

}
