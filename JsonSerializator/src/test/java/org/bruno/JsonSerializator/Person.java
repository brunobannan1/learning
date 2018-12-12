package org.bruno.JsonSerializator;

import java.util.Arrays;
import java.util.Objects;

public class Person {
    private String name;
    private int age;
    private Person[] friends;

    public Person(String name, int age, Person[] friends) {
        this.name = name;
        this.age = age;
        this.friends = friends;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                Objects.equals(name, person.name) &&
                Arrays.equals(friends, person.friends);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, age);
        result = 31 * result + Arrays.hashCode(friends);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", friends=" + Arrays.toString(friends) +
                '}';
    }
}
