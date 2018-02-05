package ru.cwl.otus.hw10.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.cwl.otus.hw10.DBService;
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
        Transaction tx=null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        Session s = sf.openSession();
        T t = s.get(clazz, id);
        s.close();
        return t;
    }

    @Override
    public <T extends DataSet> List<T> load(Class<T> clazz) {
        Session s = sf.openSession();
        Query<T> a = s.createQuery("select o from "+clazz.getSimpleName()+" o", clazz);
        List<T> result = a.getResultList() ;
        s.close();
        return result;
    }

    @Override
    public void close() {
        sf.close();
    }
}
