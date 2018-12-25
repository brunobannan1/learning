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

    public static void testMethod() throws Exception {
        MyORM myORM = new MyORM(connection);
        int start = myORM.getLastPrimaryKey(Person.class);
        if (start == -1) start = +2;
        Random rndm = new Random();
        for (int i = start; i < 50 + start; i++) {
            String generatedString = "generated string number: " + i;
            int rnd = rndm.nextInt(100);
            Person person = new Person(i, generatedString, rnd, rnd + rndm.nextInt(), false, generatedString);
            dbServiceCached.save(person);
            Thread.sleep(250);
        }
        for (int i = start; i < 50 + start; i++) {
            System.out.println(dbServiceCached.read(Person.class, "where id = \'" + (i) + "\'"));
            Thread.sleep(250);
        }

        for (int i = start; i < 100 + start; i++) {
            String generatedString = "generated string number: " + i;
            int rnd = rndm.nextInt(10);
            Person person = new Person(i, generatedString, rnd, rnd + rndm.nextInt(), false, generatedString);
            dbServiceCached.save(person);
            Thread.sleep(250);
        }
    }
}