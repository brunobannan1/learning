package org.bruno.mySimpleORM.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ItMan implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int brainpower;
    @OneToOne(cascade = CascadeType.ALL)
    Address address;
    @OneToOne(cascade = CascadeType.ALL)
    Phone phone;

    public ItMan() {
    }

    public ItMan(String name, int brainpower, Address address, Phone phone) {
        this.setId(-1);
        this.name = name;
        this.brainpower = brainpower;
        this.address = address;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBrainpower() {
        return brainpower;
    }

    public void setBrainpower(int brainpower) {
        this.brainpower = brainpower;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "ItMan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brainpower=" + brainpower +
                ", address=" + address +
                ", phone=" + phone +
                '}';
    }
}
