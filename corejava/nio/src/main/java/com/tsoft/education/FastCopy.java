package com.tsoft.education;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FastCopy {
    public static void main(String[] args) throws Exception {
        FastCopy fastCopy = new FastCopy();
        fastCopy.start();
    }

    private void start() throws IOException {
        File srcFile = new File("test.data");
        System.out.println("Source " + srcFile.getAbsoluteFile());

        File dstFile = new File("copied.data");

        try (FileChannel src = new FileInputStream(srcFile).getChannel()) {
            try (FileChannel dst = new FileOutputStream(dstFile).getChannel()) {
                src.transferTo(0, src.size(), dst);
            }
        }

        System.out.println("See result in " + dstFile.getAbsoluteFile());
    }
}
