package com.tsoft.education;

public class Main {
    public static void main(String[] args) throws InterruptedException {
 /*       System.out.println("=== 1 === Create many threads; each putting it's result to ConcurrentHashMap");
        PutResultsToMap putResultsToMap = new PutResultsToMap();
        putResultsToMap.startThreads(100000);

        System.out.println("=== 2 === Sleeping thread is interrupted by another thread");
        InterruptSleepengThread interruptSleepengThread = new InterruptSleepengThread();
        interruptSleepengThread.start();
  */
        System.out.println("=== 3 === 'synchronized' - how it works ?");
        SynchronizedClass synchronizedClass = new SynchronizedClass();
        synchronizedClass.start();
    }
}
