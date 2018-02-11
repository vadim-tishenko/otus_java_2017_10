package ru.cwl.otus.hw13.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.cwl.otus.hw11.CacheEngine;
import ru.cwl.otus.hw12.PayLoadService;
import ru.cwl.otus.hw13.ApplicationConfig;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by vadim.tishenko
 * on 11.02.2018 16:10.
 */
@WebListener
public class ApplicationContextBoot implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ApplicationContext context =
                new AnnotationConfigApplicationContext(ApplicationConfig.class);

        CacheEngine ce = context.getBean(CacheEngine.class);
        sce.getServletContext().setAttribute("cache",ce);

        PayLoadService payLoadService = context.getBean(PayLoadService.class);
        Thread thread = new Thread(payLoadService);
        thread.start();

        System.out.println("contextInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("contextDestroyed");

    }
}
