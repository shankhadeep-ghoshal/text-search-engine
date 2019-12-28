import java.util.Locale;

public class ETL {
    private final String data;

    public ETL(String data) {
        assert data != null;
        this.data = data;
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


    public String getData() {
        return this.data;
    }
}