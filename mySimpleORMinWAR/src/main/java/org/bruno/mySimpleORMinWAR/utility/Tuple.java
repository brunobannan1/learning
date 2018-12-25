package org.bruno.mySimpleORMinWAR.utility;

import java.util.Objects;

public class Tuple<T, V> {
    private T fst;
    private V snd;

    public Tuple(T fst, V snd) {
        this.fst = fst;
        this.snd = snd;
    }

    public T fst() {
        return fst;
    }

    public void setFst(T fst) {
        this.fst = fst;
    }

    public V snd() {
        return snd;
    }

    public void setSnd(V snd) {
        this.snd = snd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return Objects.equals(fst, tuple.fst) &&
                Objects.equals(snd, tuple.snd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fst, snd);
    }

    @Override
    public String toString() {
        return "Tuple{" +
                "fst=" + fst +
                ", snd=" + snd +
                '}';
    }
}