package ru.cwl.otus.hw11;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by vadim.tishenko
 * on 29.01.2018 23:03.
 */
public class CacheEngineTest {
    CacheEngine<String, String> cache;

    @Before
    public void setUp() {
        cache = new CacheEngine<>();
    }

    @After
    public void tearDown() {
        cache.dispose();
    }

    @Test
    public void get() {
        String res = cache.get("key");
        assertThat(res, equalTo(null));
        assertThat(cache.getHitCount(), is(0L));
        assertThat(cache.getMisCount(), is(1L));
    }

    @Test
    public void testEvictOnSize() {
        cache.put("A","aa");
        cache.put("B","bb");
        cache.put("C","cc");
        cache.put("D","dd");
        cache.put("E","ee");
        assertThat(cache.size(),is(5));
        cache.put("F","ff");
        assertThat(cache.size(),is(5));
    }

    @Test
    public void testEvictOnMaxLifeTime() throws InterruptedException {
        cache=new CacheEngine<>(5,100,0,false);
        cache.put("A","aa");
        String res = cache.get("A");
        assertThat(res,is("aa"));
        Thread.sleep(105);
        String res2=cache.get("A");
        assertThat(res2,equalTo(null));

    }

    @Test
    public void testEvictOnIdleTime() throws InterruptedException {
        cache=new CacheEngine<>(5,0,100,false);
        cache.put("A","aa");
        String res = cache.get("A");
        assertThat(res,is("aa"));
        for(int i=0;i<10;i++) {
            Thread.sleep(50);
            System.out.println(i);
            assertThat(cache.get("A"),is("aa"));
        }
        assertThat(cache.get("A"),is("aa"));
        System.out.println("hc:"+cache.getHitCount());
        Thread.sleep(250);
        assertThat(cache.get("A"),equalTo(null));


    }

    //@Test
    public void getHitCount() {
    }

    //@Test
    public void getMisCount() {
    }

    //@Test
    public void dispoze() {
    }
}