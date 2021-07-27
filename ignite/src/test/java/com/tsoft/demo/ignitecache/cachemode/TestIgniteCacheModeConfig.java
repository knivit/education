package com.tsoft.demo.ignitecache.cachemode;

import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestIgniteCacheModeConfig {

    @Bean
    public IgniteConfiguration igniteConfigurationA() {
        return new IgniteConfiguration()
            .setIgniteInstanceName("Ignite_A")
            .setWorkDirectory("C:\\Users\\16715738\\tmp\\TestIgniteCacheConfig_A");
    }

    @Bean
    public IgniteConfiguration igniteConfigurationB() {
        return new IgniteConfiguration()
            .setIgniteInstanceName("Ignite_B")
            .setWorkDirectory("C:\\Users\\16715738\\tmp\\TestIgniteCacheConfig_B");
    }

    @Bean
    public CacheConfiguration<Integer, String> atomicCacheConfiguration() {
        return new CacheConfiguration<Integer, String>()
            .setName("CACHE_1")
            .setAtomicityMode(CacheAtomicityMode.ATOMIC) // TRANSACTIONAL увеличивает время put() в 5-6 раз
            .setCacheMode(CacheMode.REPLICATED) // PARTITIONED не работает с new ScanQuery().setLocal(true)
            .setRebalanceMode(CacheRebalanceMode.ASYNC) // SYNC по времени то же, что и ASYNC; NONE в 3 раза медленнее
            .setBackups(0)
            .setStatisticsEnabled(true);
    }
}
