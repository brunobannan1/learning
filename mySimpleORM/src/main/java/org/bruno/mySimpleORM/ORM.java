package org.bruno.mySimpleORM;

import org.bruno.mySimpleORM.utility.AutoIncrement;
import org.bruno.mySimpleORM.utility.ConnectionInitializator;
import org.bruno.mySimpleORM.utility.Executor;
import org.bruno.mySimpleORM.utility.ReflectionHelper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.Map;

public final class ORM {

    private ORM() {
    }

    public static void saveObjectToDB(Object o) {
        try {
            Connection connection = ConnectionInitializator.getConnection();
            Executor executor = new Executor(connection);

            Map<Field, Object> map = ReflectionHelper.getAllObjectFields(o);

            /*List<Field> list = new ArrayList<>();

            for (Map.Entry<Field, Object> entry: map.entrySet()) {
                if(ReflectionHelper.isAnnotationExsistOnField(entry.getKey(), AutoIncrement.class))
                    list.add(entry.getKey());
            }
            map.remove(list);
            */
            String query = generateUpdateString(map, o);
            executor.executeUpdate(query);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static String generateUpdateString(Map<Field, Object> map, Object o) {
        String tableName = "public.\"" + o.getClass().getName().substring(o.getClass().getName().lastIndexOf('.')+1) + "\"";
        StringBuilder names = new StringBuilder("insert into " + tableName + " (");
        StringBuilder questionMarks = new StringBuilder("(");
        for(Map.Entry<Field, Object> entry : map.entrySet()) {
            if(entry.getKey().isAnnotationPresent(AutoIncrement.class)) continue;
            names.append("\"" + entry.getKey().getName()+"\",");
            if(entry.getValue().getClass().isArray()) {
                int length = Array.getLength(entry.getValue());
                StringBuilder sb = new StringBuilder("'{");
                for (int i = 0; i < length; i++) {
                    sb.append(Array.get(entry.getValue(),i).toString()+",");

                }
                questionMarks.append(sb.deleteCharAt(sb.length()-1).append("}'").toString()+",");
            } else
                questionMarks.append("\'"+entry.getValue()+"\',");
        }
        names.deleteCharAt(names.length()-1);
        questionMarks.deleteCharAt(questionMarks.length()-1);

        names.append(")");
        questionMarks.append(")");

        return names.append(" values ").append(questionMarks).toString();
    }

    public static Object createObjectFromDB(Class clazz, String condition) {
        Connection connection = ConnectionInitializator.getConnection();
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
}