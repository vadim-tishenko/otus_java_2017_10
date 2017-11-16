package ru.cwl.otus.hw05;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by vadim.tishenko
 * on 16.11.2017 20:35.
 */
public class ClassFinder {
    public static void main(String[] args) throws IOException {
        ClassFinder cf = new ClassFinder();
        final ClassLoader loader = cf.getClass().getClassLoader();
        Enumeration<URL> res = Thread.currentThread().getContextClassLoader().getResources("ru");


        while (res.hasMoreElements()) {
            URL el = res.nextElement();
            File file = new File(el.getFile());
            if (file.isDirectory()) {

                System.out.println(file.list());

                //System.out.println("dir");
            }
            System.out.println(el);
        }
        List<String> res33 = cf.getResourceFiles("ru/cwl/otus/hw05");
        Collection<String> res55 = getResources(null);
        ResourceList.getResources(null);

    }

    public static Collection<String> getResources(final Pattern pattern) {
        final ArrayList<String> retval = new ArrayList<String>();
        final String classPath = System.getProperty("java.class.path", ".");
        final String[] classPathElements = classPath.split(System.getProperty("path.separator"));
        for (final String element : classPathElements) {
            retval.add(element /*"getResources(element, pattern)"*/);
        }
        return retval;
    }

    private List<String> getResourceFiles(String path) throws IOException {
        List<String> filenames = new ArrayList<>();

        try (InputStream in = getResourceAsStream(path);
                BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String resource;

            while ((resource = br.readLine()) != null) {
                filenames.add(resource);
            }
        }

        return filenames;
    }

    private InputStream getResourceAsStream(String resource) {
        final InputStream in = getContextClassLoader().getResourceAsStream(resource);

        return in == null ? getClass().getResourceAsStream(resource) : in;
    }

    private ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
