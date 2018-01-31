package ru.cwl.otus.hw11;

import java.util.List;
import java.util.Objects;

import ru.cwl.otus.hw10.DBService;
import ru.cwl.otus.hw10.hibernate.DBServiceHibernateImpl;
import ru.cwl.otus.hw10.model.DataSet;

/**
 * Created by tischenko on 31.01.2018 11:32.
 */
public class CascedDBService implements DBService {
    DBService dbService = new DBServiceHibernateImpl();
    CacheEngine<Key, DataSet> cacheEngine = new CacheEngine<>();

    @Override
    public <T extends DataSet> void save(T entity) {
        dbService.save(entity);
        Key key = new Key(entity.getId(), entity.getClass());
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
            Key key = new Key(entity.getId(), entity.getClass());
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

class Key {
    Long id;
    Class clazz;

    public Key(Long id, Class clazz) {
        this.id = id;
        this.clazz = clazz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key = (Key) o;
        return Objects.equals(id, key.id) &&
                Objects.equals(clazz, key.clazz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clazz);
    }
}
