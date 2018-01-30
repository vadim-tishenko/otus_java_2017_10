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
    public void testEmptyCache() {
        String res = cache.get("key");
        assertThat(res, equalTo(null));
        assertThat(cache.getHitCount(), is(0L));
        assertThat(cache.getMisCount(), is(1L));
    }

    @Test
    public void testEvictOnSizeOverflow() {
        cache.put("A", "aa");
        cache.put("B", "bb");
        cache.put("C", "cc");
        cache.put("D", "dd");
        cache.put("E", "ee");
        assertThat(cache.size(), is(5));
        cache.put("F", "ff");
        assertThat(cache.size(), is(5));
    }

    @Test
    public void testEvictAfterIdleTimeExpired() throws InterruptedException {
        cache = new CacheEngine<>(5, 0, 100, false);
        cache.put("A", "aa");
        String res = cache.get("A");
        assertThat(res, is("aa"));
        for (int i = 0; i < 20; i++) {
            Thread.sleep(40);
            assertThat(cache.get("A"), is("aa"));
        }
        assertThat(cache.getHitCount(), is(21l));
        //expired....
        Thread.sleep(220);
        assertThat(cache.get("A"), equalTo(null));
    }

    @Test
    public void testEvictAfterLifeTimeExpired() throws InterruptedException {
        cache = new CacheEngine<>(5, 100, 0, false);
        cache.put("A", "aa");
        assertThat(cache.get("A"), is("aa"));
        Thread.sleep(40);
        assertThat(cache.get("A"), is("aa"));
        Thread.sleep(40);
        assertThat(cache.get("A"), is("aa"));
        Thread.sleep(40);
        assertThat(cache.get("A"), equalTo(null));

        assertThat(cache.getHitCount(), is(3L));
        assertThat(cache.getMisCount(), is(1L));
    }


}