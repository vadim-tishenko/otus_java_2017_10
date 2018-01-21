package ru.cwl.otus.jawa.hw12.mustache;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;
import java.util.List;

/**
 * Created by vadim.tishenko
 * on 21.01.2018 22:00.
 * https://jknack.github.io/handlebars.java/
 */
public class Example2 {

    public static void main(String[] args) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader("/", "");
        Handlebars hb=new Handlebars(loader);
        Template t = hb.compile("templates/template.mustache");
        //MustacheFactory mf = new DefaultMustacheFactory();

        final Ctx context = new Ctx();
        List<Ctx.Item> items = context.getItems();
        System.out.println(t.apply(context));
        //Mustache mustache = mf.compile("templates/template.mustache");
        //mustache.execute(new PrintWriter(System.out), new Context()).flush();

        Handlebars handlebars = new Handlebars();
        Template template = handlebars.compileInline("Hello {{this}}!");
        System.out.println(template.apply("Handlebars.java"));
    }
}

