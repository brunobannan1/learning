package org.bruno.MyTestFramework;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MyTestFramework {

    public static void run(Class clazz) {
        Object[] args = {};
        Method before = null;
        Method after = null;
        if(!ReflectionHelper.getAllAnnotations(clazz, org.bruno.MyTestFramework.annotations.Before.class).isEmpty()) {
            before = ReflectionHelper.getAllAnnotations(clazz, org.bruno.MyTestFramework.annotations.Before.class).get(0);
        }
        if(!ReflectionHelper.getAllAnnotations(clazz, org.bruno.MyTestFramework.annotations.After.class).isEmpty()) {
            after = ReflectionHelper.getAllAnnotations(clazz, org.bruno.MyTestFramework.annotations.After.class).get(0);
        }
        ArrayList<Method> methods = ReflectionHelper.getAllAnnotations(clazz, org.bruno.MyTestFramework.annotations.Test.class);
        for(Method method : methods) {
            Object o = ReflectionHelper.instantiate(clazz, args);
            if (before != null && after != null) {
                ReflectionHelper.invokeMethod(o, before.getName(), args);
                ReflectionHelper.invokeMethod(o, method.getName(), args);
                ReflectionHelper.invokeMethod(o, after.getName(), args);
            } else if (after == null && before != null) {
                ReflectionHelper.invokeMethod(o, before.getName(), args);
                ReflectionHelper.invokeMethod(o, method.getName(), args);
            } else if (before == null && after != null) {
                ReflectionHelper.invokeMethod(o, method.getName(), args);
                ReflectionHelper.invokeMethod(o, after.getName(), args);
            } else {
                ReflectionHelper.invokeMethod(o, method.getName(), args);
            }
        }
    }

    public static boolean assertEquals(Object expected, Object actual) {
        return expected.equals(actual);
    }
}
