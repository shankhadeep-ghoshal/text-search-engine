package etl;

import java.util.*;

/**
 * Recommended way to use is to initialize this using the {@link #ETL(String)} constructor and
 * chaining {@link #removeSpecialCharacters()}, followed by {@link #removeExtraWhitespace()},
 * followed by {@link #toLowerCase(Locale)} and then converting it to word map by chaining
 * {@link #createWordMap()}
 */
public class ETL {
    private final String data;
    private final Map<String, Set<Integer>> wordMap;

    /**
     * @apiNote Placed only to enable Reflection. Not recommended for usage. Use
     * {@link #ETL(String)} instead
     */
    public ETL() {
        this.data = "";
        this.wordMap = new HashMap<>();
    }

    public ETL(String data) {
        assert data != null;
        this.data = data;
        this.wordMap = new HashMap<>();
    }

    /**
     * Replaces all punctuations with blank space
     * @return a new instance of {@link #ETL(String)} with the updated value
     */
    public ETL removeSpecialCharacters() {
        String newData = data.replaceAll("[\\p{Punct}]", " ");
        return new ETL(newData);
    }

    /**
     * Replaces all white spaces with a single white space. This takes into consideration the
     * fact that there can be multiple white spaces consecutively and/or tab characters
     * @return a new instance of {@link #ETL(String)} with the updated value
     */
    public ETL removeExtraWhitespace() {
        String newData = data.replaceAll("\\s+", " ");
        return new ETL(newData.substring(0, newData.length() - 1));
    }

    public ETL toLowerCase(final Locale locale) {
        String newData = data.toLowerCase(locale);
        return new ETL(newData);
    }

    public ETL createWordMap() {
        String[] words = data.split("\\s");

        for (int i = 0; i < words.length; i++) {
            if (!wordMap.containsKey(words[i])) {
                wordMap.put(words[i], new TreeSet<>());
            }
            wordMap.get(words[i]).add(i);
        }

        return this;
    }

    public Map<String, Set<Integer>> getWordMap() {
        return this.wordMap;
    }

    public String getData() {
        return this.data;
    }
}