package pl.training.cache

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.cache.transaction.TransactionAwareCacheManagerProxy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableCaching
@Configuration
class CacheConfiguration {

    @Bean
    fun cacheManager(): CacheManager {
        return TransactionAwareCacheManagerProxy(ConcurrentMapCacheManager("reports"))
    }

    /*
    @Bean
    fun cacheManager(connectionFactory: RedisConnectionFactory): CacheManager {
        val config = RedisCacheConfiguration.defaultCacheConfig()
        config.usePrefix()
        return RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(config)
            .build()
    }

    @Bean
    fun hazelcastInstanceClient(): HazelcastInstance {
        val config = ClientConfig()
        config.clusterName = "training"
        config.networkConfig.addAddress("localhost:5701")
        return HazelcastClient.newHazelcastClient(config)
    }

    @Bean
    fun hazelcastInstanceClient(): HazelcastInstance {
        val config = Config()
        config.networkConfig.portAutoIncrement = true
        config.join.multicastConfig.isEnabled = true
        config.join.multicastConfig.multicastPort = 20000
        return Hazelcast.newHazelcastInstance(config)
    }

    @Bean
    fun cacheManager(hazelcastInstanceClient: HazelcastInstance): CacheManager {
        return HazelcastCacheManager(hazelcastInstanceClient)
    }
    */

}
