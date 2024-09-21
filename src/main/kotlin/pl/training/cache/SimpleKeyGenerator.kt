package pl.training.cache

import org.slf4j.LoggerFactory
import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.stereotype.Component
import java.lang.reflect.Method

@Component
class SimpleKeyGenerator : KeyGenerator {

    private val log = LoggerFactory.getLogger(SimpleKeyGenerator::class.java)

    override fun generate(target: Any, method: Method, vararg params: Any): Any {
        val key = method.name + params.joinToString(", ") { it.toString() }
        log.info("Key: $key")
        return key
    }

}
