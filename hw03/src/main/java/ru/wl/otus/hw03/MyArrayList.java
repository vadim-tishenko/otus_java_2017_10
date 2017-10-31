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
        size=0;
    }

    public T get(int index) {
        if(index>=0 && index<size){
            return (T) elements[index];
        }
        throw new IndexOutOfBoundsException();
    }

    public T set(int index, T element) {
        if(index>=0 && index<size){
            T result= (T) elements[index];
            elements[index]=element;
            return result;
        }
        throw new IndexOutOfBoundsException();
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
        ListIterator<T> it=new MyArrayListIterator<T>((T[]) elements,size);
        return it;
    }

    public ListIterator<T> listIterator(int index) {
        throw new NotImplementedException();
    }

    public List<T> subList(int fromIndex, int toIndex) {
        throw new NotImplementedException();
    }

    public String toString() {
        Iterator<T> it = iterator();
        if (! it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (;;) {
            T e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (! it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof List))
            return false;

        ListIterator<T> e1 = listIterator();
        ListIterator<?> e2 = ((List<?>) o).listIterator();
        while (e1.hasNext() && e2.hasNext()) {
            T o1 = e1.next();
            Object o2 = e2.next();
            if (!(o1==null ? o2==null : o1.equals(o2)))
                return false;
        }
        return !(e1.hasNext() || e2.hasNext());
    }
}
