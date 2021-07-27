package com.tsoft.demo.igniteservice;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

public class IgniteConsistenceIdGenerator {

    private String hostName = "unknown";

    public IgniteConsistenceIdGenerator() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            hostName = addr.getHostName();
        } catch (UnknownHostException ex) {
        }
    }

    public String generateConsistenceId() {
        return  hostName + "_" + UUID.randomUUID().toString();
    }
}
