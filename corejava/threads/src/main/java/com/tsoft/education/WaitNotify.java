package com.tsoft.education;

public class WaitNotify {
    public static void main(String[] args) throws InterruptedException {
        WaitNotify waitNotify = new WaitNotify();
        waitNotify.start();
    }

    private Object lock = new Object();
    private int condition = 10;

    private void start() throws InterruptedException {
        Thread awaiter = new Thread(() -> {
            synchronized(lock) {
                System.out.println("Awaiter: I'm started. Going to check condition ...");
                while (condition == 10) {
                    try {
                        System.out.println("Awaiter: condition is false, doing lock.wait()");
                        lock.wait();
                        System.out.println("Awaiter: Got a notification, check the condition again");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
                System.out.println("Awaiter: Condition is true, exiting ...");
            }
        });

        Thread notifier = new Thread(() -> {
            // 'synchronized' must be here, or we'll receive an java.lang.IllegalMonitorStateException
            // also this is prevents from data race и memory visibility
            synchronized(lock) {
                System.out.println("Notifier: I'm started. setting the condition to true");
                condition = 5;

                System.out.println("Notifier: Sending notify() on 'lock' object");
                lock.notifyAll();

                System.out.println("Notifier: done, exiting ...");
            }
        });

        awaiter.start();
        Thread.sleep(100); // make sure Awaiter is started
        notifier.start();

        awaiter.join();
        notifier.join();
    }
}
