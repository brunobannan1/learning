package org.bruno.mySimpleORM.core;

import org.bruno.mySimpleORM.executors.Executor;
import org.bruno.mySimpleORM.interfaces.AutoIncrement;
import org.bruno.mySimpleORM.utility.ReflectionHelper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class MyORM {
    private final Connection connection;

    public MyORM(Connection connection) {
        this.connection = connection;
    }

    public void save(Object o) {
        try {
            Executor executor = new Executor(connection);
            Map<Field, Object> map = ReflectionHelper.getAllObjectFields(o);
            String query = generateUpdateString(map, o);
            executor.executeUpdate(query);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public int getLastPrimaryKey(Class clazz) {
        Executor executor = new Executor(connection);
        String tableName = "public.\"" + clazz.getName().substring(clazz.getName().lastIndexOf('.') + 1) + "\"";
        int id = executor.executeQuery("select id from " + tableName + " ORDER by id DESC LIMIT 1", resultSet -> {
            resultSet.next();
            return resultSet.getInt(1);
        });
        return id;
    }

    private String generateUpdateString(Map<Field, Object> map, Object o) {
        String tableName = "public.\"" + o.getClass().getName().substring(o.getClass().getName().lastIndexOf('.') + 1) + "\"";
        StringBuilder names = new StringBuilder("insert into " + tableName + " (");
        StringBuilder questionMarks = new StringBuilder("(");
        for (Map.Entry<Field, Object> entry : map.entrySet()) {
            if (entry.getKey().isAnnotationPresent(AutoIncrement.class)) continue;
            names.append("\"" + entry.getKey().getName() + "\",");
            if (entry.getValue().getClass().isArray()) {
                int length = Array.getLength(entry.getValue());
                StringBuilder sb = new StringBuilder("'{");
                for (int i = 0; i < length; i++) {
                    sb.append(Array.get(entry.getValue(), i).toString() + ",");

                }
                questionMarks.append(sb.deleteCharAt(sb.length() - 1).append("}'").toString() + ",");
            } else
                questionMarks.append("\'" + entry.getValue() + "\',");
        }
        names.deleteCharAt(names.length() - 1);
        questionMarks.deleteCharAt(questionMarks.length() - 1);

        names.append(")");
        questionMarks.append(")");

        return names.append(" values ").append(questionMarks).toString();
    }

    public Object read(Class clazz, String condition) {
        Executor executor = new Executor(connection);
        try {
            Field[] listFields = ReflectionHelper.getAllClassFields(clazz);
            Object object = clazz.getConstructor().newInstance();
            for (Field field : listFields) {
                String query = "select " + field.getName() + " from public.\"" + clazz.getName().substring(clazz.getName().lastIndexOf('.') + 1) + "\" " + condition;
                executor.executeQuery(query, resultSet -> {
                    String name = field.getName();
                    resultSet.next();
                    try {
                        Field fieldToExchange = object.getClass().getDeclaredField(name);
                        fieldToExchange.setAccessible(true);
                        fieldToExchange.set(object, resultSet.getObject(name));
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    return true;
                });
            }
            return object;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object> getAll(Class o) {
        List<Object> objects = new ArrayList<>();
        String tableName = "public.\"" + o.getName().substring(o.getName().lastIndexOf('.') + 1) + "\"";
        Executor executor = new Executor(connection);
        int count = executor.executeQuery("select count (*) from " + tableName, resultSet -> {
            resultSet.next();
            return resultSet.getInt(1);
        });
        if (count > 0) {
            int[] id = new int[count];
            executor.executeQuery("select id from " + tableName, resultSet -> {
                for (int i = 0; i < count; i++) {
                    resultSet.next();
                    id[i] = resultSet.getInt(1);
                }
                return id;
            });

            int startid = Arrays.stream(id).min().getAsInt();
            for (int i : id) {
                objects.add(read(o, " where id = \'" + id[i - startid] + "\'"));
                if (i - startid >= id.length - 1) break;
            }
            return objects;
        } else
            return new ArrayList<>();
    }
}