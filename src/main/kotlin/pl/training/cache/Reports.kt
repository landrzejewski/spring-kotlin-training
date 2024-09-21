package pl.training.cache

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.cache.CacheManager
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Component
class Reports(private val reportingService: ReportingService, private val cacheManager: CacheManager) : ApplicationRunner {

    private val log = Logger.getLogger(ReportingService::class.java.name)

    override fun run(args: ApplicationArguments) {
        log.info("execution 1: ${reportingService.generateMonthlySalesReport(1, 2024)}")
        log.info("execution 2: ${reportingService.generateMonthlySalesReport(1, 2024)}")

        /*
        log.info("execution 1: ${reportingService.generateMonthlySalesReport(2, 2024)}")
        reportingService.restart()
        log.info("execution 1: ${reportingService.generateMonthlySalesReport(1, 2024)}")
        log.info("execution 2: ${reportingService.generateMonthlySalesReport(1, 2024)}")
        log.info("execution 1: ${reportingService.generateMonthlySalesReport(2, 2024)}")
        reportingService.resetReport("generateMonthlySalesReport[1, 2024]")
        log.info("execution 1: ${reportingService.generateMonthlySalesReport(1, 2024)}")
        log.info("execution 2: ${reportingService.generateMonthlySalesReport(1, 2024)}")
        log.info("execution 1: ${reportingService.generateMonthlySalesReport(2, 2024)}")
        reportingService.insertReport("generateMonthlySalesReport[1, 2024]")
        log.info("execution 1: ${reportingService.generateMonthlySalesReport(1, 2024)}")
        log.info("execution 2: ${reportingService.generateMonthlySalesReport(1, 2024)}")
        log.info("execution 1: ${reportingService.generateMonthlySalesReport(2, 2024)}")
        */

        val cache = cacheManager.getCache("reports")
        // cache?.clear()
        // cache?.put("generateMonthlySalesReport[1, 2024]", 1.0)
        // cache?.get("generateMonthlySalesReport[2, 2024]", Double::class.java)
    }

}
