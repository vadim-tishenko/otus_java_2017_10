package ru.cwl.otus.hw16.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by tischenko on 16.03.2018 17:05.
 */
public class AppLoader {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("loader started");
        ProcessBuilder pb=new ProcessBuilder("java","-jar","hw16/front/target/front-1.0.jar","front-1");
        Process p = pb.start();

        InputStream is = p.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line = null;
        System.out.println("---");
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println("---");

        while (p.isAlive()){
            Thread.sleep(1);
            System.out.print(".");
        }
        System.out.println(p.exitValue());
    }
}
