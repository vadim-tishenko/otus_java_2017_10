package ru.wl.otus.hw03;

import org.junit.Test;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

/**
 * Created by vadim.tishenko
 * on 28.10.2017 21:57.
 */
public class MyArrayListTest {
    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    @Test
    public void size() throws Exception {
        List<String> actual = Arrays.asList("a", "b", "c");
        MyArrayList<String> l = new MyArrayList<String>();
        assertThat(l,hasSize(0));
        l.addAll(actual);
        assertThat(l,hasSize(3));
        l.remove("a");
        assertThat(l,hasSize(2));
        //List<String> expected = Arrays.asList("a", "b", "c");
    }
    /*
       //1. Test equal.
        assertThat(actual, is(expected));

        //2. If List has this value?
        assertThat(actual, hasItems("b"));

        //3. Check List Size
        assertThat(actual, hasSize(3));

        assertThat(actual.size(), is(3));

        //4.  List order

        // Ensure Correct order
        assertThat(actual, contains("a", "b", "c"));

        // Can be any order
        assertThat(actual, containsInAnyOrder("c", "b", "a"));

        //5. check empty list
        assertThat(actual, not(IsEmptyCollection.empty()));

        assertThat(new ArrayList<>(), IsEmptyCollection.empty());
     */

    @Test
    public void isEmpty() throws Exception {
    }

    @Test
    public void contains() throws Exception {
    }

    @Test
    public void iterator() throws Exception {
    }

    @Test
    public void toArray() throws Exception {
    }

    @Test
    public void toArray1() throws Exception {
    }

    @Test
    public void add() throws Exception {
    }

    @Test
    public void remove() throws Exception {
    }

    @Test
    public void containsAll() throws Exception {
    }

    @Test
    public void addAll() throws Exception {
    }

    @Test
    public void addAll1() throws Exception {
    }

    @Test
    public void removeAll() throws Exception {
    }

    @Test
    public void retainAll() throws Exception {
    }

    @Test
    public void clear() throws Exception {
    }

    @Test
    public void get() throws Exception {
    }

    @Test
    public void set() throws Exception {
    }

    @Test
    public void add1() throws Exception {
    }

    @Test
    public void remove1() throws Exception {
    }

    @Test
    public void indexOf() throws Exception {
    }

    @Test
    public void lastIndexOf() throws Exception {
    }

    @Test
    public void listIterator() throws Exception {
    }

    @Test
    public void listIterator1() throws Exception {
    }

    @Test
    public void subList() throws Exception {
    }

}