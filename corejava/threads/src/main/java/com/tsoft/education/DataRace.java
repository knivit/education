package com.tsoft.education;

public class DataRace {
    public static void main(String[] args) throws InterruptedException {
        DataRace dataRace = new DataRace();
        dataRace.start();
    }

    // DataRace - a result of code reordering (by JVM or a processor)
    private int A;
    private int B;

    private void start() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            int r = A;
            B = 1;
            System.out.println("t1: r1=" + r);
        });

        Thread t2 = new Thread(() -> {
            int r = B;
            A = 2;
            System.out.println("t2: r2=" + r);
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
