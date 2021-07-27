package com.tsoft.demo.igniteservice;

import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CachePeekMode;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestIgniteServiceConfig.class)
public class IgniteServiceTest {

    @Autowired
    private IgniteConfiguration igniteConfigurationA;

    @Autowired
    private IgniteConfiguration igniteConfigurationB;

    @Autowired
    private CacheConfiguration<Integer, String> atomicCacheConfiguration;

    // https://ignite.apache.org/docs/latest/services/services
    @Test
    public void service() {
        Ignite igniteA = Ignition.getOrStart(igniteConfigurationA);
        IgniteCache<Integer, String> cacheA = igniteA.getOrCreateCache(atomicCacheConfiguration);

        //fillCache("cacheA", cacheA);
        printCacheSize("cacheA", cacheA);
        scanCache("cacheA", cacheA);

        Ignite igniteB = Ignition.getOrStart(igniteConfigurationB);
        IgniteCache<Integer, String> cacheB = igniteB.getOrCreateCache(atomicCacheConfiguration);

        printCacheSize("cacheB", cacheB);
        scanCache("cacheB", cacheB);

        IgniteServiceA serviceA = igniteA.services().serviceProxy(IgniteServiceA.NAME, IgniteServiceA.class, false);
        serviceA.start();
    }

    private void scanCache(String name, IgniteCache<?, ?> cache) {
        ScanQuery query = new ScanQuery<>()
            .setFilter((k, v) -> ((Integer)k) % 2 == 0);
            //.setLocal(true); - выигрыша в скорости не дает, однако, если кеш PARTITIONED, то дает неверные результаты (проходит только локальные записи)

        long start = System.currentTimeMillis();
        log.info("{} scan size = {} in {} ms", name, cache.query(query).getAll().size(), System.currentTimeMillis() - start);
    }

    private void fillCache(String name, IgniteCache<Integer, String> cache) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1_000_000; i++) {
            cache.put(i, UUID.randomUUID().toString());
        }
        log.info("{} initialized in {} ms", name, System.currentTimeMillis() - start);
    }

    private void printCacheSize(String name, IgniteCache<?, ?> cache) {
        log.info("{} localSize = {}, size = {}, ALL size = {}, BACKUP size = {}, NEAR size = {}, PRIMARY size = {}, ONHEAP size = {}, OFFHEAP size = {}",
            name,
            cache.localSize(), cache.size(),
            cache.size(CachePeekMode.ALL), cache.size(CachePeekMode.BACKUP), cache.size(CachePeekMode.NEAR),
            cache.size(CachePeekMode.PRIMARY), cache.size(CachePeekMode.ONHEAP), cache.size(CachePeekMode.OFFHEAP));
    }
}
