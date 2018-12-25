package org.bruno.mySimpleORMinWAR.services.cache;

import org.bruno.mySimpleORMinWAR.utility.Tuple;

import java.lang.ref.SoftReference;
import java.util.Map;

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

    Tuple<Class, Number> getKeyForSave(K object);

    Tuple<Class, Number> getKeyForLoad(Class clazz, String condition);

    public Map<K, SoftReference<Item<K, V>>> getItems();
}