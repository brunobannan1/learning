package org.bruno.JsonSerializator;

public class Address{

    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Address(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address [city=" + city +"]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address that = (Address) o;

        if (!city.equals(that.city)) return false;
        return true;
    }
}