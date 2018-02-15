package ru.cwl.otus.hw14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tischenko on 12.02.2018 11:58.
 */
public class ParallelSorter {
    int[] sort(int[] array) {
        return sort(array, 4);
    }

    int[] sort(int[] array, int threadsCount) {
        int[][] la = splitArray(array, threadsCount);

        List<Thread> threads = new ArrayList<>();
        for (int[] ts : la) {
            final Thread thread = new Thread(() -> Arrays.sort(ts));
            thread.start();
            threads.add(thread);
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int[] result = joinArrays(la, array.length);

        return result;
    }

    int[] sort2(int[] array, int threadsCount) {
        int[][] la = splitArray(array, threadsCount);
        List<Callable<Object>> callables = new ArrayList<>();

        for (int[] ts : la) {
            callables.add(Executors.callable(()->Arrays.sort(ts)));
        }
        ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);
        try {
            executorService.invokeAll(callables);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int[] result = joinArrays(la, array.length);
        return result;
    }

    int[][] splitArray(int[] array, int n) {
        int rangeSize = array.length / n;
        int[][] result = new int[n][];
        for (int i = 0; i < n; i++) {
            int from = i * rangeSize;
            int to = (i + 1) * rangeSize;
            if (i == n - 1) to = array.length;
            int[] range = Arrays.copyOfRange(array, from, to);
            result[i] = range;
        }
        return result;
    }

    int[] joinArrays(int[][] arrays, int length) {
        int[] dstArray = new int[length];

        int index[] = new int[arrays.length];

        for (int k = 0; k < length; k++) {
            int m0 = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int i = 0; i < arrays.length; i++) {
                if (index[i] == -1) continue;
                int min = arrays[i][index[i]];
                if (min < m0) {
                    m0 = min;
                    minIndex = i;
                }
            }
            dstArray[k] = arrays[minIndex][index[minIndex]];
            index[minIndex]++;
            if (index[minIndex] == arrays[minIndex].length) {
                index[minIndex] = -1;
            }
        }
        return dstArray;
    }
}
