package com.tsoft.education;

public class RaceCondition {
    public static void main(String[] args) throws InterruptedException {
        RaceCondition raceCondition = new RaceCondition();
        raceCondition.start();
    }

    private boolean done;

    private void start() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            int i = 0;
            System.out.println("t1: I'm waiting for 'done'");
            while (!done) i ++;
            System.out.println("t1: done is true !");
        });

        t1.start();
        System.out.println("Thread t1 is started");
        Thread.sleep(200);
        done = true;
        System.out.println("Now 'done' is true, so t1 must end, but ... it never goes ! (need to make done volatile)");

        // the same as t1.join()
        synchronized (t1) { t1.wait(); }
    }
}
