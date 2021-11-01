package fileManager;

import java.io.*;
import java.util.Objects;

public class FileManager {
    private static int fileCount = 0;
    private static int dirCount = 0;

    // public static int countFiles(String path) - принимает путь к папке,
// возвращает количество файлов в папке и всех подпапках по пути
    public static int countFilesBefore(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        fileOrFolderDoNotExist(file);
        for (File someFile : Objects.requireNonNull(files)) {
            if (someFile.isFile() && !someFile.isHidden()) {
                fileCount++;
            }
        }
        if (file.getParent() != null) {
            countFilesBefore(file.getParent());
        }
        return fileCount;
    }

    public static int countFilesInside(String path) {
        int count = 0;
        File file = new File(path);
        fileOrFolderDoNotExist(file);
        if (file.isFile()) {
            count++;
        } else {
            File[] filesPotentiallyWithNull = file.listFiles();
            if (filesPotentiallyWithNull != null) {
                for (File fileInside : filesPotentiallyWithNull) {
                    if (fileInside.isFile()) {
                        count++;
                    } else {
                        count = count + countFilesInside(fileInside.getAbsolutePath());
                    }
                }
            }
        }
        return count;
    }

    // public static int countDirs(String path) - принимает путь к папке,
// возвращает количество папок в папке и всех подпапках по пути
    public static int countDirsBefore(String path) {
        File file = new File(path);
        fileOrFolderDoNotExist(file);
        File[] files = file.listFiles();
        for (File someFile : Objects.requireNonNull(files)) {
            if (someFile.isDirectory() && !someFile.isHidden()) {
                dirCount++;
            }
        }
        if (file.getParent() != null) {
            countDirsBefore(file.getParent());
        }
        return dirCount;
    }

    public static int countDirsInside(String path) {
        int count = 0;
        File file = new File(path);
        fileOrFolderDoNotExist(file);
        if (file.isFile()) {
            return count;
        } else {
            File[] filesWithPotentialNull = file.listFiles();
            if (filesWithPotentialNull != null) {
                for (File fileInside : filesWithPotentialNull) {
                    if (fileInside.isDirectory()) {
                        count++;
                        count = count + countDirsInside(fileInside.getAbsolutePath());
                    }
                }
            }
        }
        return count;
    }

    public static void copy(String from, String to) throws IOException {
        File fileFrom = new File(from);
        File fileTo = new File(to);
        if (fileTo.isFile()) {
            throw new RuntimeException("You cant copy to another file");
        }
        //Create a directory if it does not exist
        if (!fileTo.exists()) {
            fileTo.mkdirs();
        }
        //Check if the file is a file
        if (fileFrom.isFile()) {
            copyFiles(fileFrom, new File(to, fileFrom.getName()));
        } else {
            cannotBeCopiedToAChildFolder(fileFrom, fileTo);
            //if from is directory, create new package for it and copy files to that
            String updatePath = to + File.separator + fileFrom.getName();
            File newDirectory = new File(updatePath);
            newDirectory.mkdirs();

            String[] names = fileFrom.list();

            for (String fileName : Objects.requireNonNull(names)) {
                File fromFileWithUpdatedPath = new File(from, fileName);
                File toFileWithUpdatedPath = new File(updatePath, fileName);
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

    public static void delete(String fileToBeDeleted) {
        File file = new File(fileToBeDeleted);
        if (file.isFile()) {
            file.delete();
        } else {
            File[] files = file.listFiles();
            for (File fileName : Objects.requireNonNull(files)) {
                if (fileName.isDirectory()) {
                    delete(fileName.getPath());
                }
                fileName.delete();
            }
            file.delete();
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

    private static void fileOrFolderDoNotExist(File file) {
        if (!file.exists()) {
            throw new RuntimeException("Nothing was found by this path");
        }
    }
}
