package com.tsoft.education;

public class SynchronizedClass {
    class SyncMethods {

        /**
         *   Two 'synchronized' methods - they won't execute in parallel,
         *   as object's monitor (i.e. "syncMethods" object) is one for both
         *
         */

        synchronized void m1() { // the same as synchronized(this)
            System.out.println("m1, sleep 5 sec");
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        synchronized void m2() { // the same as synchronized(this)
            System.out.println("m2, sleep 5 sec");
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class SyncStatements {
        private final Object lock1 = new Object();
        private final Object lock2 = new Object();

        void m1() {
            synchronized(lock1) {
                System.out.println("m1, sleep 5 sec");
                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        void m2() {
            synchronized(lock2) {
                System.out.println("m2, sleep 5 sec");
                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void start() throws InterruptedException {
        System.out.println("Part 1. Synchronizing methods");
        SyncMethods syncMethods = new SyncMethods();
        Thread t1 = new Thread(() -> { syncMethods.m1(); });
        Thread t2 = new Thread(() -> { syncMethods.m2(); });
        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println("Part 2. Synchronizing statements");
        SyncStatements syncStatements = new SyncStatements();
        Thread t3 = new Thread(() -> { syncStatements.m1(); });
        Thread t4 = new Thread(() -> { syncStatements.m2(); });
        t3.start(); t4.start();
        t3.join(); t4.join();
    }
}
