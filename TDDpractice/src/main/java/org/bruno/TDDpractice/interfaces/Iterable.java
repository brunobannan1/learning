package org.bruno.TDDpractice.interfaces;

import java.util.List;

public interface Iterable<T> {
    Iterator<T> createIterator(List<T> list);
}