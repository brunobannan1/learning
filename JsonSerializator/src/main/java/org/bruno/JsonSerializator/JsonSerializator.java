package org.bruno.JsonSerializator;

import javax.json.*;
import java.lang.reflect.Array;
import java.util.Map;

public class JsonSerializator {

    public static JsonObject serialize (Object o) throws IllegalAccessException{
        Map<String, Object> mapFields = ReflectionHelper.getAllFields(o);
        JsonObjectBuilder builder = Json.createObjectBuilder();
        for(Map.Entry<String, Object> entry : mapFields.entrySet()) {
            Object iterateObject = entry.getValue();
            if(iterateObject == null) {
                builder.add(entry.getKey(),JsonObject.NULL);
            } else
            if(iterateObject.getClass().isArray()) {
                int length = Array.getLength(iterateObject);
                JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
                for (int i = 0; i < length; i++) {
                    if(!(ReflectionHelper.isWrapperType(Array.get(iterateObject,i).getClass()) || Array.get(iterateObject,i).getClass() == String.class)) {
                        arrBuilder.add(serialize(Array.get(iterateObject,i)));
                    } else
                    arrBuilder.add(Array.get(iterateObject,i).toString());
                }
                builder.add(entry.getKey(), arrBuilder);
            }
            else
            if(!(iterateObject.getClass().isArray() || iterateObject.getClass() == String.class || ReflectionHelper.isWrapperType(iterateObject.getClass())) ) {
                builder.add(entry.getKey(),serialize(iterateObject));
            } else
                builder.add(entry.getKey(), entry.getValue().toString());
        }
        JsonObject finalString = builder.build();
        return finalString;
    }

    private JsonSerializator(){
    }
}