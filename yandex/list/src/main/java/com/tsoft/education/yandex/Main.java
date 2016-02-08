package com.tsoft.education.yandex;

/*
An interview has time limit 60 min.
Apply date: 07 feb 2016

1. What is the difference between HashMap and TreeMap ?

Answer: TreeMap keeps keys sorted. HashMap doesn't.

2. Given: a singly linked list with possible loops.
Find out a number of elements or report there is a loop.
Do not allocate any arrays.

Tests:
1) A -> B -> null. Must return 2.
2) A -> B -> A. Must return "A loop found".

Answer: Iterate through the list, increasing the counter.
For each element do check (starting from the first) is it appeared before the counter is 0.

3. Given: ArrayList<ArrayList<Integer>>.
The outer ArrayList has 10..10.000 elements.
The inner ArrayList has 1..10.000 elements (i.e. Integers).
Sort it. Find out algorithm's complexity.

4. An object has array ("window") of N integers.
Object's method receive an integer and must return a minimal integer since last N received elements.
*/

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
