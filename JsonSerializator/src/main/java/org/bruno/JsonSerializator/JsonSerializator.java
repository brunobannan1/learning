package org.bruno.JsonSerializator;

import javax.json.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class JsonSerializator {

    public static String serialize (Object o) throws IllegalAccessException{
        Map<String, Object> mapFields = ReflectionHelper.getAllFields(o);
        JsonObjectBuilder builder = Json.createObjectBuilder();
        for(Map.Entry<String, Object> entry : mapFields.entrySet()) {
            Object iterateObject = entry.getValue();
            if(iterateObject.getClass().isArray()) {
                int length = Array.getLength(iterateObject);
                JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
                for (int i = 0; i < length; i++) {
                    arrBuilder.add(Array.get(iterateObject,i).toString());
                }
                builder.add(entry.getKey(), arrBuilder);
            }
            else
                builder.add(entry.getKey(), entry.getValue().toString());
        }
        String finalString = builder.build().toString();
        return finalString;
    }

    private JsonSerializator(){
    }
}