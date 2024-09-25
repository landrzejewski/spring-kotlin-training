package pl.training.commons.web

import java.time.ZonedDateTime

class ExceptionDto(
    val timestamp: ZonedDateTime = ZonedDateTime.now(),
    val description: String
)
