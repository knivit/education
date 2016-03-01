package com.tsoft.education.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        CsvGeneratorApplication app = null;

        try {
            CommandLineParams commandLineParams = new CommandLineParams(args).parse();

            // load a configuration
            app = new CsvGeneratorApplication();
            app.loadConfigs(commandLineParams.getConfigFileName());

            // generate
            app.startGeneration(commandLineParams);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + "\n");
            printHelp();
        } finally {
            if (app != null) {
                app.close();
            }
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

        InputStream exampleConfigStream = Main.class.getResourceAsStream("ConfigExample.xml");
        BufferedReader in = new BufferedReader(new InputStreamReader(exampleConfigStream));
        try {
            while (in.ready()) {
                String text = in.readLine();
                System.out.println(text);
            }
        } finally {
            in.close();
        }
        System.exit(1);
    }
}
