package com.tsoft.demo.igniteservice;

import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.services.ServiceConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestIgniteServiceConfig {

    @Bean
    public IgniteConsistenceIdGenerator consistenceIdGenerator() {
        return new IgniteConsistenceIdGenerator();
    }

    @Bean
    public ServiceConfiguration serviceAConfiguration() {
        return new ServiceConfiguration()
            .setName(IgniteServiceA.NAME)
            .setService(new IgniteServiceAImpl())
            .setMaxPerNodeCount(1)
            .setTotalCount(2);
    }

    @Bean
    public IgniteConfiguration igniteConfigurationA(IgniteConsistenceIdGenerator consistenceIdGenerator, ServiceConfiguration... serviceConfigurations) {
        return new IgniteConfiguration()
            .setIgniteInstanceName("Ignite_A")
            .setConsistentId(consistenceIdGenerator.generateConsistenceId())
            .setServiceConfiguration(serviceConfigurations)
            .setWorkDirectory("C:\\Users\\16715738\\tmp\\TestIgniteServiceConfig_A");
    }

    @Bean
    public IgniteConfiguration igniteConfigurationB(IgniteConsistenceIdGenerator consistenceIdGenerator, ServiceConfiguration... serviceConfigurations) {
        return new IgniteConfiguration()
            .setIgniteInstanceName("Ignite_B")
            .setConsistentId(consistenceIdGenerator.generateConsistenceId())
            .setServiceConfiguration(serviceConfigurations)
            .setWorkDirectory("C:\\Users\\16715738\\tmp\\TestIgniteServiceConfig_B");
    }

    @Bean
    public CacheConfiguration<Integer, String> atomicCacheConfiguration() {
        return new CacheConfiguration<Integer, String>()
            .setName("CACHE_1")
            .setAtomicityMode(CacheAtomicityMode.ATOMIC) // TRANSACTIONAL увеличивает время put() в 5-6 раз
            .setCacheMode(CacheMode.REPLICATED) // PARTITIONED не работает с new ScanQuery().setLocal(true)
            // SYNC на тесте приводит к
            // Caused by: class org.apache.ignite.IgniteCheckedException: Tree is being concurrently destroyed: CACHE_1-p-619##CacheData
            //	at org.apache.ignite.internal.processors.cache.persistence.tree.BPlusTree.checkDestroyed(BPlusTree.java:1090)
            .setRebalanceMode(CacheRebalanceMode.ASYNC)
            .setBackups(0)
            .setStatisticsEnabled(true);
    }
}
