package org.bruno.mySimpleORM;

import org.bruno.mySimpleORM.utility.ConnectionInitializator;
import org.bruno.mySimpleORM.utility.Executor;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;

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
        char[] lastMarks = {'A','B','C'};
        Person person = new Person(1,"Meksikawka", 100,50,false, lastMarks);
        ORM.saveObjectToDB(person);
        Connection connection = ConnectionInitializator.getConnection();
        Executor executor = new Executor(connection);
        //String del = "delete from public.\"Person\"";executor.executeUpdate(del);
    }

    @Test
    public void houseSaveTest() {
        String[] ulitsi = {"Pervomayskaya","Piterskaya","Lenina"};
        int[] neplatyat = {1,2,9,23,43};
        House house = new House(100,2,"Nikolay",ulitsi,"Nijegorodskaya",neplatyat);
        ORM.saveObjectToDB(house);
        Connection connection = ConnectionInitializator.getConnection();
        Executor executor = new Executor(connection);
        //String del = "delete from public.\"House\"";executor.executeUpdate(del);
    }

    @Test
    public void canRestoreObjectFromDB() {
        String condition = "where id = \'38\'";
        Person person = (Person) ORM.createObjectFromDB(Person.class, condition);
    }
}