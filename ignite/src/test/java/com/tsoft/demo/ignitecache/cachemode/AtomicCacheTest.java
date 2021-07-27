package com.tsoft.demo.ignitecache.cachemode;

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
@ContextConfiguration(classes = TestIgniteCacheModeConfig.class)
public class AtomicCacheTest {

    @Autowired
    private IgniteConfiguration igniteConfigurationA;

    @Autowired
    private IgniteConfiguration igniteConfigurationB;

    @Autowired
    private CacheConfiguration<Integer, String> atomicCacheConfiguration;

    /**
     * Два теста демонстрируют разницу во времени "готовности" кеша на двух нодах
     * 1) atomic - сначала запускает две ноды, потом инициализирует кэш на одной из них
     * Время готовности в 10 раз больше чем во втором тесте
     *
     * 2) rebalance - тест запускает одну ноду и формирует кэш на ней, потом
     * запускает вторую ноду и кэш реплицируется с первой ноды на вторую.
     * Время готовности меньше, чем в первом тесте, в 10 раз
     */

    @Test
    public void atomic() {
        Ignite igniteA = Ignition.getOrStart(igniteConfigurationA);
        IgniteCache<Integer, String> cacheA = igniteA.getOrCreateCache(atomicCacheConfiguration);

        Ignite igniteB = Ignition.getOrStart(igniteConfigurationB);
        IgniteCache<Integer, String> cacheB = igniteB.getOrCreateCache(atomicCacheConfiguration);

        fillCache("cacheA", cacheA);
        printCacheSize("cacheA", cacheA);
        scanCache("cacheA", cacheA);

        printCacheSize("cacheB", cacheB);
        scanCache("cacheB", cacheB);

        // === REPLICATED ===
        // [14:19:27] Topology snapshot [ver=2, locNode=d625aa1f, servers=2, clients=0, state=ACTIVE, CPUs=12, offheap=6.4GB, heap=7.9GB]
        // [14:19:27]   ^-- Baseline [id=0, size=2, online=2, offline=0]
        // 14:20:40.583 [main] cacheA initialized in 73237 ms
        // 14:20:40.618 [main] cacheA localSize = 480464, size = 1000000, ALL size = 2000000, BACKUP size = 1000000, NEAR size = 0, PRIMARY size = 1000000, ONHEAP size = 0, OFFHEAP size = 1000000
        // 14:20:40.983 [main] cacheA scan size = 500000 in 365 ms
        // 14:20:40.995 [main] cacheB localSize = 519536, size = 1000000, ALL size = 2000000, BACKUP size = 1000000, NEAR size = 0, PRIMARY size = 1000000, ONHEAP size = 0, OFFHEAP size = 1000000
        // 14:20:41.349 [main] cacheB scan size = 500000 in 353 ms

        // === PARTITIONED ===
        // 15:09:35.094 [main] INFO cacheA initialized in 71739 ms
        // 15:09:35.135 [main] INFO cacheA localSize = 482422, size = 1000000, ALL size = 1000000, BACKUP size = 0, NEAR size = 0, PRIMARY size = 1000000, ONHEAP size = 0, OFFHEAP size = 1000000
        // 15:09:36.083 [main] INFO cacheA scan size = 500000 in 948 ms
        // 15:09:36.095 [main] INFO cacheB localSize = 517578, size = 1000000, ALL size = 1000000, BACKUP size = 0, NEAR size = 0, PRIMARY size = 1000000, ONHEAP size = 0, OFFHEAP size = 1000000
        // 15:09:36.825 [main] INFO cacheB scan size = 500000 in 730 ms
    }

    @Test
    public void rebalance() {
        Ignite igniteA = Ignition.getOrStart(igniteConfigurationA);
        IgniteCache<Integer, String> cacheA = igniteA.getOrCreateCache(atomicCacheConfiguration);

        fillCache("cacheA", cacheA);
        printCacheSize("cacheA", cacheA);
        scanCache("cacheA", cacheA);

        Ignite igniteB = Ignition.getOrStart(igniteConfigurationB);

        long start = System.currentTimeMillis();
        IgniteCache<Integer, String> cacheB = igniteB.getOrCreateCache(atomicCacheConfiguration);
        log.info("cacheB rebalanced in {} ms", System.currentTimeMillis() - start);
        printCacheSize("cacheB", cacheB);
        scanCache("cacheB", cacheB);

        // === REPLICATED ===
        // Topology snapshot [ver=1, locNode=531c7711, servers=1, clients=0, state=ACTIVE, CPUs=12, offheap=6.4GB, heap=7.9GB]
        //    ^-- Baseline [id=0, size=1, online=1, offline=0]
        // [main] cacheA initialized in 5760 ms
        // [main] cacheA localSize = 1000000, size = 1000000, ALL size = 1000000, BACKUP size = 0, NEAR size = 0, PRIMARY size = 1000000, ONHEAP size = 0, OFFHEAP size = 1000000
        // Topology snapshot [ver=2, locNode=d418b9b7, servers=2, clients=0, state=ACTIVE, CPUs=12, offheap=6.4GB, heap=7.9GB]
        //   ^-- Baseline [id=0, size=2, online=2, offline=0]
        // [main] cacheB rebalanced in 0 ms
        // [main] cacheB localSize = 0, size = 1000000, ALL size = 1000000, BACKUP size = 0, NEAR size = 0, PRIMARY size = 1000000, ONHEAP size = 0, OFFHEAP size = 1000000

        // === PARTITIONED ===
        // [main] cacheA initialized in 6041 ms
        // [main] cacheA localSize = 1000000, size = 1000000, ALL size = 1000000, BACKUP size = 0, NEAR size = 0, PRIMARY size = 1000000, ONHEAP size = 0, OFFHEAP size = 1000000
        // [main] cacheA scan size = 500000 in 367 ms
        // [main] cacheB rebalanced in 0 ms
        // [main] cacheB localSize = 0, size = 1000000, ALL size = 1000000, BACKUP size = 0, NEAR size = 0, PRIMARY size = 1000000, ONHEAP size = 0, OFFHEAP size = 1000000
        // [main] cacheB scan size = 500000 in 2320 ms
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
