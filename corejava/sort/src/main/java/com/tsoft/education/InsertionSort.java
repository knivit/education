package com.tsoft.education;

import java.util.Arrays;
import java.util.Random;

public class InsertionSort {
    public static void main(String[] args) {
        System.out.println(InsertionSort.class.getName() + " started");
        InsertionSort insertionSort = new InsertionSort();

        // the same algorithm but with different goals
        long time = System.currentTimeMillis();
        insertionSort.minimumCode(10);
        System.out.println("Exec. time " + (System.currentTimeMillis() - time));

        time = System.currentTimeMillis();
        insertionSort.minimumWrites(10);
        System.out.println("Exec. time " + (System.currentTimeMillis() - time));
    }

    private void minimumCode(int numberOfElements) {
        System.out.println("=== Ver1. Minimum of code");
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

    private void minimumWrites(int numberOfElements) {
        System.out.println("\n ===Ver2. Minimum writes");

        int[] arr = new Random().ints(numberOfElements, 0, 100).toArray();

        if (numberOfElements < 16) {
            System.out.println("Original array: " + Arrays.toString(arr));
        }

        for (int i = 1; i < arr.length; i ++) {
            int k = i - 1;
            while (k >= 0 && arr[i] < arr[k]) k --;

            k ++;
            if (k != i) {
                int t = arr[i];
                System.arraycopy(arr, k, arr, k + 1, (i - k));
                arr[k] = t;
            }
        }

        if (numberOfElements < 16) {
            System.out.println("Sorted array: " + Arrays.toString(arr));
        }
    }
}
