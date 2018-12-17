package org.bruno.mySimpleORM;

import org.bruno.mySimpleORM.utility.ConnectionInitializator;
import org.bruno.mySimpleORM.utility.Executor;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;

public class TestORM {

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
                        while(true) {
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
        Person person = new Person(1,"Meksikawka", 100,50,false, lastMarks);
        ORM.saveObjectToDB(person);
        Connection connection = ConnectionInitializator.getConnection();
        Executor executor = new Executor(connection);
        //String del = "delete from public.\"Person\"";executor.executeUpdate(del);
    }

    @Test
    public void houseSaveTest() {
        String[] ulitsi = {"Pervomayskaya","Piterskaya","Lenina"};
        long[] neplatyat = {1,2,9,23,43};
        House house = new House(100,2,"Nikolay",ulitsi,"Nijegorodskaya",neplatyat);
        ORM.saveObjectToDB(house);
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
        String condition = "where id = \'"+(id+1)+"\'";

        String lastMarks = "A,A,A,B,A,A,A,A,A,B,A";
        Random rndm = new Random();
        int weight = rndm.nextInt(150);
        int age = rndm.nextInt(55);
        boolean can = rndm.nextBoolean();
        String string = String.valueOf(rndm.nextInt());

        Person person1 = new Person(id+1,string, weight,age,can, string);

        ORM.saveObjectToDB(person1);

        Person person = (Person) ORM.createObjectFromDB(Person.class, condition);

        Assert.assertEquals(person1,person);
        System.out.println(person);
    }
}