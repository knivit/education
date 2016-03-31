package com.tsoft.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting ...");
        Ignition.setClientMode(true);
        Ignite ignite = Ignition.start("example-default.xml");

        System.out.println("Creating a cache ...");
        CacheConfiguration cfg = new CacheConfiguration("tsoft-cache");
        IgniteCache<Integer, String> cache = ignite.getOrCreateCache(cfg);

        cache.put(1, "test");
        System.out.println("Exiting ...");
    }
}
