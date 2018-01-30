package ru.cwl.otus.hw11;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

/**
 * Created by vadim.tishenko
 * on 24.01.2018 21:42.
 */
public class CacheEngine<K, V> {
    private int maxSize = 5;

    Timer timer = new Timer();

    private long maxLifeTimeMs;
    private long maxIdleTimeMs;
    boolean isEternal;

    private long hitCount = 0;
    private long misCount = 0;

    private LinkedHashMap<K, Entry<K, V>> map = new LinkedHashMap<>();

    public CacheEngine() {
        this(5, 0, 0, true);
    }

    public CacheEngine(int maxSize, long maxLifeTimeMs, long maxIdleTimeMs, boolean isEternal) {
        this.maxSize = maxSize;
        this.maxLifeTimeMs = maxLifeTimeMs;
        this.maxIdleTimeMs = maxIdleTimeMs;
        this.isEternal = isEternal;
    }


    public V get(K key) {
        Entry<K, V> entry = map.get(key);
        if (entry == null) {
            misCount++;
            return null;
        }
        V value = entry.get();
        if (value != null) {
            hitCount++;
            entry.lastAccessTime = System.currentTimeMillis();
        } else {
            misCount++;
            map.remove(key);
        }
        return value;
    }

    void put(K key, V value) {
        Entry<K, V> entry = new Entry<>(key, value);
        if (size() >= maxSize) {
            // удалить любой.
            Set<K> keys = map.keySet();
            K anyKey = keys.iterator().next();
            map.remove(anyKey);
        }
        map.put(key, entry);

        if (isEternal) return;
        if (maxLifeTimeMs != 0) {
            TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getInsertTime() + maxLifeTimeMs);
            timer.schedule(lifeTimerTask, maxLifeTimeMs);
        }
        if (maxIdleTimeMs != 0) {
            TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + maxIdleTimeMs);
            timer.schedule(idleTimerTask, maxIdleTimeMs);
        }
    }

    int size() {
        return map.size();
    }

    long getHitCount() {
        return hitCount;
    }

    long getMisCount() {
        return misCount;
    }

    void dispose() {
        timer.cancel();
    }

    private TimerTask getTimerTask(final K key, Function<Entry<K, V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                Entry<K, V> element = map.get(key);
                if (element == null || isT1BeforeT2(timeFunction.apply(element), System.currentTimeMillis())) {
                    map.remove(key);
                    this.cancel();
                }
            }
        };
    }

    private boolean isT1BeforeT2(long t1, long t2) {
        final long TIME_THRESHOLD_MS = 10;
        return t1 < t2 + TIME_THRESHOLD_MS;
    }

    static class Entry<K, V> {
        K key;
        SoftReference<V> ref;
        final long insertTime = System.currentTimeMillis();
        long lastAccessTime = insertTime;

        Entry(K key, V value) {
            this.key = key;
            this.ref = new SoftReference<>(value);
        }

        V get() {
            lastAccessTime = System.currentTimeMillis();
            return ref.get();
        }

        long getInsertTime() {
            return insertTime;
        }

        long getLastAccessTime() {
            return lastAccessTime;
        }
    }

}
