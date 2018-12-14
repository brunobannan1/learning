package org.bruno.mySimpleORM;

import org.bruno.mySimpleORM.utility.ReflectionHelper;

import java.lang.reflect.Array;
import java.util.Map;

public class ORM {

    public static String saveObjectToDB(Object o) {
        try {
            Map<String, Object> map = ReflectionHelper.getAllFields(o);
            for (Map.Entry<String,Object> entry: map.entrySet()) {

            }
            String query = generateUpdateString(map, o);
            return query;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Object createObjectFromDB(Class clazz, int id) {
        return null;
    }

    private static String generateUpdateString(Map<String, Object> map, Object o) {
        String tableName = "public.\"" + o.getClass().getName().substring(o.getClass().getName().lastIndexOf('.')+1) +"\"";
        StringBuilder names = new StringBuilder("insert into " + tableName + " (");
        StringBuilder questionMarks = new StringBuilder("(");
        for(Map.Entry<String, Object> entry : map.entrySet()) {
            names.append("\"" + entry.getKey()+"\",");
            if(entry.getValue().getClass().isArray()) {
                int length = Array.getLength(entry.getValue());
                StringBuilder sb = new StringBuilder("'{");
                for (int i = 0; i < length; i++) {
                    if(Array.get(entry.getValue(),i).getClass() == Character.class) {
                        sb.append(Array.get(entry.getValue(),i).toString()+",");
                    }
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
