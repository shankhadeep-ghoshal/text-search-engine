package io;

public abstract class AbstractDataReader implements DataReader {
    protected final String dataLocation;

    public AbstractDataReader() {
        this.dataLocation = "";
    }

    protected AbstractDataReader(String dataLocation) {
        this.dataLocation = dataLocation;
    }
}