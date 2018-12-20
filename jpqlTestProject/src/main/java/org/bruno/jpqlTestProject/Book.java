package org.bruno.jpqlTestProject;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @ManyToOne
    private Author author;
    private LocalDate localDate;

    protected Book() {
    }

    public Book(String name, Author author, LocalDate localDate) {
        this.name = name;
        this.author = author;
        this.localDate = localDate;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Author getAuthor() {
        return author;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author=" + author +
                ", localDate=" + localDate +
                '}';
    }
}
