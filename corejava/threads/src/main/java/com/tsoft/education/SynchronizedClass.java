package com.tsoft.education;

public class SynchronizedClass {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(SynchronizedClass.class.getName() + ": 'synchronized' - how it works ?");
        SynchronizedClass synchronizedClass = new SynchronizedClass();
        synchronizedClass.start();
    }

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
        System.out.println("Part 1. Using monitor of the same object");
        SyncMethods syncMethods = new SyncMethods();
        Thread ts1 = new Thread(() -> { syncMethods.m1(); });
        Thread ts2 = new Thread(() -> { syncMethods.m2(); });
        ts1.start(); ts2.start();
        ts1.join(); ts2.join();

        System.out.println("Part 2. Using monitors of two objects");
        SyncMethods sm1 = new SyncMethods();
        SyncMethods sm2 = new SyncMethods();
        Thread tsm1 = new Thread(() -> { sm1.m1(); });
        Thread tsm2 = new Thread(() -> { sm2.m2(); });
        tsm1.start(); tsm2.start();
        tsm1.join(); tsm2.join();

        System.out.println("Part 3. Synchronizing statements");
        SyncStatements ss = new SyncStatements();
        Thread tss1 = new Thread(() -> { ss.m1(); });
        Thread tss2 = new Thread(() -> { ss.m2(); });
        tss1.start(); tss2.start();
        tss1.join(); tss2.join();
    }
}
