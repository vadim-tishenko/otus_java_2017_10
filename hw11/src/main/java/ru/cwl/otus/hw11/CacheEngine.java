package ru.cwl.otus.hw11;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

/**
 * Created by vadim.tishenko
 * on 24.01.2018 21:42.
 */
public class CacheEngine<K,V> {
    private int maxSize=10;

    private long maxLifeTime;
    private long maxIdleTime;

    private long hitCount=0;
    private long misCount=0;

    private LinkedHashMap<K,Entry<K,V>> map=new LinkedHashMap<>();

    public V get(K key){
        Entry<K, V> entry = map.get(key);
        if(entry==null){
            misCount++;
            return null;
        }
        V value=entry.get();
        if(value!=null){
            hitCount++;
            entry.lastAccessTime=System.currentTimeMillis();
        }else {
            misCount++;
            map.remove(key);
        }
        return value;
    }
    void put(K key,V value){
        Entry<K,V> entry = new Entry<>(key, value);
        map.put(key,entry);
    }
    int size(){
        return map.size();
    }

    long getHitCount(){
        return hitCount;
    }
    long getMisCount(){
        return misCount;
    }
    void dispoze(){

    }

    static class Entry<K,V>{
        K key;
        SoftReference<V> ref;
        final long insTime=System.currentTimeMillis();
        long lastAccessTime;

        Entry(K key, V value) {
            this.key=key;
            this.ref=new SoftReference<>(value);
        }

        V get(){
            return ref.get();
        }
    }

}
