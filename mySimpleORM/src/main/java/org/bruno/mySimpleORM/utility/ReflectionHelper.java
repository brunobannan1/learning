package org.bruno.mySimpleORM.utility;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class ReflectionHelper {
    private ReflectionHelper() {
    }

/*    public static boolean isAnnotationExsistOnField(Field field, Class clazz) {
        List<Annotation> list = (Arrays.asList(field.getDeclaredAnnotations()));
        List<Class> listz = list.stream().map(p -> p.getClass()).collect(Collectors.toList());
        if(listz.contains(clazz)) return true;
        return false;
    }*/

    public static <T> T instantiate(Class<T> type, Object... args) {
        try {
            if (args.length == 0) {
                return type.getDeclaredConstructor().newInstance();
            } else {
                Class<?>[] classes = toClasses(args);
                return type.getDeclaredConstructor(classes).newInstance(args);
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Set<Class> WRAPPER_TYPES = new HashSet(Arrays
            .asList(Boolean.class,
                    Character.class,
                    Byte.class,
                    Short.class,
                    Integer.class,
                    Long.class,
                    Float.class,
                    Double.class,
                    Void.class));

    public static boolean isWrapperType(Class clazz) {
        return WRAPPER_TYPES.contains(clazz);
    }

    static ArrayList<Method> getAllAnnotations(Class type, Class annotationClass) {
        ArrayList<Method> list = new ArrayList<>();
        for (Method method : type.getDeclaredMethods()) {
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                if (annotation.annotationType() == annotationClass) {
                    list.add(method);
                }
            }
        }
        return list;
    }

    public static Field[] getAllClassFields (Class clazz){
        Field listFields[] = clazz.getDeclaredFields();
        return listFields;
    }

    public static Map<Field, Object> getAllObjectFields(Object o) throws IllegalAccessException {
        Map<Field, Object> map = new HashMap<>();
        Class clazz = o.getClass();
        Field listFields[] = clazz.getDeclaredFields();
        for (Field field : listFields) {
            field.setAccessible(true);
            map.put(field, field.get(o));
        }
        return map;
    }

    static Object invokeMethod(Object object, String name, Object... args) {
        Method method = null;
        try {
            method = object.getClass().getDeclaredMethod(name, toClasses(args));
            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


    static private Class<?>[] toClasses(Object[] args) {
        List<Class<?>> classes = Arrays.stream(args).map(Object::getClass).collect(Collectors.toList());
        return classes.toArray(new Class<?>[classes.size()]);
    }

    public static CallBack executeCallback(Object o) {
        if (o.getClass() == Integer.class) return (a) -> Integer.getInteger(a);
        if (o.getClass() == Long.class) return (a) -> Long.getLong(a);
        if (o.getClass() == Character.class) return (a) -> a.charAt(0);
        if (o.getClass() == Boolean.class) return (a) -> Boolean.getBoolean(a);
        if (o.getClass() == Array.class) return (a) -> a;

        return null;
    }
}
