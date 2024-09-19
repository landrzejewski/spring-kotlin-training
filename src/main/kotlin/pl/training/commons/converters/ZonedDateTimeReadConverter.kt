package pl.training.commons.converters

import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.Date

@Component
class ZonedDateTimeReadConverter : Converter<Date, ZonedDateTime> {
    override fun convert(date: Date) = date.toInstant().atZone(ZoneOffset.UTC)
}
