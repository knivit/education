package com.tsoft.demo.igniteservice;

import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.services.ServiceContext;

@Slf4j
public class IgniteServiceAImpl implements IgniteServiceA {

    @IgniteInstanceResource
    private Ignite ignite;

    private volatile boolean cancel = false;

    private int counter = 0;

    @Override
    public void cancel(ServiceContext serviceContext) {
        log.info(
            "\n=======================================" +
            "\ncancel called, ignite {}" +
            "\n=======================================", ignite.configuration().getConsistentId());

        cancel = true;
    }

    @Override
    public void init(ServiceContext serviceContext) throws Exception {
        log.info(
            "\n=======================================" +
            "\ninit called, ignite {}" +
            "\n=======================================", ignite.configuration().getConsistentId());
    }

    @Override
    public void execute(ServiceContext serviceContext) throws Exception {
        log.info(
            "\n=======================================" +
            "\nexecute called, ignite {}" +
            "\n=======================================", ignite.configuration().getConsistentId());
    }

    @Override
    public void start() {
        while (!cancel) {
            counter ++;
            log.info("counter = {}", counter);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
