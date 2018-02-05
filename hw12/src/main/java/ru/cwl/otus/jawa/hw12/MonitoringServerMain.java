package ru.cwl.otus.jawa.hw12;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import ru.cwl.otus.hw10.DBService;
import ru.cwl.otus.hw10.hibernate.DBServiceHibernateImpl;
import ru.cwl.otus.hw10.model.DataSet;
import ru.cwl.otus.hw10.model.UserDataSet;
import ru.cwl.otus.hw11.CacheEngine;
import ru.cwl.otus.hw11.CachedDBService;
import ru.cwl.otus.hw11.Key;
import ru.cwl.otus.jawa.hw12.servlets.CacheMonitoringServlet;


/**
 * Created by vadim.tishenko
 * on 21.01.2018 10:24.
 */
public class MonitoringServerMain {
    public static void main(String[] args) throws Exception {

        CacheEngine<Key, DataSet> cacheEngine;
        DBServiceHibernateImpl dbService;
        DBService cachedDbService;
        UserDataSet user1;


        cacheEngine = new CacheEngine<>();
        dbService = new DBServiceHibernateImpl();
        cachedDbService = new CachedDBService(dbService, cacheEngine);

        final int PORT = 8888;

        ResourceHandler rh = new ResourceHandler();
        rh.setBaseResource(Resource.newClassPathResource("/pub/"));
        ResourceHandler rh2 = new ResourceHandler();
        rh2.setBaseResource(Resource.newClassPathResource("/pub2/"));
        //rh.setDirectoriesListed(false);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new CacheMonitoringServlet(cacheEngine)), "/cache");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(rh, rh2, context));


        PayLoadService pl = new PayLoadService(cachedDbService);


        server.start();

        Thread thread = new Thread(pl);
        thread.start();

        //server.toString()
        //System.out.println(server.dump());
        System.out.println("started! http://localhost:" + PORT);
        server.join();
    }
}
