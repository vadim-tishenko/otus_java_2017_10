package ru.cwl.otus.hw10;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.cwl.otus.hw10.model.DataSet;

import java.util.List;

/**
 * Created by vadim.tishenko
 * on 04.01.2018 17:32.
 */
public class DBServiceHibernateImpl implements DBService {
    protected SessionFactory sf;
    public DBServiceHibernateImpl() {
        sf = SF.getSessionFactory();
    }

    @Override
    public <T extends DataSet> void save(T user) {
        Session s = sf.openSession();
        s.save(user);
        s.close();
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        Session s = sf.openSession();
        T t = s.load(clazz, id);
        s.close();
        return t;
    }

    @Override
    public <T extends DataSet> List<T> load(Class<T> clazz) {
        Session s = sf.openSession();

        List<T> result = null;//s. ;
        s.close();
        return result;
    }

    @Override
    public void close() {
        sf.close();
    }
}
