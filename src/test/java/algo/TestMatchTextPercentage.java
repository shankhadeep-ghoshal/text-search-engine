package algo;

import etl.ETL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestMatchTextPercentage {
    private static Map<String, Set<Integer>> STRING_MAP;
    private static DecimalFormat DECIMAL_FORMAT;

    @BeforeAll
    static void setUp() {
       STRING_MAP = new ETL("To Be Or Not To Be")
                .removeSpecialCharacters()
                .removeExtraWhitespace()
                .toLowerCase(Locale.getDefault())
                .createWordMap()
                .getWordMap();
       DECIMAL_FORMAT = new DecimalFormat("#.##");
       DECIMAL_FORMAT.setRoundingMode(RoundingMode.CEILING);
    }

    @Test
    void testPatternsDontMatch() {
        String[] pattern = "because".split("\\s+");
        double percentage = new MatchTextPercentage(STRING_MAP, pattern)
                .calculatePercentageWordMatchInText();
        String result = DECIMAL_FORMAT.format(percentage);
        assertEquals("0", result);
    }

    @Test
    void testPatternsCompleteMatch() {
        String[] pattern = "to be".split("\\s+");
        double percentage = new MatchTextPercentage(STRING_MAP, pattern)
                .calculatePercentageWordMatchInText();
        String result = DECIMAL_FORMAT.format(percentage);
        assertEquals("100", result);
    }

    @Test
    void testPatternsPartialMatch() {
        String[] pattern = "to not".split("\\s+");
        double percentage = new MatchTextPercentage(STRING_MAP, pattern)
                .calculatePercentageWordMatchInText();
        String result = DECIMAL_FORMAT.format(percentage);
        assertEquals("75", result);
    }
}