package org.bruno.JsonSerializator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class ReflectionHelper {
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

    private ReflectionHelper() {
    }

    static boolean isAnnotationExsistOnField(Field field, Annotation annotation) {
        List<Annotation> list = Arrays.asList(field.getAnnotations());
        if (list.contains(annotation)) return true;
        return false;
    }

    static <T> T instantiate(Class<T> type, Object... args) {
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

    static Map<Field, Object> getAllFields(Object o) throws IllegalAccessException {
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
}
