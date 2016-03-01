package com.tsoft.education.csv;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        CommandLineParams commandLineParams = new CommandLineParams(args).parse();
        try (CsvGeneratorApplication app = new CsvGeneratorApplication()) {
            // load a configuration
            app.loadConfigs(commandLineParams.getConfigFileName());

            // generate
            app.startGeneration(commandLineParams);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + "\n");
            printHelp();
        }
    }

    private static void printHelp() throws IOException {
        System.out.println(
                "Usage:\n" +
                "    Output folder's name\n" +
                "    Number of files to be generated\n" +
                "    Number of lines per a file\n" +
                "    <config file.xml>\n" +
                "    [Number of iterations = 1]\n" +
                "    [Duration of iteration (in seconds) = 1]\n\n" +
                "Config file example:\n");
        System.exit(1);
    }
}
