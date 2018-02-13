package ru.cwl.otus.hw12;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * Created by vadim.tishenko
 * on 13.02.2018 20:57.
 */
public class TemplateEngineBuilder {
    static TemplateEngine build() {
        //log.info("tpl init");
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        // HTML is the default mode, but we will set it anyway for better understanding of code
        templateResolver.setTemplateMode(TemplateMode.HTML);

        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        // Set template cache TTL to 1 hour. If not set, entries would live in cache until expelled by LRU
        templateResolver.setCacheTTLMs(3600000L);

        // Cache is set to true by default. Set to false if you want templates to
        // be automatically updated when modified.
        templateResolver.setCacheable(true);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        //log.info("tpl init done");
        return templateEngine;
    }
}
