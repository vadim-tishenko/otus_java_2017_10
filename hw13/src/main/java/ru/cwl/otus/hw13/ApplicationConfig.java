package ru.cwl.otus.hw13;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.cwl.otus.hw10.DBService;
import ru.cwl.otus.hw10.hibernate.DBServiceHibernateImpl;
import ru.cwl.otus.hw10.model.DataSet;
import ru.cwl.otus.hw11.CacheEngine;
import ru.cwl.otus.hw11.CachedDBService;
import ru.cwl.otus.hw11.Key;
import ru.cwl.otus.hw12.PayLoadService;

/**
 * Created by vadim.tishenko
 * on 09.02.2018 22:48.
 */
@Configuration
@ComponentScan
public class ApplicationConfig {

    @Bean
    CachedDBService getCDBService(CacheEngine<Key, DataSet> ce, DBService dbService) {
        return new CachedDBService(dbService, ce);
    }

    @Bean
    DBService getDBService() {
        return new DBServiceHibernateImpl();
    }

    @Bean
    CacheEngine<Key, DataSet> getCache() {
        return new CacheEngine<>();
    }


    @Bean
    PayLoadService payLoadService(@Qualifier("getCDBService") DBService dbService){
        return new PayLoadService(dbService);
    }

}
