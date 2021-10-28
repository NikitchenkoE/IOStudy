package fileAnalyzer;

import java.io.*;
import java.util.Scanner;
import java.util.StringJoiner;

public class FileAnalyzer {
    private final String BAD_PATH = "File by this path not found, try again";
    private String filePath = "";
    private String word = "";

    public FileAnalyzer() {
    }

    public FileAnalyzer(String filePath, String word) {
        this.filePath = filePath;
        this.word = word;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void startAnalyze() throws IOException {
        System.out.println("Write path to file and press enter");
        Scanner scanner = new Scanner(System.in);
        while (filePath.isEmpty()) {
            filePath = scanner.nextLine();
        }
        System.out.println("Write word to find in file and press enter");
        while (word.isEmpty()) {
            word = scanner.nextLine();
            scanner.close();
        }
        try {
            System.out.println(analyzeFile());
        } catch (FileNotFoundException e) {
            System.out.println(BAD_PATH);
        }
    }

    public String analyzeFile() throws IOException {
        try {
            StringBuilder fileInfo = createStringFromFile(filePath);
            int wordCount = countNumberOfWord(fileInfo, word);
            String stringsWithWord = getStringsWithWord(fileInfo, word);

            return String.format("Count of word '%s' = %s, in strings: \n%s ", word, wordCount, stringsWithWord);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(BAD_PATH);
        }


    }

    private int countNumberOfWord(StringBuilder stringBuilder, String word) {
        int count = 0;
        String[] words = stringBuilder.toString().split("[ ?.!\n]+");
        for (String message : words) {
            if (message.equals(word)) {
                count++;
            }
        }
        return count;
    }

    private String getStringsWithWord(StringBuilder stringBuilder, String word) {
        String[] fileStrings = stringBuilder.toString().split("[?.!\n]+");
        StringJoiner stringJoiner = new StringJoiner(" \n");
        for (String fileString : fileStrings) {
            if (equalsWord(word, fileString)) {
                stringJoiner.add(fileString);
            }
        }
        return stringJoiner.toString();
    }

    private boolean equalsWord(String word, String str) {
        boolean result = false;
        String[] strings = str.split("[, ?.!\n]+");
        for (String string : strings) {
            if (string.equals(word)) {
                result = true;
                break;
            }
        }
        return result;
    }

    private StringBuilder createStringFromFile(String filePath) throws IOException {
        int code;
        InputStream inputStream = new FileInputStream(filePath);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        StringBuilder stringBuilder = new StringBuilder();

        while ((code = bufferedInputStream.read()) != -1) {
            stringBuilder.append((char) code);
        }
        bufferedInputStream.close();
        return stringBuilder;
    }
}

