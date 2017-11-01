package ru.wl.otus.hw03;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
        return size == 0;
    }

    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    public Iterator<T> iterator() {
        Iterator<T> iterator = new Iterator<T>() {
            int current = 0;

            @Override
            public boolean hasNext() {
                return current < size;
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
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2);
        }
        elements[size] = t;
        size++;
        return true;
    }

    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) return false;
        }
        return true;
    }

    public boolean addAll(Collection<? extends T> c) {
        c.forEach(cc -> add(cc));
        return true;
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection<?> c) {
        boolean result = false;
        for (Object o : c) {
            result |= remove(o);
        }
        return result;
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        size = 0;
    }

    public T get(int index) {
        if (index >= 0 && index < size) {
            return (T) elements[index];
        }
        throw new IndexOutOfBoundsException();
    }

    public T set(int index, T element) {
        if (index >= 0 && index < size) {
            T result = (T) elements[index];
            elements[index] = element;
            return result;
        }
        throw new IndexOutOfBoundsException();
    }

    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    public T remove(int index) {
        T result = get(index);
        System.arraycopy(elements, index + 1, elements, index, size - index-1);
        size--;
        return result;
    }

    public int indexOf(Object o) {
        Objects.equals(o,o);
        for (int index = 0; index < size; index++) {
            if (Objects.equals(o,elements[index])) return index;
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        for (int index = size - 1; index >= 0; index--) {
            if (Objects.equals(o,elements[index])) return index;
        }
        return -1;
    }

    public ListIterator<T> listIterator() {
        ListIterator<T> it = new MyArrayListIterator<T>((T[]) elements, size);
        return it;
    }

    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
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
            if (!(o1 == null ? o2 == null : o1.equals(o2)))
                return false;
        }
        return !(e1.hasNext() || e2.hasNext());
    }
}
