package com.tsoft.education.csv.column;

import java.util.List;

public class ValueGenerator extends Generator {
    private Object[] values;
    private int index = -1;
    
    public ValueGenerator(List<Object> values) {
        this.values = values.toArray();
    }
    
    @Override
    public Object next() {
        if (isRandomized()) {
            index = getRandom(values.length);
        } else {
            index ++;
            if (index >= values.length) index = 0;

        }
        return values[index];
    }
}
