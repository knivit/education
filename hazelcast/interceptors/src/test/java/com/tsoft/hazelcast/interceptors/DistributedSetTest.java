package com.tsoft.hazelcast.interceptors;

import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class DistributedSetTest {
    private static final String TEST = "Test string";

    @Test
    public void test() {
        GroupConfig groupConfig = new GroupConfig("g1", "pswd1");
        Config config = new Config();
        config.setGroupConfig(groupConfig);

        HazelcastInstance in1 = Hazelcast.newHazelcastInstance(config);
        HazelcastInstance in2 = Hazelcast.newHazelcastInstance(config);

        Set<String> set1 = in1.getSet("Set1");
        set1.add(TEST);

        Set<String> set2 = in2.getSet("Set1");
        assertEquals(TEST, set2.iterator().next());

        in1.shutdown();

        HazelcastInstance in3 = Hazelcast.newHazelcastInstance();
        Set<String> set3 = in3.getSet("Set1");
        assertEquals(TEST, set3.iterator().next());
//        in3.shutdown();

        HazelcastInstance in4 = Hazelcast.newHazelcastInstance();
        in3.shutdown();

        Set<String> set4 = in4.getSet("Set1");
        assertEquals(TEST, set4.iterator().next());
    }
}
