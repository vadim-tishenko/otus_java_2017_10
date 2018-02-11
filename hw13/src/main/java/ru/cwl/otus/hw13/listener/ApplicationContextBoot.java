package ru.cwl.otus.hw13.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import ru.cwl.otus.hw11.CacheEngine;
import ru.cwl.otus.hw12.PayLoadService;
import ru.cwl.otus.hw13.ApplicationConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by vadim.tishenko
 * on 11.02.2018 16:10.
 */
@WebListener
public class ApplicationContextBoot implements ServletContextListener {
    Logger log= LoggerFactory.getLogger(ApplicationContextBoot.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ApplicationContext context =
                new AnnotationConfigApplicationContext(ApplicationConfig.class);

        final ServletContext servletContext = sce.getServletContext();

        CacheEngine cacheEngine = context.getBean(CacheEngine.class);
        servletContext.setAttribute("cache",cacheEngine);

        TemplateEngine templateEngine = buildTemplateEngine(servletContext);
        servletContext.setAttribute("templateEngine",templateEngine);

        PayLoadService payLoadService = context.getBean(PayLoadService.class);
        Thread thread = new Thread(payLoadService);
        thread.start();

        log.info("contextInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("contextDestroyed");
    }

    TemplateEngine buildTemplateEngine(ServletContext servletContext) {
        log.info("tpl init");
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);

        // HTML is the default mode, but we will set it anyway for better understanding of code
        templateResolver.setTemplateMode(TemplateMode.HTML);
        // This will convert "home" to "/WEB-INF/templates/home.html"
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        // Set template cache TTL to 1 hour. If not set, entries would live in cache until expelled by LRU
        templateResolver.setCacheTTLMs(Long.valueOf(3600000L));

        // Cache is set to true by default. Set to false if you want templates to
        // be automatically updated when modified.
        templateResolver.setCacheable(true);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        log.info("tpl init done");
        return templateEngine;
    }

}
