package com.tsoft.education.csv.column;

public class DoubleValueGenerator extends Generator {
    private double from = 0;
    private double to = 256;
    
    @Override
    public Object next() {
        double value = getDoubleRandom(to - from) + from;
        return value;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public void setTo(double to) {
        this.to = to;
    }
}
