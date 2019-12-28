import org.junit.jupiter.api.*;

import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestEtl {
    private static final String DATA_RAW = "The quick brown fox, jumped over the lazy dog.";
    private static final String DATA_PUNCTUATIONS_REMOVED =
            "The quick brown fox  jumped over the lazy dog ";
    private static final String DATA_PUNCTS_AND_WHITESPACES_REMOVED =
            "The quick brown fox jumped over the lazy dog";
    private static final String DATA_ALL_ABOVE_AND_LOWER_CASE =
            DATA_PUNCTS_AND_WHITESPACES_REMOVED.toLowerCase();

    private static ETL ETL_DATA;

    @BeforeAll
    static void steUpETLData() {
        ETL_DATA = new ETL(DATA_RAW)
                .removeSpecialCharacters()
                .removeExtraWhitespace()
                .toLowerCase(Locale.getDefault());
    }

    @Order(1)
    @Test
    void testRawDataToRemovedPunctuationsAndWhitespacesToLowerCase() {
        String newData = ETL_DATA.getData();
        assertEquals(DATA_ALL_ABOVE_AND_LOWER_CASE, newData);
    }

    @Order(2)
    @Test
    void testNullEntry() {
        assertThrows(AssertionError.class, () -> new ETL(null));
    }

    @Order(3)
    @Test
    void testWordMap() {
        final Set<Integer> theSet = ETL_DATA.createWordMap().getWordMap().get("the");
        final Set<Integer> testTheSetExpectedOutput = new TreeSet<>();
        testTheSetExpectedOutput.add(0);
        testTheSetExpectedOutput.add(6);

        assertEquals(testTheSetExpectedOutput, theSet);
    }
}