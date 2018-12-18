package org.bruno.mySimpleORM;

import org.bruno.mySimpleORM.interfaces.DBService;

import java.util.List;

public class PersonDao {
    private DBService myOrmDBService;

    public PersonDao(DBService myOrmDBService) {
        this.myOrmDBService = myOrmDBService;
    }

    public void save(Person person) {
        myOrmDBService.save(person);
    }

    public Person read(String condition) {
        return (Person) myOrmDBService.read(Person.class, condition);
    }

    public List<Object> readAll() {
        return null;
    }
}
