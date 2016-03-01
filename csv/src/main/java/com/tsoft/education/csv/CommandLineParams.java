package com.tsoft.education.csv;

public class CommandLineParams {
    private String[] args;
    private String folderName;
    private int numberOfFiles;
    private int linesPerFile;
    private int numberOfIterations;
    private int durationOfIteration;
    private String configFileName;

    public CommandLineParams(String... args) {
        this.args = args;
    }

    public String getFolderName() {
        return folderName;
    }

    public int getNumberOfFiles() {
        return numberOfFiles;
    }

    public int getLinesPerFile() {
        return linesPerFile;
    }

    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public int getDurationOfIteration() {
        return durationOfIteration;
    }

    public String getConfigFileName() {
        return configFileName;
    }

    public CommandLineParams parse() {
        if (args.length < 4) {
            throw new IllegalArgumentException("Not enough params");
        }

        // parse args
        folderName = args[0];
        numberOfFiles = Integer.parseInt(args[1]);
        linesPerFile = Integer.parseInt(args[2]);
        configFileName = args[3];

        if (args.length >= 5) numberOfIterations = Integer.parseInt(args[4]);
        else numberOfIterations = 1;

        if (args.length >= 6) durationOfIteration = Integer.parseInt(args[5]);
        else durationOfIteration = 1;

        if (numberOfFiles < 1 || linesPerFile < 1 || numberOfIterations < 1 || durationOfIteration < 1) {
            throw new IllegalArgumentException("All integer params must be greater then 1");
        }

        return this;
    }
}
