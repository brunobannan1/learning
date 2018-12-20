package org.bruno.jpqlTestProject;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Repository extends CrudRepository<Book, Long> {

    List<Book> findByName(String name);

}