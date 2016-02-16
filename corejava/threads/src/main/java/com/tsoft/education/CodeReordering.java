package com.tsoft.education;

public class CodeReordering {
    /**
     * Tried to reproduce a case when compilator re-orders the code
     * and failed ...
     */

    public static void main(String[] args) throws InterruptedException {
        CodeReordering codeReordering = new CodeReordering();

        codeReordering.start();
    }

    private void start() throws InterruptedException {
        for (int i = 0; i < 1000; i ++) {
            Thread thread = new Thread(() -> {
                doTest();
            });
            thread.start();
            thread.join();

            a = 0;
            b = 0;
        }
    }

    private volatile int a = 0;
    private int b = 0;

    private void doTest() {
        a = 1;
        b = 1;
        if (b == 1) {
            if (a == 0) System.out.println("Got it !");
        }
    }
}
