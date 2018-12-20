package org.bruno.mySimpleORM.services.cache;

public interface CacheService<K, V> {

    Item<K, V> get(K key);

    void put(Item<K, V> item);

    int getSize();

    int getHitCount();

    int getMissCount();

    void dispose();

    void setMaxSize(int maxSize);

    void setLifeTime(long lifeTimeMs);

    void setIdleTime(long idleTimeMs);

}