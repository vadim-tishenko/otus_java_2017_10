package ru.cwl.otus.hw05.xxunit;

import com.google.common.reflect.ClassPath;
import ru.cwl.otus.hw05.xxunit.annotation.After;
import ru.cwl.otus.hw05.xxunit.annotation.Before;
import ru.cwl.otus.hw05.xxunit.annotation.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by vadim.tishenko
 * on 11.11.2017 18:46.
 */
public class TestRunner {

    // guava version......
    public static void runTestsForPackageGV(String pkg) {
        final ClassLoader classLoader = TestRunner.class.getClassLoader();

        ClassPath classpath = null;
        try {
            classpath = ClassPath.from(classLoader);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClassesRecursive(pkg)) {
            runTest(classInfo.load());
        }
    }

    public static void runTestsForPackage(String pkg) {
        System.out.println("run tests for package: " + pkg);
        Collection<String> classNames = ResourceList.getResources(s -> s.startsWith(pkg + "."));
        runTestForClasses(classNames);
    }

    public static void runTestForClasses(Collection<String> classNames) {
        for (String className : classNames) {
            runTestForClass(className);
        }
    }

    static void runTestForClass(String className) {
        try {
            Class<?> c = Thread.currentThread().getContextClassLoader().loadClass(className);
            runTest(c);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void runTestForClasses(Class<?>[] classesForTesty) {
        for (Class<?> aClass : classesForTesty) {
            runTest(aClass);
        }
    }

    public static void runTest(Class<?> c) {
        Method beforeTest = null;
        Method afterTest = null;
        List<Method> tests = new ArrayList<>();
        for (Method method : c.getDeclaredMethods()) {
            if (method.getAnnotation(Test.class) != null) {
                tests.add(method);
                continue;
            }
            if (method.getAnnotation(Before.class) != null) {
                beforeTest = method;
                continue;
            }
            if (method.getAnnotation(After.class) != null) {
                afterTest = method;
            }
        }

        if (tests.isEmpty()) {
            return;
        }
        System.out.println("run test for class: " + c.getCanonicalName());
        try {
            for (Method test : tests) {
                Object obj = c.newInstance();
                if (beforeTest != null) {
                    beforeTest.invoke(obj);
                }
                try {

                    test.invoke(obj);
                } catch (InvocationTargetException ae) {
                    if (ae.getTargetException() instanceof AssertException) {
                        System.out.println("test " + test.getName() + " FAILED!");
                        System.out.print(ae.getTargetException().getMessage());
                        break;
                    }
                }
                System.out.println("test " + test.getName() + " PASSED!");
                if (afterTest != null) {
                    afterTest.invoke(obj);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        System.out.println();
    }
}
