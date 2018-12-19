package org.bruno.mySimpleORM.services;

import org.bruno.mySimpleORM.core.MyORM;
import org.bruno.mySimpleORM.interfaces.DBService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public final class MyOrmDBServiceImpl implements DBService {

    private Connection connection;
    private MyORM myORM;

    public MyOrmDBServiceImpl(Connection connection) {
        this.connection = connection;
        myORM = new MyORM(connection);
    }

    @Override
    public String getLocalStatus() {
        return null;
    }

    @Override
    public void save(Object object) {
        myORM.save(object);
    }

    @Override
    public Object read(Class clazz, String condition) {
        return myORM.read(clazz, condition);
    }

    @Override
    public List<Object> readAll(Class clazz) {
        return myORM.getAll(clazz);
    }

    @Override
    public void shutdown() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}