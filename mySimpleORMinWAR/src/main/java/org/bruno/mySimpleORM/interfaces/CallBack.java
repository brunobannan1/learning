package org.bruno.mySimpleORM.interfaces;

public interface CallBack<T> {
    T callback(String result);
}