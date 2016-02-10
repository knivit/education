package com.tsoft.education;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockWithTimeout {
    private Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockWithTimeout reentrantLock = new ReentrantLockWithTimeout();
        reentrantLock.start();

        System.out.println("Exiting ...");
    }

    private void start() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            doWork();
        });
        t1.setName("t1");

        Thread t2 = new Thread(() -> {
            doWork();
        });
        t2.setName("t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    private void doWork() {
        String threadName = Thread.currentThread().getName();
        try {
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                try {
                    System.out.println(threadName + " got the lock. Sleeping for 10 sec ...");
                    Thread.sleep(10 * 1000);
                    System.out.println(threadName + " Awoke !");
                } finally {
                    lock.unlock();
                }
            } else System.out.println(threadName + " couldn't get the lock, work is not done");
        } catch (InterruptedException ex) {
            System.out.println(threadName + " was interrupted");
        }
    }
}
