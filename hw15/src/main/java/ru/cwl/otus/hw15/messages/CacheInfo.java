package ru.cwl.otus.hw15.messages;

import ru.cwl.otus.hw11.CacheEngine;

/**
 * Created by vadim.tishenko
 * on 09.03.2018 22:16.
 */
public class CacheInfo {
    private final int maxSize;
    private final long maxLifeTimeMs;
    private final long maxIdleTimeMs;
    private final long hit;
    private final long mis;
    private final int size;

    public CacheInfo(CacheEngine ce) {
        maxSize = ce.getMaxSize();
        maxLifeTimeMs = ce.getMaxLifeTimeMs();
        maxIdleTimeMs = ce.getMaxIdleTimeMs();
        hit = ce.getHitCount();
        mis = ce.getMisCount();
        size = ce.size();
    }

    public int getMaxSize() {
        return maxSize;
    }

    public long getMaxLifeTimeMs() {
        return maxLifeTimeMs;
    }

    public long getMaxIdleTimeMs() {
        return maxIdleTimeMs;
    }

    public long getHit() {
        return hit;
    }

    public long getMis() {
        return mis;
    }

    public int getSize() {
        return size;
    }
}
