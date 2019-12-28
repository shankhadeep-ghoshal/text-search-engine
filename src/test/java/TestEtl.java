import org.junit.jupiter.api.*;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestEtl {
    private static final String dataRaw = "The quick brown fox, jumped over the lazy dog.";
    private static final String dataPunctuationsRemoved =
            "The quick brown fox  jumped over the lazy dog ";
    private static final String dataPunctsAndWhitespacesRemoved =
            "The quick brown fox jumped over the lazy dog";
    private static final String dataAllAboveAndLowerCase =
            dataPunctsAndWhitespacesRemoved.toLowerCase();

    @Order(1)
    @Test
    void testRawDataToRemovedPunctuationsAndWhitespacesToLowerCase() {
        String newData =
                new ETL(dataRaw)
                        .removeSpecialCharacters()
                        .removeExtraWhitespace()
                        .toLowerCase(Locale.getDefault())
                        .getData();
        assertEquals(dataAllAboveAndLowerCase, newData);
    }

    @Order(2)
    @Test
    void testNullEntry() {
        assertThrows(AssertionError.class, () -> new ETL(null));
    }
}