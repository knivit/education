package com.tsoft.education.csv.column;

public class IntegerValueGenerator extends Generator {
    private int from = 0;
    private int to = 256;
    
    @Override
    public Object next() {
        int value = getRandom(to - from) + from;
        return value;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public void setTo(int to) {
        this.to = to;
    }
}
