package com.tsoft.education.csv;

import java.util.logging.Level;

public class ApplicationLogger {
    private Level level;

    public ApplicationLogger(Level level) {
        this.level = level;
    }

    public void log(Level level, String msg) {
        if (level.intValue() >= getLevel().intValue()) {
            System.out.format("%6s: %s\n", level.getName(), msg);
        }
    }

    public void logText(Level level, String msg) {
        if (level.intValue() >= getLevel().intValue()) {
            System.out.print(msg);
        }
    }

    private Level getLevel() {
        return level;
    }
}
