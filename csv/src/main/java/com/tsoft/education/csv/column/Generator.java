package com.tsoft.education.csv.column;

import org.springframework.beans.factory.BeanNameAware;

import java.util.Iterator;
import java.util.Random;

public abstract class Generator implements Iterator, BeanNameAware {
    private static final Random random = new Random();
    private boolean isRandomized = true;
    
    private String beanName;
    
    public int getRandom(int max) {
        return random.nextInt(max);
    }

    public double getDoubleRandom(double max) {
        return random.nextDouble() * max;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    public boolean isRandomized() {
        return isRandomized;
    }

    public void setRandomized(boolean randomized) {
        isRandomized = randomized;
    }

    public String getBeanName() {
        return beanName;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Generator generator = (Generator) o;

        if (beanName != null ? !beanName.equals(generator.beanName) : generator.beanName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return beanName != null ? beanName.hashCode() : 0;
    }
}
