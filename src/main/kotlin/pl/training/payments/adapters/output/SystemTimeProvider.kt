package pl.training.payments.adapters.output

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
import pl.training.payments.application.output.TimeProvider
import java.time.ZonedDateTime

@Scope("singleton")
@Service
class SystemTimeProvider : TimeProvider {

    override fun getTimestamp() = ZonedDateTime.now()

}
