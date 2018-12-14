package org.bruno.mySimpleORM.utility;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Executor {

    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public int executeUpdate(String query) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
            return statement.getUpdateCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}