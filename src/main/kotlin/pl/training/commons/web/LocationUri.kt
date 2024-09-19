package pl.training.commons.web

import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

object LocationUri {

    private const val SEGMENT_SEPARATOR = "/"

    @JvmStatic
    fun fromRequestWith(segment: Any): URI {
        return ServletUriComponentsBuilder.fromCurrentRequest()
            .path("$SEGMENT_SEPARATOR$segment")
            .build()
            .toUri()
    }

}
