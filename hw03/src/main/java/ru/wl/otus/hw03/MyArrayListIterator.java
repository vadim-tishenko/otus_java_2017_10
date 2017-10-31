package ru.wl.otus.hw03;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ListIterator;

/**
 * Created by vadim.tishenko
 * on 31.10.2017 21:09.
 */
public class MyArrayListIterator<T> implements ListIterator<T> {
    T[] elements;
    int current = 0;
    int size;

    private MyArrayListIterator(){}

    public MyArrayListIterator(T[] elements,int size) {
        this.elements = elements;
        this.size = size;
        current=0;
    }

    @Override
    public boolean hasNext() {
        return current < size;
    }

    @Override
    public T next() {
        return elements[current++];
    }

    @Override
    public boolean hasPrevious() {
        return current>0;
    }

    @Override
    public T previous() {
        return elements[--current];
    }

    @Override
    public int nextIndex() {
        return current;
    }

    @Override
    public int previousIndex() {
        return current-1;
    }

    @Override
    public void remove() {
        throw new NotImplementedException();
    }

    @Override
    public void set(T t) {
        elements[current-1]=t;
    }

    @Override
    public void add(T t) {
        throw new NotImplementedException();
    }
}
