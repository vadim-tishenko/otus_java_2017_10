package ru.cwl.hw02.agent;

/**
 * Created by vadim.tishenko
 * on 23.10.2017 22:12.
 */

import java.lang.instrument.Instrumentation;

public class MemoryAgent {

    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("init MemoryAgent");
        MemoryAgent.instrumentation = instrumentation;
    }

    public static long getSize(Object obj) {
        if (instrumentation == null) {
            throw new IllegalStateException("MemoryAgent not initialised");
        }
        return instrumentation.getObjectSize(obj);
    }
}