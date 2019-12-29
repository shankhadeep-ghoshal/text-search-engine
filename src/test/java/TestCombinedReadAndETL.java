import etl.ETL;
import file.io.FileDataReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

class TestCombinedReadAndETL {

    @Test
    void testGetWordCountInTextFile() {
        Map<String, Set<Integer>> obtainedResult =
                new ETL(new FileDataReader("src\\test\\resources\\test2.txt")
                        .readDataFromSource())
                        .removeSpecialCharacters()
                        .removeExtraWhitespace()
                        .toLowerCase(Locale.getDefault())
                        .createWordMap()
                        .getWordMap();

        int theWordLetCount = obtainedResult.get("let").size();
        Assertions.assertEquals(3, theWordLetCount);
    }
}
