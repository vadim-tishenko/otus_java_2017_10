package ru.cwl.otus.jawa.hw12.mustache;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by vadim.tishenko
 * on 21.01.2018 22:00.
 * https://jknack.github.io/handlebars.java/
 */
public class Example {

    public static void main(String[] args) throws IOException {
        MustacheFactory mf = new DefaultMustacheFactory();

        Mustache mustache = mf.compile("templates/template.mustache");
        mustache.execute(new PrintWriter(System.out), new Ctx()).flush();
    }
}

