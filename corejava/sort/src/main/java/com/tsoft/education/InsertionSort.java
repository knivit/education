package com.tsoft.education;

import java.util.Arrays;
import java.util.Random;

public class InsertionSort {
    /**
     * Insertion Sort
     *
     * Given:
     *   | 3 | 2 | 1 | 7 |
     *
     * Iterations:
     * 1) Take 1-th element, 2. Compare it with previous, 3.
     *    2 is less than 3, so swap them.
     *    | 2 | 3 | 1 | 7 |
     * 2) Take 2-th element, 1. Compare it with previous, 3.
     *    1 is less than 3, so swap them
     *    | 2 | 1 | 3 | 7 |
     *
     *    Take 1-th element, 1. Compare it with previous, 2.
     *    1 is less than 2, so swap them.
     *    | 1 | 2 | 3 | 7 |
     *
     * etc
     *
     */
    public static void main(String[] args) {
        System.out.println(InsertionSort.class.getName() + " started");
        InsertionSort insertionSort = new InsertionSort();

        // the same algorithm but with different goals
        long time = System.currentTimeMillis();
        insertionSort.start(10);
        System.out.println("Exec. time " + (System.currentTimeMillis() - time));
    }

    private void start(int numberOfElements) {
        int[] arr = new Random().ints(numberOfElements, 0, 100).toArray();

        if (numberOfElements < 16) {
            System.out.println("Original array: " + Arrays.toString(arr));
        }

        for (int i = 1; i < arr.length; i ++) {
            int x = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > x) {
                arr[j + 1] = arr[j];
                j --;
            }
            arr[j + 1] = x;
        }

        if (numberOfElements < 16) {
            System.out.println("Sorted array: " + Arrays.toString(arr));
        }
    }
}
