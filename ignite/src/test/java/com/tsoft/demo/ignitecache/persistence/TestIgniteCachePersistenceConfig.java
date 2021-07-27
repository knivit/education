package com.tsoft.demo.ignitecache.persistence;

import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.DataRegionConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestIgniteCachePersistenceConfig {

    private static final String PERSISTED_DATA_REGION_NAME = "persistedRegion";

    @Bean
    public DataRegionConfiguration persistenceRegion() {
        return new DataRegionConfiguration()
            .setName(PERSISTED_DATA_REGION_NAME)
            .setPersistenceEnabled(true)
            .setMetricsEnabled(true);
    }

    @Bean
    public DataStorageConfiguration persistenceStorage(DataRegionConfiguration persistenceRegion) {
        return new DataStorageConfiguration()
            .setDataRegionConfigurations(persistenceRegion);
    }

    @Bean
    public IgniteConfiguration igniteConfigurationA(DataStorageConfiguration persistenceStorage) {
        return new IgniteConfiguration()
            .setIgniteInstanceName("Ignite_A")
            .setClientMode(false)
            .setConsistentId("node-ignite-a")
            .setDataStorageConfiguration(persistenceStorage)
            .setWorkDirectory("C:\\Users\\16715738\\tmp\\TestIgnitePersistenceConfig_A");
    }

    @Bean
    public IgniteConfiguration igniteConfigurationB(DataStorageConfiguration persistenceStorage) {
        return new IgniteConfiguration()
            .setIgniteInstanceName("Ignite_B")
            .setClientMode(false)
            .setConsistentId("node-ignite-b")
            .setDataStorageConfiguration(persistenceStorage)
            .setWorkDirectory("C:\\Users\\16715738\\tmp\\TestIgnitePersistenceConfig_B");
    }

    @Bean
    public CacheConfiguration<Integer, String> atomicCacheConfiguration() {
        return new CacheConfiguration<Integer, String>()
            .setName("CACHE_1")
            .setDataRegionName(PERSISTED_DATA_REGION_NAME)
            .setAtomicityMode(CacheAtomicityMode.ATOMIC) // TRANSACTIONAL увеличивает время put() в 5-6 раз
            // Кластер (R-replicated, P-partitioned)
            //   1 нода - P = R
            //   2 нода - P опасен, т.к. при остановке одной ноды часть кэша будет потеряна и бэкап не спасет (тоже будет потерян)
            //   3 ноды - R примерно равен P (+ 1 backup) по расходу ОЗУ и тоже самое по надежности (ну только если две ноды не упадут)
            //   4 нода - P (+ 1 backup) лучше по расходу ОЗУ, чем R
            // PARTITIONED неверно работает с new ScanQuery().setLocal(true)
            .setCacheMode(CacheMode.REPLICATED)
            .setRebalanceMode(CacheRebalanceMode.ASYNC) // SYNC по времени то же, что и ASYNC; NONE в 3 раза медленнее
            .setBackups(0)
            .setStatisticsEnabled(true);
    }
}
