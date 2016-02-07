package com.tsoft.education;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class Main {
    public static void main(String[] args) throws Exception {
        File srcFile = new File("test.data");
        System.out.println("Source " + srcFile.getAbsoluteFile());

        FileChannel src = new FileInputStream(srcFile).getChannel();

        File dstFile = new File("copied.data");
        FileChannel dst = new FileOutputStream(dstFile).getChannel();

        src.transferTo(0, src.size(), dst);

        src.close();
        dst.close();

        System.out.println("See result in " + dstFile.getAbsoluteFile());
    }
}
