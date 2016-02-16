package com.tsoft.education;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }

    private void start() {
        ClassA a = new ClassA();
        ClassB b = new ClassB();

        String aStr = a.toString();
        String bStr = b.toString();
        String nullStr = null;
        System.out.println(aStr + ", " + bStr + ", " + nullStr);
    }

    class ClassA {
        private int val = 2;

        public ClassA() {
            val = 3;
        }

        @Override
        public String toString() {
            return Integer.toString(val);
        }
    }

    class ClassB extends ClassA {
        private int val = 4;

        public ClassB() {
            val = 10;
        }
    }
}
