package FileAnalyzer;

import fileAnalyzer.FileAnalyzer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileAnalyzerTest {
    String filePath = "C:\\Users\\evgen\\IdeaProjects\\MessageSenderTwilio\\IOStudy\\src\\test\\java\\testFiles\\test.txt";
    String word = "I";

    @BeforeEach
    void createFile() throws IOException {
        PrintWriter printWriter = new PrintWriter(filePath);
        String fileText = "Maybe it’s just me.\n" +
                "but the next stanza is where I start to have a problem.\n" +
                "I mean how can the evening bump into the stars?\n" +
                "And what’s an obbligato of snow?\n" +
                "Also, I roam the decaffeinated streets.\n" +
                "At that point Im lost. I need help.";
        StringReader stringReader = new StringReader(fileText);
        int s;
        while ((s = stringReader.read()) != -1) {
            printWriter.print((char) s);
        }
        printWriter.close();
        stringReader.close();
    }

    @Test
    void testAnalyzeFile_TestToFindAllWordsAndStrings() throws IOException {
        String word = "I";

        FileAnalyzer fileAnalyzer = new FileAnalyzer(filePath, word);

        String expected = "Count of word: 5 , strings: [but the next stanza is where I start to have a problem, I mean how can the evening bump into the stars, Also, I roam the decaffeinated streets, At that point Im lost,  I need help]";
        String actual = fileAnalyzer.analyzeFile();

        assertEquals(expected, actual);
    }

    @Test
    void testAnalyzeFile_TestToTryAnalyzeFileByBadPath() throws IOException {
        String badPath = "badPath";

        FileAnalyzer fileAnalyzer = new FileAnalyzer(badPath, badPath);

        Exception exception = assertThrows(RuntimeException.class, fileAnalyzer::analyzeFile);
        String actualMessage = exception.getMessage();
        String expected = "File by this path not found, try again";

        assertEquals(expected, actualMessage);
    }

    @Test
    void testAnalyzeFileWorkWithUTF8() throws IOException {
        String filePathUTF = "C:\\Users\\evgen\\IdeaProjects\\MessageSenderTwilio\\IOStudy\\src\\test\\java\\testFiles\\testUTF.txt";
        PrintWriter printWriter = new PrintWriter(filePathUTF);
        String fileText = "Один миг пролетел. Два часа прошло. Три. Пять человек сидели в вагонетке. Десять голубей пролетели над домом. Гвозь был длинный. День был короткий. Два больших дня.";
        StringReader stringReader = new StringReader(fileText);
        int s;
        while ((s = stringReader.read()) != -1) {
            printWriter.print((char) s);
        }
        printWriter.close();
        stringReader.close();

        String word = "Два";

        FileAnalyzer fileAnalyzer = new FileAnalyzer(filePathUTF, word);

        String expected = "Count of word: 2 , strings: [ Два часа прошло,  Два больших дня]";
        String actual = fileAnalyzer.analyzeFile();

        assertEquals(expected, actual);
    }

    @Test
    void testAnalyze() throws IOException {
        FileAnalyzer fileAnalyzer = new FileAnalyzer(filePath, word);
        var file = fileAnalyzer.analyze();

        int expectedCount = 5;
        String expectedStrings = "[but the next stanza is where I start to have a problem, I mean how can the evening bump into the stars, Also, I roam the decaffeinated streets, At that point Im lost,  I need help]";
        int actualCount = file.getCountOfWords();
        String actualStrings = file.getStringsWithWord().toString();

        assertEquals(expectedCount, actualCount);
        assertEquals(expectedStrings, actualStrings);
    }

    @Test
    void testFilterToFilterList() {
        FileAnalyzer fileAnalyzer = new FileAnalyzer();

        String word = "One";
        List<String> inputArray = new ArrayList<>();
        inputArray.add("One second");
        inputArray.add("Two");
        inputArray.add("One minute");

        List<String> expected = new ArrayList<>();
        expected.add("One second");
        expected.add("One minute");

        List<String> actual = fileAnalyzer.filter(inputArray, word);

        assertEquals(expected, actual);
    }

    @Test
    void testCountWords() {
        FileAnalyzer fileAnalyzer = new FileAnalyzer();

        String word = "One";
        List<String> inputArray = new ArrayList<>();
        inputArray.add("One second One One");
        inputArray.add("Two");
        inputArray.add("One minute");

        int expected = 4;
        int actual = fileAnalyzer.countWords(inputArray, word);

        assertEquals(expected, actual);
    }
}