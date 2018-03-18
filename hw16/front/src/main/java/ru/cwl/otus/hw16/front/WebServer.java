package ru.cwl.otus.hw16.front;

import java.util.Scanner;

/**
 * Created by tischenko on 16.03.2018 17:08.
 */
public class WebServer {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("web server started");
        for (String arg : args) {
            System.out.println("arg:" + arg);
        }
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if ("q".equals(line)) break;
            System.out.println("in:" + line);
        }

        // Thread.sleep(5000);
    }
}
