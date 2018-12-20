package org.bruno.JsonSerializator;

import java.util.Objects;

public class Address {

    private String city;
    private Person person;

    public Address(String city, Person person) {
        this.city = city;
        this.person = person;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(city, address.city) &&
                Objects.equals(person, address.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, person);
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", person=" + person +
                '}';
    }
}