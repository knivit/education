package com.tsoft.demo.igniteservice;

import org.apache.ignite.services.Service;

public interface IgniteServiceA extends Service {

    String NAME = IgniteServiceA.class.getName();

    void start();

}
