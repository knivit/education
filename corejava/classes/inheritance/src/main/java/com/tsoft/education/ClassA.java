package com.tsoft.education;

public class ClassA {
    private int val = 2;

    public ClassA() {
        val = 3;
    }

    @Override
    public String toString() {
        return Integer.toString(val);
    }
}
