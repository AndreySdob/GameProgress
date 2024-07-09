package org.example;

import java.io.File;
import java.util.List;

public class DeleteFiles {

    public static void deleteFiles(List<String> files) {
        for (String filePath : files) {
            File file = new File(filePath);
            if (file.delete()) {
                System.out.println("Deleted file: " + filePath);
            } else {
                System.err.println("Failed to delete file: " + filePath);
            }
        }
    }
}
