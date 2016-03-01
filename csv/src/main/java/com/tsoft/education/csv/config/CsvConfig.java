package com.tsoft.education.csv.config;

import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.util.Assert;

import java.util.List;

public class CsvConfig {
    private String table;
    private List<ColumnConfig> columnConfigs;
    private int percentOfDuplicates;
    private TaskScheduler scheduler;
    private String filename;
    private FileSystemXmlApplicationContext context;
    
    public CsvConfig(String table, List<ColumnConfig> columnConfigs, TaskScheduler scheduler) {
        this.table = table;
        this.columnConfigs = columnConfigs;
        this.scheduler = scheduler;
    }

    public String getTable() {
        return table;
    }

    public List<ColumnConfig> getColumnConfigs() {
        return columnConfigs;
    }

    public int getPercentOfDuplicates() {
        return percentOfDuplicates;
    }

    public void setPercentOfDuplicates(int percentOfDuplicates) {
        Assert.isTrue(percentOfDuplicates >=0 || percentOfDuplicates <= 100);
        this.percentOfDuplicates = percentOfDuplicates;
    }

    public TaskScheduler getScheduler() {
        return scheduler;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public FileSystemXmlApplicationContext getContext() {
        return context;
    }

    public void setContext(FileSystemXmlApplicationContext context) {
        this.context = context;
    }
}
