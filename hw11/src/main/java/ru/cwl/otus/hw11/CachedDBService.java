package ru.cwl.otus.hw11;

import java.util.List;
import java.util.Objects;

import ru.cwl.otus.hw10.DBService;
import ru.cwl.otus.hw10.hibernate.DBServiceHibernateImpl;
import ru.cwl.otus.hw10.model.DataSet;

/**
 * Created by tischenko on 31.01.2018 11:32.
 */
public class CachedDBService implements DBService {
    DBService dbService;
    CacheEngine<Key, DataSet> cacheEngine;// = new CacheEngine<>();

    public CachedDBService(DBService dbService, CacheEngine<Key, DataSet> cacheEngine) {
        this.dbService = dbService;
        this.cacheEngine = cacheEngine;
    }

    @Override
    public <T extends DataSet> void save(T entity) {
        dbService.save(entity);
        Key key = Key.of(entity);
        cacheEngine.put(key,entity);
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        Key key = new Key(id, clazz);
        DataSet entity = cacheEngine.get(key);
        if (entity == null) {
            entity = dbService.load(id, clazz);
            if (entity != null) {
                cacheEngine.put(key, entity);
            }
        }
        return (T) entity;
    }

    @Override
    public <T extends DataSet> List<T> load(Class<T> clazz) {
        List<T> result = dbService.load(clazz);
        for (T entity : result) {
            Key key = Key.of(entity);
            cacheEngine.put(key, entity);
        }
        return result;
    }

    @Override
    public void close() {
        dbService.close();
        cacheEngine.dispose();
    }
}

