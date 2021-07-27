package com.tsoft.demo.ignitemessage.example;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.apache.ignite.resources.IgniteInstanceResource;

import java.util.UUID;

public class MyClusterJob implements IgniteBiPredicate<UUID, Integer> {

    private Ignite ignite;

    // Inject Ignite instance.
    @IgniteInstanceResource
    public void setIgnite(Ignite ignite) {
        this.ignite = ignite;
    }

    //@Override
    public void cancel() {
        System.out.println("cancel");
    }

    //@Override
    public Object execute() throws IgniteException {
        System.out.println("execute");
        return null;
    }

    @Override
    public boolean apply(UUID uuid, Integer s) {
        System.out.println("apply");
        return true;
    }
}
