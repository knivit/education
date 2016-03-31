package com.tsoft.education;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ExploringVolatile {
    public static void main(String[] args) throws InterruptedException {
        ExploringVolatile exploringVolatile = new ExploringVolatile();

        exploringVolatile.start();
    }

    volatile int volatilePrimitiveCounter = 0;
    volatile Integer volatileObjectCounter = 0;
    AtomicInteger atomicInteger = new AtomicInteger(0);

    private void start() throws InterruptedException {
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 100000; i ++) {
            Thread t = new Thread(() -> {
                volatilePrimitiveCounter ++;
                volatileObjectCounter ++;
                atomicInteger.incrementAndGet();
            });
            threads.add(t);
        }

        threads.forEach(Thread::start);

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Results:");
        System.out.println("volatilePrimitiveCounter = " + volatilePrimitiveCounter);
        System.out.println("volatileObjectCounter = " + volatileObjectCounter);
        System.out.println("atomicInteger = " + atomicInteger);
    }
}
