package ru.cwl.otus.hw16.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by tischenko on 16.03.2018 17:05.
 */
public class AppLoader {
    private static final Logger log = LoggerFactory.getLogger(AppLoader.class);
    public static void main(String[] args) throws IOException, InterruptedException {
        log.info("loader started");
        ProcessBuilder pb = new ProcessBuilder("java", "-Daaa=bbb", "-jar", "hw16/front/target/front-1.0.jar", "front-1", "localhost", "2345");
        Process p = pb.start();
        log.info("process {} started", p);

        InputStream is = p.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));


        String line = null;
        log.info("---");
        while ((line = reader.readLine()) != null) {
            log.info("p: " + line);
        }
        log.info("---");

        while (p.isAlive()){
            Thread.sleep(1);
            System.out.print(".");
        }
        log.info("exit value:{}", p.exitValue());
    }
}
