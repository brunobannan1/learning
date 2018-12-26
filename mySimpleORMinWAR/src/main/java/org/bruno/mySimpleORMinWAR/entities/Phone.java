package org.bruno.mySimpleORMinWAR.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Phone implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(targetEntity = ItMan.class)
    @JoinColumn
    private ItMan itManId;
    private String number;

    public Phone() {
    }

    public Phone(ItMan itManId, String number) {
        this.setId(-1);
        this.itManId = itManId;
        this.number = number;
    }

    public ItMan getItManId() {
        return itManId;
    }

    public void setItManId(ItMan itManId) {
        this.itManId = itManId;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }
}
