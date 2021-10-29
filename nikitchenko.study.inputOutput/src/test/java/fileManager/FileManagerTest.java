package fileManager;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {

    @Test
    void countFiles() {
        System.out.println(FileManager.countFiles("D:\\file\\1"));
    }

    @Test
    void countDirs() {
        System.out.println(FileManager.countDirs("D:\\file\\1"));
    }

    @Test
    void copy() throws IOException {
        FileManager.copy("D:\\file\\1","D:\\file\\1 — копия");
    }

    @Test
    void move() {
    }
}