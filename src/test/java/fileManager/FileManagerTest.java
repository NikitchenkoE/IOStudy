package fileManager;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {

//    @Test
//    void countFilesBefore() {
//        System.out.println(FileManager.countFilesBefore("D:\\file"));
//    }
//
//    @Test
//    void countDirsBefore() {
//        System.out.println(FileManager.countDirsBefore("C:\\"));
//    }

    @Test
    void countFilesInsideFolder() {
        String path = "src/test/java/FileManagerCopyTestFiles/copied";
        int expectedCount = 5;
        int actualCount = FileManager.countFilesInside(path);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    void countFilesByDoesntExistPath_ExpectedException() {
        String pathTo = "C:\\someFile";
        Exception exception = assertThrows(RuntimeException.class, () -> FileManager.countFilesInside(pathTo));

        String expectedMessage = "Nothing was found by this path";
        String actual = exception.getMessage();

        assertEquals(expectedMessage, actual);
    }

    @Test
    void countDirsByDoesntExistPath() {
        String pathTo = "C:\\someFile";
        Exception exception = assertThrows(RuntimeException.class, () -> FileManager.countDirsInside(pathTo));

        String expectedMessage = "Nothing was found by this path";
        String actual = exception.getMessage();

        assertEquals(expectedMessage, actual);
    }

    @Test
    void countDirsInsideFolder() {
        String path = "src/test/java/FileManagerCopyTestFiles/copied";
        int expectedCount = 4;
        int actualCount = FileManager.countDirsInside(path);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    void copyDirToAnotherDir() throws IOException {
        String pathFrom = "src/test/java/FileManagerCopyTestFiles/copied";
        String pathTo = "src/test/java/FileManagerCopyTestFiles/direction";

        FileManager.copy(pathFrom, pathTo);
        File fileFrom = new File(pathFrom);
        File fileInDirectionFolder = new File(pathTo, fileFrom.getName());

        String expected = "[1, 1.txt, 2, 2.txt, 3, 5.txt, someDirectory]";
        List<String> fileNames = new ArrayList<>(Arrays.asList(Objects.requireNonNull(fileInDirectionFolder.list())));
        String actual = fileNames.toString();

        assertEquals(expected, actual);

        FileManager.delete(pathTo);
    }

    @Test
    void copyFileToAnotherDir() throws IOException {
        String pathFrom = "src/test/java/FileManagerCopyTestFiles/copied/1.txt";
        String pathTo = "src/test/java/FileManagerCopyTestFiles/direction";

        FileManager.copy(pathFrom, pathTo);
        File fileFrom = new File(pathFrom);
        File fileTo = new File(pathTo, fileFrom.getName());

        assertTrue(fileTo.exists());

        FileManager.delete(pathTo);
    }

    @Test
    void tryToCopyParentFolderToChild_ExpectedException() throws IOException {
        String pathFrom = "src/test/java/FileManagerCopyTestFiles/copied";
        String pathTo = "src/test/java/FileManagerCopyTestFiles/copied/someDirectory";
        Exception exception = assertThrows(RuntimeException.class, () -> FileManager.copy(pathFrom, pathTo));

        String expectedMessage = "Cannot be copied to a child folder";
        String actual = exception.getMessage();
        assertEquals(expectedMessage, actual);
    }

    @Test
    void tryToCopyInFile_ExpectedException() {
        String pathFrom = "src/test/java/FileManagerCopyTestFiles/copied/2.txt";
        String pathTo = "src/test/java/FileManagerCopyTestFiles/copied/1.txt";
        Exception exception = assertThrows(RuntimeException.class, () -> FileManager.copy(pathFrom, pathTo));

        String expectedMessage = "You cant copy to another file";
        String actual = exception.getMessage();
        assertEquals(expectedMessage, actual);
    }

    @Test
    void deleteTestTryToDeleteFile() {
        String filePath = "src/test/java/FileManagerCopyTestFiles/copied/4";
        File file = new File(filePath);
        file.mkdir();
        FileManager.delete(filePath);

        assertFalse(file.exists());
    }

    @Test
    void move() throws IOException {
        String pathFrom = "src/test/java/FileManagerCopyTestFiles/copied/4";
        String pathTo = "src/test/java/FileManagerCopyTestFiles/moved/newDirToMovInto";
        File fileFrom = new File(pathFrom);
        File fileTo = new File(pathTo);
        fileFrom.mkdir();

        FileManager.move(pathFrom, pathTo);

        String expected = "[4]";
        List<String> fileNames = new ArrayList<>(Arrays.asList(Objects.requireNonNull(fileTo.list())));
        String actual = fileNames.toString();

        assertEquals(expected, actual);
        assertFalse(fileFrom.exists());
    }
}