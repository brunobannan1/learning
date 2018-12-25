package org.bruno.mySimpleORMinWAR.interfaces;

import java.util.List;

public interface DBService {

    String getLocalStatus();

    void save(Object object);

    Object read(Class clazz, String condition);

    List<Object> readAll(Class clazz);

    void shutdown();

}
