package fileAnalyzer;

import java.util.List;

public class AnalyzedFile {
    private int countOfWords;
    private List<String> stringsWithWord;

    public AnalyzedFile() {
    }

    public AnalyzedFile(int countOfWords, List<String> stringsWithWord) {
        this.countOfWords = countOfWords;
        this.stringsWithWord = stringsWithWord;
    }

    public int getCountOfWords() {
        return countOfWords;
    }

    public void setCountOfWords(int countOfWords) {
        this.countOfWords = countOfWords;
    }

    public List<String> getStringsWithWord() {
        return stringsWithWord;
    }

    public void setStringsWithWord(List<String> stringsWithWord) {
        this.stringsWithWord = stringsWithWord;
    }
}
