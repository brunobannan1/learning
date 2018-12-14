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
    }
}