package fileAnalyzer;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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

    public String analyzeFile() throws IOException {
        AnalyzedFile analyzedFile = analyze();
        return String.format("Count of word: %s , strings: %s", analyzedFile.getCountOfWords(), analyzedFile.getStringsWithWord());
    }

    public AnalyzedFile analyze() throws IOException {
        String content = readContentString(filePath).toString();
        List<String> sentences = new ArrayList<>(Arrays.asList(content.split("[?.!\n]+")));
        List<String> sentencesWithWord = filter(sentences, word);
        int countWord = countWords(sentencesWithWord, word);
        return new AnalyzedFile(countWord, sentencesWithWord);
    }

    public List<String> filter(List<String> sentences, String word) {
        return sentences.stream()
                .filter(sentence -> sentence.contains(word))
                .collect(Collectors.toList());
    }

    public int countWords(List<String> sentencesWithWord, String word) {
        return Math.toIntExact(sentencesWithWord.stream()
                .map(s -> s.split(" "))
                .flatMap(Arrays::stream)
                .filter(sentence -> sentence.contains(word))
                .count());
    }

    private StringBuilder readContentString(String filePath) throws IOException {
        try {
            InputStream inputStream = new FileInputStream(filePath);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            return readContent(bufferedInputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(BAD_PATH, e);
        }
    }

    private StringBuilder readContent(InputStream inputStream) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        byte[] buffer = new byte[8192];
        while (bufferedInputStream.read() != -1) {
            bufferedInputStream.read(buffer);
        }
        StringBuilder stringBuilder = new StringBuilder(new String(buffer));
        bufferedInputStream.close();

        return stringBuilder;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getWord() {
        return word;
    }
}




