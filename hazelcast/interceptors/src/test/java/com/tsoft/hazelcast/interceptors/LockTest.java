package com.tsoft.hazelcast.interceptors;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.junit.Test;

import java.util.concurrent.locks.Lock;

public class LockTest {
    @Test
    public void test() {
        HazelcastInstance hi = Hazelcast.newHazelcastInstance();
        Lock lock = hi.getLock("MPN cluster");
        try {

        } finally {
            lock.unlock();
        }
    }
}
