package ru.wl.otus.hw03;

import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;


/**
 * Created by vadim.tishenko
 * on 28.10.2017 22:00.
 */
public class MyArrayTestHL {

    @Test
    public void addAll() {
        List<String> l1 = new MyArrayList<String>();
        Collections.addAll(l1, "Q", "W", "T");
        Collections.addAll(l1, "Q1", "W2", "T3");

        // Ensure Correct order
        assertThat(l1, contains("Q", "W", "T","Q1","W2","T3"));
    }

    @Test
    public void testCopy(){
        List<String> src=new LinkedList<String>();
        Collections.addAll(src, "Q", "W", "T");
        List<String> dst=new MyArrayList<String>();
        Collections.addAll(dst, "Q1", "W1", "T1");

        Collections.copy(dst,src);
        assertThat(dst, contains("Q", "W", "T"));
    }

    @Test
    public void testSort() {
        List<String> l1 = new MyArrayList<String>();
        Collections.addAll(l1, "B","A", "C");
        Collections.sort(l1, Comparator.naturalOrder());
        assertThat(l1, contains("A", "B", "C"));

    }
}
