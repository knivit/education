package com.tsoft.education.csv;

import com.tsoft.education.csv.config.CsvConfig;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.Assert;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.List;

public class CsvGeneratorApplication implements Closeable {
    private List<CsvConfig> configList;
    private int iterationCount = 0;

    public void loadConfigs(String... configFileNames) {
        Assert.notNull(configFileNames);

        configList = new ArrayList<>();
        for (String configFileName : configFileNames) {
            FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(configFileName);

            String[] csvConfigNames = context.getBeanNamesForType(CsvConfig.class);
            for (String csvConfigName : csvConfigNames) {
                CsvConfig csvConfig = (CsvConfig) context.getBean(csvConfigName);
                configList.add(csvConfig);
                csvConfig.setFilename(configFileName);
                csvConfig.setContext(context);
            }
        }
    }

    public void startGeneration(CommandLineParams commandLineParams) throws Exception {
        configCycle: for (CsvConfig config : configList) {
            System.out.println("Start job with config: " + config.getFilename());

            ScheduledFuture future = config.getScheduler().scheduleAtFixedRate(
                new Generator(config, commandLineParams.getFolderName(), commandLineParams.getNumberOfFiles(),
                        commandLineParams.getLinesPerFile(), commandLineParams.getNumberOfIterations()),
                    commandLineParams.getDurationOfIteration() * 1000);

            while (commandLineParams.getNumberOfIterations() > getIterationCount()) {
                if (future.isDone()) {
                    continue configCycle;
                }

                Thread.sleep(1000);
            }

            future.cancel(false);
        }
    }

    @Override
    public void close() {
        if (configList == null) {
            return;
        }

        for (CsvConfig config : configList) {
            config.getContext().close();
        }
    }

    public synchronized int getIterationCount() {
        return iterationCount;
    }

    private synchronized void iterationCompleted() {
        iterationCount ++;
    }

    private class Generator implements Runnable {
        private CsvConfig config;
        private String folderName;
        private int numberOfFiles;
        private int linesPerFile;
        private int numberOfIterations;

        public Generator(CsvConfig config, String folderName, int numberOfFiles, int linesPerFile, int numberOfIterations) {
            this.config = config;
            this.folderName = folderName;
            this.numberOfFiles = numberOfFiles;
            this.linesPerFile = linesPerFile;
            this.numberOfIterations = numberOfIterations;
        }

        @Override
        public void run() {
            if (numberOfIterations <= getIterationCount()) {
                return;
            }

            try {
                System.out.println("Iteration #" + (iterationCount + 1));
                new CsvGenerator().generate(folderName, numberOfFiles, linesPerFile, config);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                iterationCompleted();
            }
        }
    }
}
