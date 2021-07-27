package com.tsoft.demo.ignitemessage;

import org.apache.ignite.lang.IgniteBiPredicate;

import java.util.UUID;

public interface MessageListener<T> extends IgniteBiPredicate<UUID, T> {
    Topic getTopic();

    boolean apply(UUID uuid, T msg);
}
