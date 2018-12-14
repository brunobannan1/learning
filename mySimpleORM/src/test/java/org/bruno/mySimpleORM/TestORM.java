package org.bruno.mySimpleORM;

import org.bruno.mySimpleORM.utility.ConnectionInitializator;
import org.bruno.mySimpleORM.utility.Executor;
import org.bruno.mySimpleORM.utility.ExecutorImpl;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;

public class TestORM {
    public Connection connection;
    public ExecutorImpl executor;

    @Before
    public void initialize() {
        connection = ConnectionInitializator.getConnection();
        executor = new ExecutorImpl(connection);
    }

    @Test
    public void testQuery() {
        String query = "select * from users";
        try {
            executor.execQuery(query,
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
        String query = ORM.saveObjectToDB(person);
        Connection connection = ConnectionInitializator.getConnection();
        Executor executor = new Executor(connection);
        executor.executeUpdate(query);
        String del = "delete from public.\"Person\"";executor.executeUpdate(del);
    }

    @Test
    public void houseSaveTest() {
        String[] ulitsi = {"Pervomayskaya","Piterskaya","Lenina"};
        int[] neplatyat = {1,2,9,23,43};
        House house = new House(100,2,"Nikolay",ulitsi,"Nijegorodskaya",neplatyat);
        String query = ORM.saveObjectToDB(house);
        System.out.println(query);
        Connection connection = ConnectionInitializator.getConnection();
        Executor executor = new Executor(connection);
        executor.executeUpdate(query);
        //String del = "delete from public.\"House\"";executor.executeUpdate(del);
    }
}