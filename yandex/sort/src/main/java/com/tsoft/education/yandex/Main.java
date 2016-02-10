package com.tsoft.education.yandex;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();

        ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < 4; i ++) {
            ArrayList<Integer> subList = new ArrayList<>();
            list.add(subList);
            int n = new Random().nextInt(20) + 1;
            for (int k = 0; k < n; k ++) {
                subList.add(new Random().nextInt(100));
            }
        }

        ArrayList<Integer> result = main.sort(list);
    }

    private ArrayList<Integer> sort(ArrayList<ArrayList<Integer>> list) {
        // 1. Sort inner lists using standard Java functions
        for (int i = 0; i < list.size(); i ++) {
            list.get(i).sort(Integer::compare);
        }

        // 2. Sort the outer list
        ArrayList<Integer> result = new ArrayList<>();
        int[] indexes = new int[list.size()];
        int processed = 0;
        while (processed < list.size()) {
            int[] arr = new int[list.size()];
            for (int i = 0; i < list.size(); i ++) {
                if (indexes[i] == -1) continue;
            }
        }

        return result;
    }
}
