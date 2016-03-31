package com.tsoft.education;

import java.util.concurrent.Semaphore;

public class DeadLock {
    public static void main(String[] args) throws InterruptedException {
        DeadLock deadLock = new DeadLock();
        deadLock.start();
    }

    private Object lock1 = new Object();
    private Object lock2 = new Object();
    private Semaphore sem = new Semaphore(1);

    private void start() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("t1: Got lock1");
                try {
                    sem.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }

                System.out.println("t1: Trying to get lock2");
                sem.release();
                synchronized (lock2) {
                    System.out.println("t1: Got lock2");
                }

            }
            System.out.println("t1: Exiting");
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("t2: Got lock2");
                try {
                    sem.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }

                System.out.println("t2: Trying to get lock1");
                sem.release();
                synchronized (lock1) {
                    System.out.println("t2: Got lock1");
                }

            }
            System.out.println("t1: Exiting");
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
