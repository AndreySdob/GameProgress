

import org.example.DeleteFiles;
import org.example.GameProgress;
import org.example.ZipFiles;

import java.io.*;
import java.util.Arrays;
import java.util.List;


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


class SaveGame {

    public static <GameProgress> void saveGame(String filePath, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException e) {
            System.err.println("Error saving game progress: " + e.getMessage());
        }
    }
}



