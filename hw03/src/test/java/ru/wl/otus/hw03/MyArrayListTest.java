package ru.wl.otus.hw03;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by vadim.tishenko
 * on 28.10.2017 21:57.
 */
public class MyArrayListTest {

    @Test
    public void size() throws Exception {
        List<String> actual = Arrays.asList("a", "b", "c");
        MyArrayList<String> l = new MyArrayList<String>();
        assertThat(l,hasSize(0));
        l.addAll(actual);
        assertThat(l,hasSize(3));
        l.remove("a");
        assertThat(l,hasSize(2));
    }

    @Test
    public void testContainsNull() {
        List<String> list = new MyArrayList<String>();
        list.add(null);
        assertTrue(list.contains(null));
    }

    @Test
    public void testRemoveNull() {
        List<String> list = new MyArrayList<String>();
        list.add(null);
        list.remove(null);
        assertTrue(list.isEmpty());
    }

    @Test
    public void testIndexOfNull() {
        List<String> list = new MyArrayList<String>();
        list.add(null);
        assertEquals(0, list.indexOf(null));
    }

    @Test
    public void testLastIndexOfNull() {
        List<String> list = new MyArrayList<String>();
        list.add(null);
        list.add(null);
        assertEquals(1, list.lastIndexOf(null));
    }
}