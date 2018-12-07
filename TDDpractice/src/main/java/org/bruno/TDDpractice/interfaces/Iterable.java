package org.bruno.TDDpractice.interfaces;

public interface Iterable<T> {
    Iterator<T> createIterator();
}
