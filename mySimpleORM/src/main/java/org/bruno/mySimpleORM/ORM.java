package org.bruno.mySimpleORM;

import org.bruno.mySimpleORM.utility.AutoIncrement;
import org.bruno.mySimpleORM.utility.ReflectionHelper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Map;

public class ORM {

    public static String saveObjectToDB(Object o) {
        try {
            Map<Field, Object> map = ReflectionHelper.getAllFields(o);

            /*List<Field> list = new ArrayList<>();

            for (Map.Entry<Field, Object> entry: map.entrySet()) {
                if(ReflectionHelper.isAnnotationExsistOnField(entry.getKey(), AutoIncrement.class))
                    list.add(entry.getKey());
            }
            map.remove(list);
            */

            String query = generateUpdateString(map, o);
            return query;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Object createObjectFromDB(Class clazz, int id) {
        return null;
    }

    private static String generateUpdateString(Map<Field, Object> map, Object o) {
        String tableName = "public.\"" + o.getClass().getName().substring(o.getClass().getName().lastIndexOf('.')+1) +"\"";
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

}
