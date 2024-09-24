package pl.training.commons.aop

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Function
import java.util.logging.Logger

@Aspect
@Component
class CacheAspect {

    private val caches: MutableMap<String, Cache<String, Any>> = ConcurrentHashMap()
    var cacheSupplier: Function<Int, Cache<String, Any>> = Function { LinkedHashMapCache(it) }
    private val log: Logger = Logger.getLogger(CacheAspect::class.java.name)

    @Around("@annotation(fromCache)")
    @Throws(Throwable::class)
    fun read(joinPoint: ProceedingJoinPoint, fromCache: FromCache): Any {
        val cacheName = fromCache.value
        val key = generateKey(cacheName, joinPoint)
        caches.putIfAbsent(cacheName, cacheSupplier.apply(fromCache.capacity))
        val cache = caches[cacheName]
        val value = cache?.get(key)
        if (value != null) {
            log.info("Cache hit")
            return value
        }
        val result = joinPoint.proceed()
        cache?.put(key, result)
        return result
    }

    private fun generateKey(cacheName: String, joinPoint: JoinPoint) =
        cacheName + joinPoint.args.joinToString(separator = "") { it.toString() }

}
