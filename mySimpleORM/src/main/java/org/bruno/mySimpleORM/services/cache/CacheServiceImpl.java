package org.bruno.mySimpleORM.services.cache;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

public class CacheServiceImpl<K, V> implements CacheService<K, V> {

    private static final int TIME_THRESHOLD_MS = 10;

    private static final int DEFAULT_SIZE = 100;
    private final Map<K, SoftReference<Item<K, V>>> items = new LinkedHashMap<>();
    private final Timer timer = new Timer();
    private final int cacheSize;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private int hitCount = 0;
    private int missCount = 0;

    public CacheServiceImpl(int cacheSize, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        this.cacheSize = cacheSize;
        this.lifeTimeMs = lifeTimeMs;
        this.idleTimeMs = idleTimeMs;
        this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0 || isEternal;
    }

    public CacheServiceImpl() {
        this.cacheSize = DEFAULT_SIZE;
        this.lifeTimeMs = 0;
        this.idleTimeMs = 0;
        this.isEternal = true;
    }

    @Override
    public Item<K, V> get(K key) {
        Item<K, V> item = items.get(key).get();
        if (item == null) {
            missCount++;
        } else {
            hitCount++;
            item.setLastAccessTime();
        }
        return item;
    }

    @Override
    public void put(Item<K, V> item) {
        SoftReference<Item<K, V>> softReference = new SoftReference<>(item);
        K key = item.getKey();
        items.put(key, softReference);

        if (lifeTimeMs != 0) {
            TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
            timer.schedule(lifeTimerTask, lifeTimeMs);
        }
        if (idleTimeMs != 0) {
            TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
            timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
        }
    }

    @Override
    public int getSize() {
        return cacheSize;
    }

    @Override
    public int getHitCount() {
        return hitCount;
    }

    @Override
    public int getMissCount() {
        return missCount;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    @Override
    public void setMaxSize(int maxSize) {
        throw new RuntimeException("Shouldn't use this method for this implementation");
    }

    @Override
    public void setLifeTime(long lifeTimeMs) {
        throw new RuntimeException("Shouldn't use this method for this implementation");
    }

    @Override
    public void setIdleTime(long idleTimeMs) {
        throw new RuntimeException("Shouldn't use this method for this implementation");
    }

    private TimerTask getTimerTask(final K key, Function<Item<K, V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                Item<K, V> item = items.get(key).get();
                if (item == null || isT1BeforeT2(timeFunction.apply(item), System.currentTimeMillis())) {
                    items.remove(key);
                    this.cancel();
                }
            }
        };
    }

    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }
}
