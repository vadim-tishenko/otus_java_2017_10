package ru.wl.otus.hw03;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;


/**
 * Created by vadim.tishenko
 * on 28.10.2017 22:00.
 */
public class MyArrayTestHL {


    //@Test
    public void testAssertList() {

        List<String> actual = Arrays.asList("a", "b", "c");
        List<String> expected = Arrays.asList("a", "b", "c");

        //All passed / true

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

    }


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
