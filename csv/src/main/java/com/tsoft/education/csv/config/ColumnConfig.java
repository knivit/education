package com.tsoft.education.csv.config;

import java.text.Format;
import java.util.Iterator;

public class ColumnConfig {
    private String name;
    private Iterator generator;
    private Format formatter;

    public ColumnConfig(String name, Iterator generator) {
        this.name = name;
        this.generator = generator;
    }

    public String getName() {
        return name;
    }

    public Iterator getGenerator() {
        return generator;
    }

    public Format getFormatter() {
        return formatter;
    }

    public void setFormatter(Format formatter) {
        this.formatter = formatter;
    }
}
