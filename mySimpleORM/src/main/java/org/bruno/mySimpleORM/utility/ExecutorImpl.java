package org.bruno.mySimpleORM.utility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExecutorImpl {
    private final Connection connection;

    public ExecutorImpl(Connection connection) {
        this.connection = connection;
    }

    public <T> T execQuery(String query, ResultHandler<T> resultHandler) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(query);
        ResultSet resultSet = stmt.getResultSet();
        T value = resultHandler.handle(resultSet);
        resultSet.close();
        stmt.close();

        return value;
    }
}
