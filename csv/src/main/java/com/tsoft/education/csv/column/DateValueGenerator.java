package com.tsoft.education.csv.column;

import java.util.Date;

public class DateValueGenerator extends Generator {
    @Override
    public Object next() {
        return new Date();
    }
}
