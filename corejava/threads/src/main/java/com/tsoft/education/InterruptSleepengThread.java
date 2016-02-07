package com.tsoft.education;

import java.util.Arrays;
import java.util.Optional;

public class InterruptSleepengThread {
    public void start() throws InterruptedException {
        Thread sleeper = new Thread(() -> {
            Thread.currentThread().setName("Sleeper");
            try {
                System.out.println("Thread " + Thread.currentThread().getId() + " says: I'm trying to sleep for 10 sec ...");
                Thread.sleep(10 * 1000);
            } catch (InterruptedException ex) {
                System.out.println("Oops .. Was interrupted");
            }
        });

        Thread interrupter = new Thread(() -> {
            Thread.currentThread().setName("Interrupter");
            System.out.println("Thread " + Thread.currentThread().getId() + " says: I'm an Interrupter !!!");
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // find out the Sleeper and wake it up
            int activeThreadsCount = Thread.activeCount();
            Thread[] activeThreads = new Thread[activeThreadsCount];
            Thread.enumerate(activeThreads);

            Optional<Thread> thread = Arrays.asList(activeThreads).stream()
                    .filter(t -> t.getName().equals("Sleeper"))
                    .findFirst();
            if (thread.isPresent()) {
                thread.get().interrupt();
                System.out.println("Heh ... got Sleeper !");
            } else {
                System.out.println("Hmm ... can not find Sleeper !");
            }
        });

        sleeper.start();
        interrupter.start();

        // wait them
        sleeper.join();
        interrupter.join();
    }

}
