package org.bruno.mySimpleORMinWAR.entities;

import org.bruno.mySimpleORMinWAR.interfaces.AutoIncrement;

import java.util.Objects;

public class Person {

    @AutoIncrement
    private long id;
    private String fio;
    private long weight;
    private long age;
    private boolean candoprogramming;
    private String lastmarks;

    public Person() {

    }

    public Person(long id, String fio, long weight, long age, boolean canDoProgramming, String lastmarks) {
        this.id = id;
        this.fio = fio;
        this.weight = weight;
        this.age = age;
        this.candoprogramming = canDoProgramming;
        this.lastmarks = lastmarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id &&
                weight == person.weight &&
                age == person.age &&
                candoprogramming == person.candoprogramming &&
                Objects.equals(fio, person.fio) &&
                Objects.equals(lastmarks, person.lastmarks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fio, weight, age, candoprogramming, lastmarks);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", fio='" + fio + '\'' +
                ", weight=" + weight +
                ", age=" + age +
                ", candoprogramming=" + candoprogramming +
                ", lastmarks='" + lastmarks + '\'' +
                '}';
    }
}