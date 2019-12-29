import file.io.FileDriver;

import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Application {
    private static final ExecutorService IO_THREAD_POOL = Executors.newFixedThreadPool(24);

    private static void closeThreadPool() {
        IO_THREAD_POOL.shutdown();
        try {
            if (!IO_THREAD_POOL.awaitTermination(60, TimeUnit.SECONDS)) {
                IO_THREAD_POOL.shutdownNow();
                if (!IO_THREAD_POOL.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Pool did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            IO_THREAD_POOL.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        if (args == null) {
            throw new IllegalArgumentException("Please enter location of folder containing files");
        }

        String filesLocation = args[0];
        System.out.println(Paths.get(filesLocation).toAbsolutePath().toString());
        FileDriver fileDriver = new FileDriver(System.out, IO_THREAD_POOL, filesLocation);
        fileDriver.initialize();

        try(Scanner keyboard = new Scanner(System.in)) {
            while (true) {
                System.out.print("search> ");
                final String line = keyboard.nextLine();
                if (line.equals(":quit")) {
                    closeThreadPool();
                    break;
                }
                else {
                    fileDriver.checkPattern(line);
                }
            }
        }
    }
}