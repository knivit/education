package com.tsoft.education.yandex;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();

        System.out.println("First list, 3 elements without loops");
        main.start(main.buildListWithoutLoops());

        System.out.println("\nSecond list, 3 elements with a loop: a second element refer itself");
        main.start(main.buildListWithLoops());
    }

    class Element {
        Element next;
    }

    private void start(Element first) {
        int n = 0;
        Element curr = first;
        while (curr != null) {
            n ++;
            curr = curr.next;
            if (isLoop(first, curr, n)) {
                System.out.println("A loop is found !");
                break;
            }
        }

        System.out.println(n);
    }

    private boolean isLoop(Element first, Element curr, int n) {
        Element loop = first;
        while (loop != null && n > 0 && loop != curr) {
            n --;
            loop = loop.next;
        }
        if (n != 0) return true;
        return false;
    }

    private Element buildListWithLoops() {
        Element e1 = new Element();
        Element e2 = new Element();
        Element e3 = new Element();
        e1.next = e2;
        e2.next = e2;
        e3.next = null;

        return e1;
    }

    private Element buildListWithoutLoops() {
        Element e1 = new Element();
        Element e2 = new Element();
        Element e3 = new Element();
        e1.next = e2;
        e2.next = e3;
        e3.next = null;

        return e1;
    }
}
