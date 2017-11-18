package ru.cwl.otus.hw05;

import static ru.cwl.otus.hw05.xxunit.TestRunner.*;

/**
 * Created by vadim.tishenko
 * on 18.11.2017 16:19.
 */
public class Main {

    public static void main(String[] args)  {
        runTest(ru.cwl.otus.hw05.tests.sys.TestClass3.class);

        Class<?> classesForTesty[]={
                ru.cwl.otus.hw05.tests.TestClass1.class,
                ru.cwl.otus.hw05.tests.TestClass2.class,
                ru.cwl.otus.hw05.tests.sys.TestClass3.class
        };

        runTestForClasses(classesForTesty);

        runTestsForPackage("ru.cwl.otus.hw05.tests");
    }




}
