package org.bruno.mySimpleORM;

import org.bruno.mySimpleORM.utility.AutoIncrement;

import java.util.Arrays;
import java.util.Objects;

public class Person {

    private @AutoIncrement int id;
    private String fio;
    private int weight;
    private int age;
    private boolean canDoProgramming;
    private char[] lastMarks;

    public Person() {

    }

    public Person(int id, String fio, int weight, int age, boolean canDoProgramming, char[] lastMarks) {
        this.id = id;
        this.fio = fio;
        this.weight = weight;
        this.age = age;
        this.canDoProgramming = canDoProgramming;
        this.lastMarks = lastMarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id &&
                weight == person.weight &&
                age == person.age &&
                canDoProgramming == person.canDoProgramming &&
                Objects.equals(fio, person.fio) &&
                Arrays.equals(lastMarks, person.lastMarks);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, fio, weight, age, canDoProgramming);
        result = 31 * result + Arrays.hashCode(lastMarks);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", fio='" + fio + '\'' +
                ", weight=" + weight +
                ", age=" + age +
                ", canDoProgramming=" + canDoProgramming +
                ", lastMarks=" + Arrays.toString(lastMarks) +
                '}';
    }
}