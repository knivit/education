package com.tsoft.education;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PutResultsToMap {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(PutResultsToMap.class.getName() + ": Create many threads; each putting it's result to ConcurrentHashMap");
        PutResultsToMap putResultsToMap = new PutResultsToMap();
        putResultsToMap.startThreads(100000);
    }

    public void startThreads(int threadCount) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadCount; i ++) {
            Calc calc = new Calc();
            Thread thread = new Thread(calc);
            threads.add(thread);

            thread.start();
        }

        // wait for threads to end
        for (Thread thread : threads) {
            thread.join();
        }

        // print the result
        long minId = results.keySet().stream().min(Long::compare).get();
        long maxId = results.keySet().stream().max(Long::compare).get();

        // calc sum
        int sum = results.values().stream().mapToInt(r -> r.value).sum();
        System.out.println("Min thread ID: " + minId + ", Max thread ID: " + maxId + ", Sum: " + sum);
    }

    class Result {
        public int value;
    }

    // HashMap isn't suitable for concurrent updates !
    public Map<Long, Result> results = new ConcurrentHashMap<>();

    class Calc implements Runnable {
        @Override
        public void run() {
            Result result = new Result();
            result.value = 1;

            long threadId = Thread.currentThread().getId();
            results.put(threadId, result);
        }
    }
}
