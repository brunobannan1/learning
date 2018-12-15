package org.bruno.mySimpleORM.utility;

public interface CallBack<T> {
    T callback(String result);
}