package org.bruno.mySimpleORMinWAR.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionInitializator {

    private ConnectionInitializator() {

    }

    public static Connection getConnection() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            String url = "jdbc:postgresql:myDB";
            return DriverManager.getConnection(url, "postgres", "argon");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}