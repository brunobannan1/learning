package org.bruno.mySimpleORM.services;

import org.bruno.mySimpleORM.interfaces.DBService;
import org.bruno.mySimpleORM.services.cache.CacheService;
import org.bruno.mySimpleORM.services.cache.CacheServiceImpl;
import org.bruno.mySimpleORM.services.cache.Item;
import org.bruno.mySimpleORM.utility.Tuple;

import java.lang.ref.SoftReference;
import java.sql.Connection;
import java.util.List;

public class CachedMyOrmDBServiceImpl implements DBService {
    private Connection connection;

    private MyOrmDBServiceImpl service;
    private CacheService cache = new CacheServiceImpl(100, 0, 0, true);

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
            }
        }
        cache.put(new Item(key, object));
        // SHOULD IMPLEMENT UPDATE QUERY =(
        /*service.save(object);*/
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

    @Override
    public List<Object> readAll(Class clazz) {
        return service.readAll(clazz);
    }

    @Override
    public void shutdown() {
        service.shutdown();
    }
}