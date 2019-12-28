package io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileDataReader extends AbstractDataReader {

    public FileDataReader(String fileLocation) {
        super(fileLocation);
    }

    @Override
    public String readDataFromSource() {
        StringBuilder stringBuilder = new StringBuilder();

        try (Stream<String> fileStream = Files.lines(Paths.get(dataLocation).toAbsolutePath(),
                StandardCharsets.UTF_8)) {
            fileStream.forEach(stringBuilder::append);
        } catch (IOException ignored) {

        }

        return stringBuilder.toString();
    }
}