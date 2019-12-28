import algo.MatchTextPercentage;
import etl.ETL;
import io.FileDataReader;
import org.junit.jupiter.api.Test;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestCombineAlgoReadETL {

    @Test
    void testWordMatchInTextFile() {
        Map<String, Set<Integer>> obtainedResult =
                new ETL(new FileDataReader("src\\test\\resources\\test2.txt")
                        .readDataFromSource())
                        .removeSpecialCharacters()
                        .removeExtraWhitespace()
                        .toLowerCase(Locale.getDefault())
                        .createWordMap()
                        .getWordMap();

        String[] pattern = "there is nothing wrong with your television set".split("\\s+");
        double percentage = new MatchTextPercentage(obtainedResult, pattern)
                .calculatePercentageWordMatchInText();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);
        String result = decimalFormat.format(percentage);
        assertEquals("100", result);
    }
}