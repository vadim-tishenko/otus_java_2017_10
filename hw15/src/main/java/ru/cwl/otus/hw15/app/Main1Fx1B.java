package ru.cwl.otus.hw15.app;

import org.eclipse.jetty.server.Server;
import org.thymeleaf.TemplateEngine;
import ru.cwl.otus.hw10.DBService;
import ru.cwl.otus.hw10.hibernate.DBServiceHibernateImpl;
import ru.cwl.otus.hw10.model.DataSet;
import ru.cwl.otus.hw11.CacheEngine;
import ru.cwl.otus.hw11.CachedDBService;
import ru.cwl.otus.hw11.Key;
import ru.cwl.otus.hw12.PayLoadService;
import ru.cwl.otus.hw12.TemplateEngineBuilder;
import ru.cwl.otus.hw15.cache.CacheServiceImpl;
import ru.cwl.otus.hw15.front.FrontendServiceImpl;
import ru.cwl.otus.hw15.messageSystem.Address;
import ru.cwl.otus.hw15.messageSystem.MessageSystem;

/**
 * Created by tischenko on 05.03.2018 22:06.
 */
public class Main1Fx1B {

    public static void main(String[] args) throws Exception {
        MessageSystem ms = new MessageSystem();

        CacheEngine<Key, DataSet> cacheEngine;
        DBServiceHibernateImpl dbService;
        DBService cachedDbService;

        cacheEngine = new CacheEngine<>();
        dbService = new DBServiceHibernateImpl();
        cachedDbService = new CachedDBService(dbService, cacheEngine);
        TemplateEngine templateEngine = TemplateEngineBuilder.build();

        FrontendService frontendService = new FrontendServiceImpl(new Address("front"), ms, templateEngine);
        CacheService cacheService = new CacheServiceImpl(new Address("cache"), ms, cacheEngine);

        ms.addAddressee(frontendService);
        ms.addAddressee(cacheService);
        ms.start();

        final int PORT = 8888;

        Server server = WebServer.configureAndRunWebServer(PORT, frontendService);

        runPayLoad(cachedDbService);

        server.join();

        ms.dispose();
    }

    private static void runPayLoad(DBService dbService) {
        PayLoadService payLoadService = new PayLoadService(dbService);
        Thread thread = new Thread(payLoadService);
        thread.start();
    }

}
