package ru.cwl.otus.hw15.app;

import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import ru.cwl.otus.hw15.system.CacheService;
import ru.cwl.otus.hw15.system.FrontendService;

/**
 * Created by tischenko on 05.03.2018 22:06.
 */
public class Main5Fx1B {
    Logger log = LoggerFactory.getLogger(Main5Fx1B.class);

    public static void main(String[] args) throws Exception {
        MessageSystem ms = new MessageSystem();

        CacheEngine<Key, DataSet> cacheEngine;
        DBServiceHibernateImpl dbService;
        DBService cachedDbService;

        cacheEngine = new CacheEngine<>();
        dbService = new DBServiceHibernateImpl();
        cachedDbService = new CachedDBService(dbService, cacheEngine);
        TemplateEngine templateEngine = TemplateEngineBuilder.build();

        CacheService cacheService = new CacheServiceImpl(new Address("cache"), ms, cacheEngine);

        ms.addAddressee(cacheService);

        final int PORT = 8888;
        final int FRONT_N = 5;
        Server[] servers = new Server[FRONT_N];

        for (int n = 0; n < FRONT_N; n++) {
            FrontendService frontendService = new FrontendServiceImpl(new Address("front-" + n), ms, templateEngine);
            ms.addAddressee(frontendService);
            servers[n] = WebServer.configureAndRunWebServer(PORT + n, frontendService);
        }

        ms.start();

        runPayLoad(cachedDbService);

        for (Server server : servers) {
            server.join();
        }

        ms.dispose();
    }

    private static void runPayLoad(DBService dbService) {
        PayLoadService payLoadService = new PayLoadService(dbService);
        Thread thread = new Thread(payLoadService);
        thread.start();
    }

}
