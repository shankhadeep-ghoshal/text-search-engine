import file.io.FileDriver;

import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Application {
    private static final ExecutorService IO_THREAD_POOL = Executors.newFixedThreadPool(24);
    private static final ExecutorService COMPUTATION_THREAD_POOL =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static void closeThreadPools(ExecutorService threadPool) {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
                if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Pool did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        if (args == null) {
            throw new IllegalArgumentException("Please enter location of folder containing files");
        }

        String filesLocation = args[0];
        System.out.println(Paths.get(filesLocation).toAbsolutePath().toString());
        FileDriver fileDriver = new FileDriver(System.out,
                IO_THREAD_POOL,
                COMPUTATION_THREAD_POOL,
                filesLocation);
        fileDriver.initialize();

        try(Scanner keyboard = new Scanner(System.in)) {
            while (true) {
                System.out.print("search> ");
                final String line = keyboard.nextLine();
                if (!line.equals(":quit")) {
                    fileDriver.checkPattern(line);
                } else {
                    closeThreadPools(COMPUTATION_THREAD_POOL);
                    closeThreadPools(IO_THREAD_POOL);
                    break;
                }
            }
        }
    }
}