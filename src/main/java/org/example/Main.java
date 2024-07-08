import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        String basePath = "E://Games/savegames";

        // Создание объектов GameProgress
        GameProgress game1 = new GameProgress(100, 2, 5, 300.5);
        GameProgress game2 = new GameProgress(80, 3, 6, 450.2);
        GameProgress game3 = new GameProgress(50, 5, 10, 1200.7);

        // Сохранение объектов GameProgress
        SaveGame.saveGame(basePath + "/save1.dat", game1);
        SaveGame.saveGame(basePath + "/save2.dat", game2);
        SaveGame.saveGame(basePath + "/save3.dat", game3);

        // Указание файлов для архивации
        List<String> filesToZip = Arrays.asList(
                basePath + "/save1.dat",
                basePath + "/save2.dat",
                basePath + "/save3.dat"
        );

        // Архивирование файлов
        ZipFiles.zipFiles(basePath + "/saves.zip", filesToZip);

        // Удаление оригинальных файлов
        DeleteFiles.deleteFiles(filesToZip);
    }
}

class DeleteFiles {

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

class ZipFiles {

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

class SaveGame {

    public static void saveGame(String filePath, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException e) {
            System.err.println("Error saving game progress: " + e.getMessage());
        }
    }
}

class GameProgress implements Serializable {
    private static final long serialVersionUID = 1L;

    private int health;
    private final int weapons;
    private final int lvl;
    private double distance;

    public GameProgress(int health, int weapons, int lvl, double distance) {
        this.health = health;
        this.weapons = weapons;
        this.lvl = lvl;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "GameProgress{" +
                "health=" + health +
                ", weapons=" + weapons +
                ", lvl=" + lvl +
                ", distance=" + distance +
                '}';
    }
}