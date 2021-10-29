package fileManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    private static int fileCount = 0;
    private static int dirCount = 0;

    // public static int countFiles(String path) - принимает путь к папке,
// возвращает количество файлов в папке и всех подпапках по пути
    public static int countFiles(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        for (File someFile : files) {
            if (someFile.isFile()){
                fileCount++;
            }
        }
        if (file.getParent() != null) {
            countFiles(file.getParent());
        }
        return fileCount;
    }

    // public static int countDirs(String path) - принимает путь к папке,
// возвращает количество папок в папке и всех подпапках по пути
    public static int countDirs(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        for (File someFile : files) {
            if (someFile.isDirectory()){
                dirCount++;
            }
        }
        if (file.getParent() != null) {
            countDirs(file.getParent());
        }
        return dirCount;
    }

    public static void copy(String from, String to) throws IOException {
    }

    //- метод по копированию папок и файлов.
//        Параметр from - путь к файлу или папке, параметр to - путь к папке куда будет производиться копирование.
    public static void move(String from, String to) {

    }
//    - метод по перемещению папок и файлов.
//        Параметр from - путь к файлу или папке, параметр to - путь к папке куда будет производиться копирование.
}
