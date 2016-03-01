package com.tsoft.education.csv;

import com.tsoft.education.csv.column.ComplexValue;
import com.tsoft.education.csv.config.ColumnConfig;
import com.tsoft.education.csv.config.CsvConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Format;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;

public class CsvGenerator {
    private ApplicationLogger logger = new ApplicationLogger(Level.INFO);
    
    private String[] duplicates = new String[50];
    private int dupIndex;
    private boolean allDupFilled;

    private static final Random random = new Random();

    public void generate(String folderName, int numberOfFiles, int linesPerFile, CsvConfig config) throws IOException {
        for (int i = 0; i < numberOfFiles; i ++) {
            String fileName = folderName + (folderName.endsWith(File.separator) ? "" : File.separator);
            fileName += config.getTable() + "_" + String.valueOf(new Date().getTime()) + ".csv";
            logger.logText(Level.INFO, "Generating " + fileName + " ");

            BufferedWriter fw = new BufferedWriter(new FileWriter(fileName));
            try {
                fw.write(generateHeader(config));
                for (int k = 0; k < linesPerFile; k ++) {
                    fw.write(generateNextLine(config));

                    if ((k % 1000) == 0) {
                        logger.logText(Level.INFO, ".");
                    }
                }
            } finally {
                fw.close();
            }
            logger.logText(Level.INFO, " done\n");
        }
    }
    
    private String generateNextLine(CsvConfig config) {
        String line = generateLine(config);

        // place it to a buffer of duplicates
        duplicates[dupIndex ++] = line;
        if (dupIndex >= duplicates.length) {
            dupIndex = 0;
            allDupFilled = true;
        }
        
        // check is this a time for a duplicate
        if (config.getPercentOfDuplicates() != 0) {
            int rnd = random.nextInt(100);
            
            // hit
            if (rnd <= config.getPercentOfDuplicates()) {
                int index = random.nextInt(allDupFilled ? duplicates.length : dupIndex);
                line = duplicates[index];
            }
        }

        return line;
    }

    private String generateLine(CsvConfig config) {
        StringBuilder buf = new StringBuilder();

        // foc complex values, we should to get them once per line
        HashMap<Iterator, ComplexValue> complexValueHashMap = new HashMap<Iterator, ComplexValue>();

        boolean first = true;
        for (ColumnConfig columnConfig : config.getColumnConfigs()) {
            if (!first) {
                buf.append(',');
            }
            first = false;

            Object rawValue;
            String columnName = columnConfig.getName();

            Iterator generator = columnConfig.getGenerator();
            ComplexValue complexValue = complexValueHashMap.get(generator);
            if (complexValue != null) {
               rawValue = complexValue.getColumnValue(columnName);
            } else {
                rawValue = generator.next();
                if (rawValue instanceof ComplexValue) {
                    complexValue = (ComplexValue) rawValue;
                    complexValueHashMap.put(generator, complexValue);

                    rawValue = complexValue.getColumnValue(columnName);
                }
            }
            
            Format formatter = columnConfig.getFormatter();
            
            String value;
            if (formatter == null) {
                if (rawValue == null) {
                    throw new IllegalArgumentException("Generator " + generator.getClass().getName() + " has returned NULL value for " + columnName + " column");
                }
                value = rawValue.toString();
            } else {
                value = formatter.format(rawValue);
            }
            
            buf.append('"').append(value).append('"');
        }

        buf.append('\n');
        return buf.toString();
    }

    private String generateHeader(CsvConfig config) {
        StringBuilder buf = new StringBuilder();

        boolean first = true;
        for (ColumnConfig columnConfig : config.getColumnConfigs()) {
            if (!first) {
                buf.append(',');
            }
            first = false;

            buf.append('"').append(columnConfig.getName()).append('"');
        }

        buf.append('\n');
        return buf.toString();
    }
}
