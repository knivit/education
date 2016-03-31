package com.tsoft.education;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CheckSet {
    public static void main(String[] args) {
        CheckSet checkSet = new CheckSet();
        checkSet.start();
    }

    private void start() {
        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(1);
        System.out.println("Set's size is " + set.size());

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "Key 1");
        map.put(1, "Key 1");
        map.put(2, "Key 2");
        System.out.println("Map's size is " + map.size());
    }
}
