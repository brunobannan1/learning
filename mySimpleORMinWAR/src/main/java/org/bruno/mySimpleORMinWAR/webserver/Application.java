package org.bruno.mySimpleORMinWAR.webserver;

import org.bruno.mySimpleORMinWAR.core.MyORM;
import org.bruno.mySimpleORMinWAR.entities.Person;
import org.bruno.mySimpleORMinWAR.services.CachedMyOrmDBServiceImpl;
import org.bruno.mySimpleORMinWAR.services.cache.CacheServiceImpl;
import org.bruno.mySimpleORMinWAR.utility.ConnectionInitializator;

import java.sql.Connection;
import java.util.Random;

public class Application {

    private static Connection connection = ConnectionInitializator.getConnection();
    private static CachedMyOrmDBServiceImpl dbServiceCached = new CachedMyOrmDBServiceImpl(connection);
    private static CacheServiceImpl service = dbServiceCached.getCache();

    public void testMethod() throws Exception {
        new Thread(() -> {
            while (true) {
                MyORM myORM = new MyORM(connection);
                int start = myORM.getLastPrimaryKey(Person.class);
                if (start == -1) start = +2;
                Random rndm = new Random();
                for (int i = start; i < 50 + start; i++) {
                    String generatedString = "generated string number: " + i;
                    int rnd = rndm.nextInt(100);
                    Person person = new Person(i, generatedString, rnd, rnd + rndm.nextInt(100), false, generatedString);
                    dbServiceCached.save(person);
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = start; i < 50 + start; i++) {
                    System.out.println(dbServiceCached.read(Person.class, "where id = \'" + (i) + "\'"));
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int i = start; i < 100 + start; i++) {
                    String generatedString = "generated string number: " + i;
                    int rnd = rndm.nextInt(10);
                    Person person = new Person(i, generatedString, rnd, rnd + rndm.nextInt(100), false, generatedString);
                    dbServiceCached.save(person);
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName());
            }
        }).start();
    }

    public CacheServiceImpl getService() {
        return service;
    }
}