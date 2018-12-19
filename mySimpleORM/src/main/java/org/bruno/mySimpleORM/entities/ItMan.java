package org.bruno.mySimpleORM.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class ItMan implements Serializable {
    @OneToOne(cascade = CascadeType.ALL)
    Address address;
    @OneToMany(mappedBy = "itManId")
    List<Phone> phone;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int brainpower;

    public ItMan() {
    }

    public ItMan(String name, int brainpower, Address address, List<Phone> phone) {
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

    public List<Phone> getPhone() {
        return phone;
    }

    public void setPhone(List<Phone> phone) {
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
