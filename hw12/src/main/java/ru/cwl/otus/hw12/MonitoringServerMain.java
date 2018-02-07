package ru.cwl.otus.hw12;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import ru.cwl.otus.hw10.DBService;
import ru.cwl.otus.hw10.hibernate.DBServiceHibernateImpl;
import ru.cwl.otus.hw10.model.DataSet;
import ru.cwl.otus.hw11.CacheEngine;
import ru.cwl.otus.hw11.CachedDBService;
import ru.cwl.otus.hw11.Key;
import ru.cwl.otus.hw12.servlets.AuthFilter;
import ru.cwl.otus.hw12.servlets.CacheMonitoringServlet;
import ru.cwl.otus.hw12.servlets.LoginServlet;

import javax.servlet.DispatcherType;
import java.util.EnumSet;


/**
 * Created by vadim.tishenko
 * on 21.01.2018 10:24.
 */
public class MonitoringServerMain {
    public static void main(String[] args) throws Exception {

        CacheEngine<Key, DataSet> cacheEngine;
        DBServiceHibernateImpl dbService;
        DBService cachedDbService;

        cacheEngine = new CacheEngine<>();
        dbService = new DBServiceHibernateImpl();
        cachedDbService = new CachedDBService(dbService, cacheEngine);

        final int PORT = 8888;

        ResourceHandler rh = new ResourceHandler();
        rh.setBaseResource(Resource.newClassPathResource("/pub/"));

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addFilter(new FilterHolder(new AuthFilter()),  "/cache", EnumSet.of(DispatcherType.REQUEST));
        context.addServlet(new ServletHolder(new CacheMonitoringServlet(cacheEngine)), "/cache");
        context.addServlet(new ServletHolder(new LoginServlet()), "/login");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(rh, context));


        server.start();

        runPayLoad(cachedDbService);

        System.out.println("started! http://localhost:" + PORT);
        server.join();
    }

    private static void runPayLoad(DBService dbService) {
        PayLoadService payLoadService = new PayLoadService(dbService);
        Thread thread = new Thread(payLoadService);
        thread.start();
    }
}
