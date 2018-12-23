package org.bruno.mySimpleORM.webserver;

import org.bruno.mySimpleORM.core.MyORM;
import org.bruno.mySimpleORM.entities.Person;
import org.bruno.mySimpleORM.services.CachedMyOrmDBServiceImpl;
import org.bruno.mySimpleORM.services.cache.CacheServiceImpl;
import org.bruno.mySimpleORM.utility.ConnectionInitializator;
import org.bruno.mySimpleORM.webserver.servlets.CacheInfoServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.sql.Connection;
import java.util.Random;

public class Application {
    private static final int PORTNUMBER = 8069;
    private static final String HTML = "public_html";
    private static Server server;
    private static ResourceHandler resourceHandler;
    private static ServletContextHandler context;
    private static Connection connection = ConnectionInitializator.getConnection();
    private static CachedMyOrmDBServiceImpl dbServiceCached = new CachedMyOrmDBServiceImpl(connection);
    private static CacheServiceImpl service = dbServiceCached.getCache();

    public static void main(String[] args) {
        setResourceHandler();
        setServletContextHandler();
        initializeServer();
        startServer();
    }

    private final static void setResourceHandler() {
        resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(HTML);
    }

    private static void setServletContextHandler() {
        context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new CacheInfoServlet(service)), "/cache");
    }

    private final static void initializeServer() {
        server = new Server(PORTNUMBER);
    }

    private static void startServer() {
        try {
            server.setHandler(new HandlerList(resourceHandler, context));
            server.start();
            while (true) {
                testMethod();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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
