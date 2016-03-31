package com.tsoft.education;

public class WaitNotify {
    public static void main(String[] args) throws InterruptedException {
        WaitNotify waitNotify = new WaitNotify();
        waitNotify.start();
    }

    private Object lock = new Object();
    private volatile int condition = 10;

    private void start() throws InterruptedException {
        Thread awaiter = new Thread(() -> {
            synchronized(lock) {
                System.out.println("Awaiter: I'm started. Going to check condition ...");
                while (condition == 10) {
                    try {
                        System.out.println("Awaiter: condition is false, doing lock.wait()");
                        lock.wait(1000);
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
            try {
                // 'synchronized' must be here, or we'll receive an java.lang.IllegalMonitorStateException
                // also this is prevents from data race и memory visibility
                synchronized (lock) {
                    System.out.println("Notifier: I'm started and in 'synchronized' section now. Setting the condition to true");
                    Thread.sleep(5000);
                    condition = 5;

                    System.out.println("Notifier: Sending notify() on 'lock' object");
                    Thread.sleep(5000);
                    lock.notifyAll();

                    System.out.println("Notifier: done, exiting the 'synchronized' section...");
                    Thread.sleep(5000);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        awaiter.start();
        Thread.sleep(5000); // make sure Awaiter is started
        notifier.start();

        awaiter.join();
        notifier.join();
    }
}
