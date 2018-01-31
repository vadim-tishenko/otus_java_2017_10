package ru.cwl.otus.hw11;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.cwl.otus.hw10.DBService;
import ru.cwl.otus.hw10.hibernate.DBServiceHibernateImpl;
import ru.cwl.otus.hw10.model.AddressDataSet;
import ru.cwl.otus.hw10.model.DataSet;
import ru.cwl.otus.hw10.model.PhoneDataSet;
import ru.cwl.otus.hw10.model.UserDataSet;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CachedDBServiceTest {

    private CacheEngine<Key, DataSet> cacheEngine;
    private DBServiceHibernateImpl dbService;
    private DBService cachedDbService;
    private UserDataSet user1;

    @Before
    public void setUp() {
        cacheEngine = new CacheEngine<>();
        dbService = new DBServiceHibernateImpl();
        cachedDbService = new CachedDBService(dbService, cacheEngine);

        user1 = new UserDataSet("Name 1", 11);
        user1.setAddress(new AddressDataSet("kovalevskaya", 10));

        user1.addPhone(new PhoneDataSet("12345-67890"));
        user1.addPhone(new PhoneDataSet("22345-67890"));
        user1.addPhone(new PhoneDataSet("33335-67890"));

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void readFromCacheTest() {

        dbService.save(user1);

        assertThat(cacheEngine.getMisCount(),is(0L));
        assertThat(cacheEngine.getHitCount(),is(0L));

        UserDataSet u1 = cachedDbService.load(1, UserDataSet.class);

        assertThat(cacheEngine.getMisCount(),is(1L));
        assertThat(cacheEngine.getHitCount(),is(0L));

        UserDataSet u1_1 = cachedDbService.load(1, UserDataSet.class);
        UserDataSet u1_2 = cachedDbService.load(1, UserDataSet.class);
        UserDataSet u1_3 = cachedDbService.load(1, UserDataSet.class);

        assertThat(cacheEngine.getHitCount(),is(3L));
    }

    @Test
    public void writeAndReadFromCacheTest() {

        cachedDbService.save(user1);

        assertThat(cacheEngine.getMisCount(),is(0L));
        assertThat(cacheEngine.getHitCount(),is(0L));

        UserDataSet u1 = cachedDbService.load(1, UserDataSet.class);

        assertThat(cacheEngine.getMisCount(),is(0L));
        assertThat(cacheEngine.getHitCount(),is(1L));

        UserDataSet u1_1 = cachedDbService.load(1, UserDataSet.class);
        UserDataSet u1_2 = cachedDbService.load(1, UserDataSet.class);
        UserDataSet u1_3 = cachedDbService.load(1, UserDataSet.class);

        assertThat(cacheEngine.getHitCount(),is(4L));
    }
}