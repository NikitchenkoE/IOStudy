import fileAnalyzer.FileAnalyzer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileAnalyzerTest {
    String filePath = "nikitchenko.study.inputOutput/src/test/java/testFiles/test.txt";
    String word = "I";

    @BeforeEach
    void createFile() throws IOException {
        PrintWriter printWriter = new PrintWriter(filePath);
        String fileText = "Maybe it’s just me,\n" +
                "but the next stanza is where I start to have a problem.\n" +
                "I mean how can the evening bump into the stars?\n" +
                "And IwIhat’s aIn obbligatoI ofI snowI?\n" +
                "Also, I roam the decaffeinated streets.\n" +
                "AIt thIat poiInt I’m lost. I need help.";
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

        String expected = "Count of word 'I' = 4, in strings: \n" +
                "but the next stanza is where I start to have a problem \n" +
                "I mean how can the evening bump into the stars \n" +
                "Also, I roam the decaffeinated streets \n" +
                " I need help ";
        String actual = fileAnalyzer.analyzeFile();

        assertEquals(expected, actual);
    }
}