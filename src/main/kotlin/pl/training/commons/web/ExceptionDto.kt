package pl.training.commons.web

import java.time.ZonedDateTime

class ExceptionDto(val description: String, val timestamp: ZonedDateTime = ZonedDateTime.now())