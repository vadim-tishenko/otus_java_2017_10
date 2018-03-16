package ru.cwl.otus.hw16.loader;

import java.io.IOException;

/**
 * Created by tischenko on 16.03.2018 17:05.
 */
public class AppLoader {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("loader started");
        ProcessBuilder pb=new ProcessBuilder("java","-jar","hw16/front/target/front-1.0.jar");
        Process p = pb.start();

        while (p.isAlive()){
            Thread.sleep(1);
            System.out.print(".");
        }
        System.out.println(p.exitValue());
    }
}
