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

    private val caches = ConcurrentHashMap<String, Cache<String, Any>>()
    var cacheSupplier = Function<Int, Cache<String, Any>> { LinkedHashMapCache(it) }
    private val log = Logger.getLogger(CacheAspect::class.java.name)

    @Around("@annotation(fromCache)")
    fun read(joinPoint: ProceedingJoinPoint, fromCache: FromCache): Any {
        val cacheName = fromCache.value
        val key = generateKey(joinPoint)
        caches.putIfAbsent(cacheName, cacheSupplier.apply(fromCache.capacity))
        val cache = caches[cacheName]!!
        val value = cache.get(key)
        if (value != null) {
            log.info("Cache hit")
            return value
        }
        val result = joinPoint.proceed()
        cache.put(key, result)
        return result
    }

    private fun generateKey(joinPoint: JoinPoint) = joinPoint.args.joinToString(separator = "") { it.toString() }

}
