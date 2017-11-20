package ru.cwl.otus.hw05.xxunit;

/**
 * Created by vadim.tishenko
 * on 16.11.2017 22:41.
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * list resources available from the classpath @ *
 */
public class ResourceList {

    public static Collection<String> getResources(Predicate<String> predicate) {
        return getResources("", predicate);
    }

    static Collection<String> getResources(final String pack, Predicate<String> predicate) {
        final ArrayList<String> result = new ArrayList<String>();
        final String classPath = System.getProperty("java.class.path", ".");
        final String[] classPathElements = classPath.split(System.getProperty("path.separator"));
        for (final String element : classPathElements) {
            result.addAll(getResources(element, pack));
        }

        result.removeIf(predicate.negate());

        return result;
    }

    private static Collection<String> getResources(final String element, final String pack) {
        final ArrayList<String> result = new ArrayList<String>();
        final File file = new File(element);
        if (file.isDirectory()) {
            result.addAll(getResourcesFromDirectory(file, pack));
        } else {
            result.addAll(getResourcesFromJarFile(file, pack));
        }
        return result;
    }

    private static Collection<String> getResourcesFromJarFile(final File file, final String pack) {
        final ArrayList<String> result = new ArrayList<String>();
        ZipFile zf;
        try {
            zf = new ZipFile(file);
        } catch (final ZipException e) {
            throw new Error(e);
        } catch (final IOException e) {
            throw new Error(e);
        }
        final Enumeration e = zf.entries();
        while (e.hasMoreElements()) {
            final ZipEntry ze = (ZipEntry) e.nextElement();
            final String fileName = ze.getName();
            final boolean accept = fileName.endsWith(".class");//pattern.matcher(fileName).matches();
            if (accept) {
                result.add(fileName.replace('/', '.').replace(".class", ""));
            }
        }
        try {
            zf.close();
        } catch (final IOException e1) {
            throw new Error(e1);
        }
        return result;
    }

    private static Collection<String> getResourcesFromDirectory(final File directory, final String pack) {
        final ArrayList<String> result = new ArrayList<String>();
        final File[] fileList = directory.listFiles();
        for (final File file : fileList) {
            if (file.isDirectory()) {
                String newPack = "".equals(pack) ? file.getName() : pack + '.' + file.getName();
                result.addAll(getResourcesFromDirectory(file, newPack));
            } else {
                final String fileName = file.getName();
                final boolean accept = fileName.endsWith(".class");
                //pattern.matcher(fileName).matches();
                if (accept) {
                    result.add(pack + '.' + fileName.replace(".class", ""));
                }
            }
        }
        return result;
    }


    public static void main(final String[] args) {

        final Collection<String> list = ResourceList.getResources(s -> s.startsWith("ru.cwl.otus.hw05.tests."));
        for (final String name : list) {
            System.out.println(name);
        }
    }
}