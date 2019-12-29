import file.io.FileDriver;
import org.junit.jupiter.api.*;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class TestFileDriver {
    private static final Random RANDOM = new Random(1000);
    private static final String DIRECTORY = "src\\test\\resources\\";
    private static final String[] PATTERNS = {"I can do that",
            "This is it",
            "I want it that way",
            "balchera",
            "chutiya"};

    private static ExecutorService THREAD_POOL;
    private static PrintStream OUTPUT_STREAM;
    private static FileDriver FILE_DRIVER;

    static void setUp() {
        THREAD_POOL = Executors.newFixedThreadPool(100);
        OUTPUT_STREAM = System.out;
        FILE_DRIVER = new FileDriver(OUTPUT_STREAM, THREAD_POOL, DIRECTORY);
        FILE_DRIVER.initialize();
    }

    @TestFactory
    Stream<DynamicTest> testForExecutingEndToEnd() {
        setUp();
        return IntStream.range(0, 10)
                .mapToObj(i ->
                        DynamicTest.dynamicTest(String.valueOf(i),
                                () -> FILE_DRIVER
                                        .checkPattern(PATTERNS[RANDOM
                                                .nextInt(PATTERNS.length)])));
    }

    @AfterAll
    static void tearDown() {
        THREAD_POOL.shutdown();
        try {
            if (!THREAD_POOL.awaitTermination(60, TimeUnit.SECONDS)) {
                THREAD_POOL.shutdownNow();
                if (!THREAD_POOL.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Pool did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            THREAD_POOL.shutdownNow();
            Thread.currentThread().interrupt();
        }

        OUTPUT_STREAM.close();
    }
}