package org.bruno.mySimpleORM.executors;

import org.bruno.mySimpleORM.interfaces.ResultHandler;

import java.sql.Connection;
import java.sql.ResultSet;
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

    public <T> T executeQuery(String query, ResultHandler<T> resultHandler) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
            ResultSet resultSet = statement.getResultSet();
            T result = resultHandler.handle(resultSet);
            resultSet.close();
            statement.close();

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}