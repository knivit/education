package com.tsoft.demo.ignitecache.persistence;

import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CachePeekMode;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.cluster.ClusterState;
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
@ContextConfiguration(classes = TestIgniteCachePersistenceConfig.class)
public class PersistenceCacheTest {

    @Autowired
    private IgniteConfiguration igniteConfigurationA;

    @Autowired
    private IgniteConfiguration igniteConfigurationB;

    @Autowired
    private CacheConfiguration<Integer, String> atomicCacheConfiguration;

    @Test
    public void two_nodes() throws Exception {
        Ignite igniteA = Ignition.getOrStart(igniteConfigurationA);
        Ignite igniteB = Ignition.getOrStart(igniteConfigurationB);

        // https://www.gridgain.com/resources/blog/apache-ignites-baseline-topology-explained
        // https://ignite.apache.org/docs/latest/tools/control-script#activating-cluster
        igniteA.cluster().state(ClusterState.ACTIVE);
        igniteB.cluster().state(ClusterState.ACTIVE);

        IgniteCache<Integer, String> cacheA = igniteA.getOrCreateCache(atomicCacheConfiguration);
        IgniteCache<Integer, String> cacheB = igniteB.getOrCreateCache(atomicCacheConfiguration);

        fillCache("cacheA", cacheA);
        printCacheSize("cacheA", cacheA);
        scanCache("cacheA", cacheA);

        printCacheSize("cacheB", cacheB);
        scanCache("cacheB", cacheB);

        // === REPLICATED ===
        // [main] cacheA initialized in 82608 ms
        // [main] cacheA localSize = 515623, size = 1000000, ALL size = 2000000, BACKUP size = 1000000, NEAR size = 0, PRIMARY size = 1000000, ONHEAP size = 0, OFFHEAP size = 1000000
        // [main] cacheA scan size = 500000 in 424 ms
        // [main] cacheB localSize = 484377, size = 1000000, ALL size = 2000000, BACKUP size = 1000000, NEAR size = 0, PRIMARY size = 1000000, ONHEAP size = 0, OFFHEAP size = 1000000
        // [main] cacheB scan size = 500000 in 420 ms
        // Размер папок игнайта - обе по 962 Мб

        // Второй запуск этого теста - fillCache закомментирован
        // [main] cacheA localSize = 515623, size = 1000000, ALL size = 2000000, BACKUP size = 1000000, NEAR size = 0, PRIMARY size = 1000000, ONHEAP size = 0, OFFHEAP size = 1000000
        // [main] cacheA scan size = 500000 in 545 ms
        // [main] cacheB localSize = 484377, size = 1000000, ALL size = 2000000, BACKUP size = 1000000, NEAR size = 0, PRIMARY size = 1000000, ONHEAP size = 0, OFFHEAP size = 1000000
        // [main] cacheB scan size = 500000 in 403 ms
        // Размер папок игнайта - обе по 1,06 Гб

        // Третий запуск - с fillCache
        // Размер папок игнайта - обе по 1,25 Гб

        // Четвертый запуск - с fillCache
        // Размер папок игнайта - обе по 1,25 Гб !!!

        // Т.е. размер на диске не менятеся, хотя содержимое кэша изменилось (UUID.randomUUID().toString())

        // Пятый запуск - поменял конфигурацию кэша на PARTITIONED и закомментировал fillCache
        // Кластер отработал без ошибок, размер папок не поменялся

        // Шестой запуск - оставил конфигурацию кэша на PARTITIONED и вернул fillCache
        // Кластер отработал без ошибок, размер папок не поменялся

        // Седьмой запуск - вернул конфигурацию кэша на REPLICATED и закомментировал fillCache
        // Кластер отработал без ошибок, размер папок не поменялся

        // === PARTITIONED ===
        // [main] cacheA initialized in 81570 ms
        // [main] cacheA localSize = 486319, size = 1000000, ALL size = 1000000, BACKUP size = 0, NEAR size = 0, PRIMARY size = 1000000, ONHEAP size = 0, OFFHEAP size = 1000000
        // [main] cacheA scan size = 500000 in 963 ms
        // [main] cacheB localSize = 513681, size = 1000000, ALL size = 1000000, BACKUP size = 0, NEAR size = 0, PRIMARY size = 1000000, ONHEAP size = 0, OFFHEAP size = 1000000
        // [main] cacheB scan size = 500000 in 805 ms
        // Размер папок игнайта - обе по 770 Мб

        // Второй запуск этого теста - fillCache закомментирован
        // Размер папок игнайта - обе по 840 Мб

        // Третий запуск - с fillCache
        // Размер папок игнайта - 0,968 и 1,01 Гб

        // Четвертый запуск - с fillCache
        // Размер папок игнайта - обе по 1,13 Гб

        // Пятый запуск - поменял конфигурацию кэша на REPLICATED и закомментировал fillCache
        // Кластер отработал без ошибок, размер папок не поменялся

        // Шестой запуск - оставил конфигурацию кэша на REPLICATED и вернул fillCache
        // Кластер отработал без ошибок, размер папок - обе по 1,19 Гб

        // Седьмой запуск - вернул конфигурацию кэша на PARTITIONED и закомментировал fillCache
        // Кластер отработал без ошибок, размер папок не поменялся
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

