package com.tsoft.education;

import java.util.Arrays;
import java.util.Random;

public class MergeSort {
    public static void main(String[] args) {
        System.out.println(MergeSort.class.getName() + " started");
        MergeSort mergeSort = new MergeSort();

        long time = System.currentTimeMillis();
        mergeSort.start(10);
        System.out.println("Exec. time " + (System.currentTimeMillis() - time));
    }

    private void start(int numberOfElements) {
        int[] arr = new Random().ints(numberOfElements, 0, numberOfElements).toArray();

        if (numberOfElements < 16) {
            System.out.println("Original array: " + Arrays.toString(arr));
        }

        mergeSort(arr);

        if (numberOfElements < 16) {
            System.out.println("Sorted array: " + Arrays.toString(arr));
        }
    }

    // Based on https://en.wikipedia.org/wiki/Merge_sort
    private void mergeSort(int[] a) {
        int[] b = new int[a.length];

        topDownSplitMerge(a, 0, a.length, b);
    }

    // [from, to)
    private void topDownSplitMerge(int[] a, int from, int to, int[] b) {
        if (to - from < 2) return; // sorted

        // recursively split runs into two halves until run size == 1,
        // then merge them and return back up the call chain
        int middle = (to + from) / 2;           // middle = mid point
        topDownSplitMerge(a, from, middle, b);  // split / merge left  half
        topDownSplitMerge(a, middle, to, b);    // split / merge right half

        topDownMerge(a, from, middle, to, b);   // merge the two half runs
        System.arraycopy(b, from, a, from, (to - from)); // copy the merged runs back to A
    }

    private void topDownMerge(int[] a, int from, int middle, int to, int[] b) {
        int i = from;
        int j = middle;

        // While there are elements in the left or right runs...
        for (int k = from; k < to; k ++) {
            // If left run head exists and is <= existing right run head.
            if (i < middle && (j >= to || a[i] <= a[j])) {
                b[k] = a[i];
                i = i + 1;
            } else {
                b[k] = a[j];
                j = j + 1;
            }
        }
    }
}
