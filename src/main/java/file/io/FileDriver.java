package file.io;

import file.FilePerformEndToEndOnOne;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileDriver {
    private final PrintStream outputStream;
    private final ExecutorService threadPoolIO;
    private final ExecutorService threadPoolComputation;
    private final List<String> fileLocation;
    private final String folderLocation;

    private List<CompletableFuture<FilePerformEndToEndOnOne>> tasksList;

    public FileDriver(final PrintStream outputStream,
                      final ExecutorService threadPoolIO,
                      final ExecutorService threadPoolComputation,
                      final String folderLocation) {
        assert folderLocation != null;
        assert threadPoolIO != null;
        assert threadPoolComputation != null;
        assert outputStream != null;

        this.outputStream = outputStream;
        this.threadPoolIO = threadPoolIO;
        this.threadPoolComputation = threadPoolComputation;
        this.folderLocation = folderLocation;
        this.fileLocation = new ArrayList<>(0);
    }

    public void initialize() {
        findAllFilesInDirectory();
        System.out.println(fileLocation.size()
                + " file/s read in directory "
                + folderLocation);
        this.tasksList = fileLocation.stream()
                .map(s -> CompletableFuture.supplyAsync(() ->
                new FilePerformEndToEndOnOne(s).readData(), threadPoolIO)
                .thenApplyAsync(FilePerformEndToEndOnOne::performEtl, threadPoolComputation))
                .collect(Collectors.toList());
    }

    public void checkPattern(String pattern) {
        tasksList.forEach(filePerformEndToEndOnOneCompletableFuture ->
                filePerformEndToEndOnOneCompletableFuture
                        .thenApplyAsync(filePerformEndToEndOnOne ->
                                filePerformEndToEndOnOne.calculateMatchPercentage(pattern),
                                threadPoolComputation)
                        .thenAccept(this::printData));
    }

    private void printData(FilePerformEndToEndOnOne filePerformEndToEndOnOne) {
        String fileLocation = filePerformEndToEndOnOne.getFileLocation();
        if (filePerformEndToEndOnOne.getMatchPercentage() > 0.0) {
            outputStream.println(fileLocation
                    + " : "
                    + filePerformEndToEndOnOne.getMatchPercentage());
        } else {
            outputStream.println(fileLocation + " : " + "No matches found");
        }
    }

    private void findAllFilesInDirectory() {
        try (Stream<Path> walker = Files.walk(Paths.get(folderLocation).toAbsolutePath())) {
            fileLocation.addAll(walker.filter(Files::isRegularFile)
                    .map(path -> path.toAbsolutePath().toString())
                    .collect(Collectors.toList()));
        } catch (IOException ignored) {

        }
    }
}