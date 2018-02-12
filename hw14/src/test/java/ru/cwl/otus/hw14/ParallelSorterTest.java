package ru.cwl.otus.hw14;

import java.util.Arrays;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by tischenko on 12.02.2018 15:44.
 */
public class ParallelSorterTest {
    final int SIZE = 1000000;
    private ParallelSorter parallelSorter;
    private int[] unsortedArray = new int[SIZE];

    @Before
    public void setUp() {
        fillArray(unsortedArray);
        parallelSorter = new ParallelSorter();
    }

    @Test
    public void sortTest() {
        int[] result = parallelSorter.sort(unsortedArray);
        assertTrue(isSorted(result));
        Arrays.sort(unsortedArray);
        assertTrue(Arrays.equals(result, unsortedArray));
    }

    @Test
    public void sortTest10Threads() {
        int[] result = parallelSorter.sort(unsortedArray,10);
        assertTrue(isSorted(result));
        Arrays.sort(unsortedArray);
        assertTrue(Arrays.equals(result, unsortedArray));
    }

    private void fillArray(int[] array) {
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt();
        }
    }

    private boolean isSorted(int[] array) {
        Integer a = array[0];
        for (int i = 1; i < array.length; i++) {
            if (a > array[i]) return false;
            a = array[i];
        }
        return true;
    }


}