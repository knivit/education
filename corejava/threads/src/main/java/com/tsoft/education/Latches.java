package com.tsoft.education;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Latches {
    public static void main(String[] args) {
        Latches lt = new Latches();
        lt.execute();
    }

    private static final int THREADS_NUM = 10;

    private final CountDownLatch startGate = new CountDownLatch(1);
    private final CountDownLatch endGate = new CountDownLatch(THREADS_NUM);

    public void execute() {
        for (int i = 0; i < THREADS_NUM; i ++) {
            LatchesTestThread ltt = new LatchesTestThread(i, startGate, endGate);
            ltt.start();
        }

        startGate.countDown();

        try {
            endGate.await();
        } catch (InterruptedException e) {
            System.out.println(String.format("Class %s was interrupted", getClass().getName()));
            e.printStackTrace();
        }

        System.out.println("Done");
    }

    class LatchesTestThread extends Thread {
        private final int threadNo;
        private final CountDownLatch startGate;
        private final CountDownLatch endGate;

        public LatchesTestThread(int threadNo, CountDownLatch startGate, CountDownLatch endGate) {
            this.threadNo = threadNo;
            this.startGate = startGate;
            this.endGate = endGate;
        }

        @Override
        public void run() {
            long beginTick = System.nanoTime();

            try {
                startGate.await();
            } catch (InterruptedException e) {
                System.out.println(String.format("Thread #%d was interrupted during awaiting the Start Gate to open", threadNo));
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            long startTick = System.nanoTime();

            // doing some work
            int sleepTime = new Random().nextInt(10) + 6;
            try {
                sleep(sleepTime * 1000);
            } catch (InterruptedException e) {
                System.out.println(String.format("Thread #%d was interrupted during its work", threadNo));
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }

            long endTick = System.nanoTime();
            System.out.println(String.format("Thread #%d has finished its work [ beginTick=%d startTick=%d sleepTime=%d endTick=%d ]",
                    threadNo, beginTick, startTick, sleepTime, endTick));

            endGate.countDown();
        }
    }
}
