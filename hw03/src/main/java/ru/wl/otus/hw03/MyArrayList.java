package ru.wl.otus.hw03;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by vadim.tishenko
 * on 28.10.2017 15:24.
 */
public class MyArrayList<T> implements List<T> {


    int size = 0;
    Object[] elements = new Object[100];

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size==0;
    }

    public boolean contains(Object o) {
        throw new NotImplementedException();
    }

    public Iterator<T> iterator() {
        Iterator<T> iterator= new Iterator<T>() {
            int current=0;
            @Override
            public boolean hasNext() {
                return current<size;
            }

            @Override
            public T next() {
                return (T) elements[current++];
            }
        };
        return iterator;
    }

    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    public <T1> T1[] toArray(T1[] a) {
        throw new NotImplementedException();
    }

    public boolean add(T t) {
        elements[size]=t;
        size++;
        return true;
    }

    public boolean remove(Object o) {
        size--;
        return true;
    }

    public boolean containsAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    public boolean addAll(Collection<? extends T> c) {
        c.forEach(cc -> add(cc));
        return true;
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        throw new NotImplementedException();
    }

    public boolean removeAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    public boolean retainAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    public void clear() {
        throw new NotImplementedException();
    }

    public T get(int index) {
        throw new NotImplementedException();
    }

    public T set(int index, T element) {
        T result= (T) elements[index];
        elements[index]=element;
        return result;
    }

    public void add(int index, T element) {
        throw new NotImplementedException();
    }

    public T remove(int index) {
        throw new NotImplementedException();
    }

    public int indexOf(Object o) {
        throw new NotImplementedException();
    }

    public int lastIndexOf(Object o) {
        throw new NotImplementedException();
    }

    public ListIterator<T> listIterator() {
        throw new NotImplementedException();
    }

    public ListIterator<T> listIterator(int index) {
        throw new NotImplementedException();
    }

    public List<T> subList(int fromIndex, int toIndex) {
        throw new NotImplementedException();
    }
}
