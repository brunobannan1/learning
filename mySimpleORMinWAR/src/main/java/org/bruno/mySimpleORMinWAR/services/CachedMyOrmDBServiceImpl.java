package org.bruno.mySimpleORMinWAR.services;

import org.bruno.mySimpleORMinWAR.interfaces.DBService;
import org.bruno.mySimpleORMinWAR.services.cache.CacheServiceImpl;
import org.bruno.mySimpleORMinWAR.services.cache.Item;
import org.bruno.mySimpleORMinWAR.utility.Tuple;

import java.lang.ref.SoftReference;
import java.sql.Connection;
import java.util.List;

public class CachedMyOrmDBServiceImpl implements DBService {
    private Connection connection;

    private MyOrmDBServiceImpl service;
    private CacheServiceImpl cache = new CacheServiceImpl(25, 0, 0, true);

    public CachedMyOrmDBServiceImpl(Connection connection) {
        this.connection = connection;
        this.service = new MyOrmDBServiceImpl(connection);
    }

    @Override
    public String getLocalStatus() {
        return service.getLocalStatus();
    }

    @Override
    public void save(Object object) {
        Tuple key = cache.getKeyForSave(object);
        SoftReference sr = (SoftReference) cache.getItems().get(key);
        if (sr != null) {
            Item item = (Item) sr.get();
            Object o = item.getValue();

            if (o != null) {
                System.out.println("----------Updating object----------");
                System.out.println("| before " + o + " |");
                System.out.println("| now " + object + " |");
                // SHOULD IMPLEMENT UPDATE QUERY =(
                //service.update(object);
            }
        }
        cache.put(new Item(key, object));
        System.out.println("| object " + object + " is (adding / updating) to DB|");
        service.save(object);
    }

    @Override
    public Object read(Class clazz, String condition) {
        Tuple key = cache.getKeyForLoad(clazz, condition);
        Item item = cache.get(key);
        if (item != null) {
            System.out.println("----------Reading from cache----------");
            Object object = item.getValue();
            if (object != null) {
                return object;
            }
        }
        return service.read(clazz, condition);
    }

    public CacheServiceImpl getCache() {
        return cache;
    }

    @Override
    public List<Object> readAll(Class clazz) {
        return service.readAll(clazz);
    }

    @Override
    public void shutdown() {
        service.shutdown();
    }
}