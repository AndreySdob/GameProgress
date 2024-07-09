package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFiles {

    public static void zipFiles(String zipFilePath, List<String> files) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            for (String filePath : files) {
                try (FileInputStream fis = new FileInputStream(filePath)) {
                    ZipEntry zipEntry = new ZipEntry(new File(filePath).getName());
                    zos.putNextEntry(zipEntry);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) >= 0) {
                        zos.write(buffer, 0, length);
                    }

                    zos.closeEntry();
                } catch (IOException e) {
                    System.err.println("Error zipping file: " + filePath + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error creating zip file: " + zipFilePath + " - " + e.getMessage());
        }
    }
}
