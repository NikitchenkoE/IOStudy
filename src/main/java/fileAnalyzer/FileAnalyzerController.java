package fileAnalyzer;

import java.io.IOException;
import java.util.Scanner;

public class FileAnalyzerController {
    FileAnalyzer fileAnalyzer = new FileAnalyzer();

    public void startAnalyze() throws IOException {
        String filePath = fileAnalyzer.getFilePath();
        String word = fileAnalyzer.getWord();
        System.out.println("Write path to file and press enter");
        Scanner scanner = new Scanner(System.in);
        while (filePath.isEmpty()) {
            filePath = scanner.nextLine();
            fileAnalyzer.setFilePath(filePath);
        }
        System.out.println("Write word to find in file and press enter");
        while (word.isEmpty()) {
            word = scanner.nextLine();
            fileAnalyzer.setWord(word);
            scanner.close();
        }
        System.out.println(fileAnalyzer.analyzeFile());
    }
}
