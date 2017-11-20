package ru.cwl.otus.hw05.xxunit;

import java.util.Objects;

/**
 * Created by vadim.tishenko
 * on 18.11.2017 16:38.
 */
public class Assert {
    public static void assertEquals(String expected, String actual) {
        if (Objects.equals(expected, actual)) return;
        final String msg = "expected: " + expected + "\nactual: " + actual+"\n";
        throw new AssertException(msg);
    }
}
