package io;

import file.io.FileDataReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestFileDataReader {
    private static final String FILE_1_LOCATION = "src\\test\\resources\\test1.txt";
    private static final String FILE_2_LOCATION = "src\\test\\resources\\test2.txt";

    @Test
    void testReadDataFromSource1() {
        final String expectedTextResult = "This is a test file";
        FileDataReader fileDataReader = new FileDataReader(FILE_1_LOCATION);
        String readResult = fileDataReader.readDataFromSource();
        assertEquals(expectedTextResult, readResult);
    }

    @Test
    void testReadDataFromSource2() {
        final String expectedTextResult = "People let me tell you 'bout my best friend, he's a warm-hearted person who'll love me till the end. People let me tell you 'bout my best friend, he's a one boy cuddly toy, my up, my down, my pride and joy. People let me tell you 'bout him he's so much fun whether we're talkin' man to man or whether we're talking son to son. Cause he's my best friend. Yes he's my best friend." +
                "Hey Tony! I like the things you do! Hey Tony! If I could I would be you! You're my one and only tiger, with my one and only taste! You know how to take a breakfast and make it mmm great! Frosted Flakes! They're more than good, they're great!" +
                "There is nothing wrong with your television set. Do not attempt to adjust the picture. We are controlling transmission. We will control the horizontal, we will control the vertical. We can change the focus to a soft blur or sharpen it to crystal clarity. For the next hour, sit quietly and we will control all that you see and hear. You are about to participate in a great adventure. You are about to experience the awe and mystery which reaches from the inner mind to the Outer Limits.";
        FileDataReader fileDataReader = new FileDataReader(FILE_2_LOCATION);
        String readResult = fileDataReader.readDataFromSource();
        assertEquals(expectedTextResult, readResult);
    }

    @Test
    void testReadWhenFileLocationIncorrect() {
        String readResult = new FileDataReader("").readDataFromSource();
        assertEquals("", readResult);
    }
}