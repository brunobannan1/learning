package org.bruno.mySimpleORM;

import org.bruno.mySimpleORM.entities.*;
import org.bruno.mySimpleORM.executors.Executor;
import org.bruno.mySimpleORM.interfaces.DBService;
import org.bruno.mySimpleORM.services.HibernateDBServiceImpl;
import org.bruno.mySimpleORM.services.MyOrmDBServiceImpl;
import org.bruno.mySimpleORM.utility.ConnectionInitializator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class TestORM {
    Connection connection = ConnectionInitializator.getConnection();
    DBService dbService = new MyOrmDBServiceImpl(connection);
    DBService hibService = new HibernateDBServiceImpl();

    @Before
    public void initialize() {
    }

    @Test
    public void testQuery() {
        Connection connection = ConnectionInitializator.getConnection();
        Executor executor = new Executor(connection);
        String query = "select * from users";
        try {
            executor.executeQuery(query,
                    resultSet -> {
                        while (true) {
                            resultSet.next();
                            System.out.println(
                                    "Id: " + resultSet.getInt(1) +
                                            " name: " + resultSet.getString(2) +
                                            " age: " + resultSet.getInt(3)
                            );
                            if (resultSet.isLast())
                                break;
                        }
                        return resultSet;
                    });
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void testUpdateStringGeneration() {
        String lastMarks = "A,B,C,D,E,F";
        Person person = new Person(1, "Meksikawka", 100, 50, false, lastMarks);
        dbService.save(person);
        Connection connection = ConnectionInitializator.getConnection();
        Executor executor = new Executor(connection);
        //String del = "delete from public.\"Person\"";executor.executeUpdate(del);
    }

    @Test
    public void houseSaveTest() {
        String[] ulitsi = {"Pervomayskaya", "Piterskaya", "Lenina"};
        long[] neplatyat = {1, 2, 9, 23, 43};
        House house = new House(100, 2, "Nikolay", ulitsi, "Nijegorodskaya", neplatyat);
        dbService.save(house);
        Connection connection = ConnectionInitializator.getConnection();
        Executor executor = new Executor(connection);
        //String del = "delete from public.\"House\"";executor.executeUpdate(del);
    }

    @Test
    public void canRestoreObjectFromDB() {
        Connection connection = ConnectionInitializator.getConnection();
        Executor executor = new Executor(connection);
        int id = executor.executeQuery("select id from public.\"Person\" ORDER by id DESC LIMIT 1", resultSet -> {
            resultSet.next();
            return resultSet.getInt(1);
        });
        String condition = "where id = \'" + (id + 1) + "\'";

        String lastMarks = "A,A,A,B,A,A,A,A,A,B,A";
        Random rndm = new Random();
        int weight = rndm.nextInt(150);
        int age = rndm.nextInt(55);
        boolean can = rndm.nextBoolean();
        String string = String.valueOf(rndm.nextInt());

        Person person1 = new Person(id + 1, string, weight, age, can, string);

        dbService.save(person1);

        Person person = (Person) dbService.read(Person.class, condition);

        Assert.assertEquals(person1, person);
        System.out.println(person);

        dbService.shutdown();
    }

    @Test
    public void getAllTest() {
        List<Object> persons = dbService.readAll(Person.class);
        System.out.println(persons.toString());
    }

    @Test
    public void hibernateTest() {
        String status = hibService.getLocalStatus();
        System.out.println("Status: " + status);
        Phone phone1 = new Phone("8800333984");
        Phone phone2 = new Phone("1800333984");
        Phone phone3 = new Phone("2800333984");
        Phone[] array = new Phone[2];
        array[0] = phone2;
        array[1] = phone3;
        Phone[] array1 = {phone1};
        Address ad1 = new Address("Petropavlovskaya");
        Address ad2 = new Address("Krepostnova");
        hibService.save(ad1);
        hibService.save(ad2);
        hibService.save(phone1);
        hibService.save(phone2);
        hibService.save(phone3);
        ItMan katya = new ItMan("Katya", 140, ad1, phone1);
        ItMan kostya = new ItMan("Kostya", 120, ad2, phone2);
        hibService.save(katya);
        hibService.save(kostya);
//        ItMan temp = (ItMan) hibService.read(ItMan.class, "1");
        hibService.shutdown();
    }

}