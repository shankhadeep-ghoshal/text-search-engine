package file;

import algo.MatchTextPercentage;
import etl.ETL;
import file.io.FileDataReader;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class FilePerformEndToEndOnOne {
    private final String fileLocation;
    private String rawTextDataRead;

    private Map<String, Set<Integer>> wordMap;
    private double matchPercentage;

    public FilePerformEndToEndOnOne(final String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public FilePerformEndToEndOnOne readData() {
        this.rawTextDataRead = new FileDataReader(fileLocation).readDataFromSource();
        return this;
    }

    public FilePerformEndToEndOnOne performEtl() {
        this.wordMap = new ETL(rawTextDataRead)
                .removeSpecialCharacters()
                .removeExtraWhitespace()
                .toLowerCase(Locale.getDefault())
                .createWordMap()
                .getWordMap();
        return this;
    }

    public FilePerformEndToEndOnOne calculateMatchPercentage(final String inputPattern) {
        this.matchPercentage = new MatchTextPercentage(wordMap,
                new ETL(inputPattern)
                        .removeSpecialCharacters()
                        .removeExtraWhitespace()
                        .toLowerCase(Locale.getDefault())
                        .getData()
                        .split("\\s+"))
                .calculatePercentageWordMatchInText();
        return this;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public double getMatchPercentage() {
        return matchPercentage;
    }
}