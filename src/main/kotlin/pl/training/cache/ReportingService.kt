package pl.training.cache

import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.logging.Logger

@CacheConfig(cacheNames = ["reports"], keyGenerator = "simpleKeyGenerator")
@Service
class ReportingService {

    private val log: Logger = Logger.getLogger(ReportingService::class.java.name)

    @Cacheable(/*condition = "#month < 6", */ /*unless = "#month < 6"*/)
    fun generateMonthlySalesReport(month: Int, year: Int): Double {
        log.info("Generating monthly sales report")
        return (month + year) * 0.3
    }

    @CacheEvict(/*cacheNames = ["reports"],*/ allEntries = true)
    fun restart() {
        log.info("Resetting monthly sales cache")
    }

    @CacheEvict(key = "#reportId")
    fun resetReport(reportId: String) {
        log.info("Resetting report with id $reportId")
    }

    @CachePut(key = "#reportId")
    fun insertReport(reportId: String): Double {
        log.info("Inserting report with id $reportId")
        return 0.0
    }

}
