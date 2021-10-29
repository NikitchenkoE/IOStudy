package fileAnalyzer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FileAnalyzerController fileAnalyzerController = new FileAnalyzerController();
        fileAnalyzerController.startAnalyze();
    }
}