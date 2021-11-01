package fileManager;

import java.io.*;
import java.util.Objects;

public class FileManager {
    private static int fileCount = 0;
    private static int dirCount = 0;

    // public static int countFiles(String path) - принимает путь к папке,
// возвращает количество файлов в папке и всех подпапках по пути
    public static int countFiles(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        for (File someFile : Objects.requireNonNull(files)) {
            if (someFile.isFile() && !someFile.isHidden()) {
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
        for (File someFile : Objects.requireNonNull(files)) {
            if (someFile.isDirectory() && !someFile.isHidden()) {
                dirCount++;
            }
        }
        if (file.getParent() != null) {
            countDirs(file.getParent());
        }
        return dirCount;
    }

    public static void copy(String from, String to) throws IOException {
        File fileFrom = new File(from);
        File fileTo = new File(to);
        if (fileTo.isFile()){
            throw new RuntimeException("You cant copy to another file");
        }
        //Create a directory if it does not exist
        if (!fileTo.exists()) {
            fileTo.mkdirs();
        }
        //Check if the file is a file
        if (fileFrom.isFile()) {
            String pathWithFileName = to + File.separator + fileFrom.getName();
            copyFiles(fileFrom, new File(pathWithFileName));
        } else {
            cannotBeCopiedToAChildFolder(fileFrom, fileTo);
            //if from is directory, create new package for it and copy files to that
            String updatePath = to + File.separator + fileFrom.getName();
            File newDirectory = new File(updatePath);
            newDirectory.mkdirs();

            String[] names = fileFrom.list();

            for (String fileName : Objects.requireNonNull(names)) {
                File fromFileWithUpdatedPath = new File(from + File.separator + fileName);
                File toFileWithUpdatedPath = new File(updatePath + File.separator + fileName);
                if (fromFileWithUpdatedPath.isDirectory()) {
                    copy(fromFileWithUpdatedPath.getPath(), toFileWithUpdatedPath.getParent());
                } else {
                    copyFiles(fromFileWithUpdatedPath, toFileWithUpdatedPath);
                }
            }
        }
    }

    private static void copyFiles(File from, File to) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(from));
             PrintWriter printWriter = new PrintWriter(to)) {
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                printWriter.println(s);
            }
        }
    }

    //- метод по копированию папок и файлов.
//        Параметр from - путь к файлу или папке, параметр to - путь к папке куда будет производиться копирование.
    public static void move(String from, String to) throws IOException {
        copy(from, to);
        delete(from);
    }
//    - метод по перемещению папок и файлов.
//        Параметр from - путь к файлу или папке, параметр to - путь к папке куда будет производиться копирование.

    public static void delete(String from) {
        File fromFile = new File(from);
        if (fromFile.isFile()) {
            fromFile.delete();
        } else {
            File[] files = fromFile.listFiles();
            for (File fileName : Objects.requireNonNull(files)) {
                if (fileName.isDirectory()) {
                    delete(fileName.getPath());
                }
                fileName.delete();
            }
            fromFile.delete();
        }

    }

    private static void cannotBeCopiedToAChildFolder(File from, File to) {
        File[] childFiles = from.listFiles();
        for (File childFile : Objects.requireNonNull(childFiles)) {
            if (childFile.isDirectory()) {
                if (childFile.equals(to)) {
                    throw new RuntimeException("Cannot be copied to a child folder");
                }
                cannotBeCopiedToAChildFolder(childFile, to);
            }
        }
    }
}
