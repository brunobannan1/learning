package org.bruno.jpqlTestProject;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Repository extends CrudRepository<Book, Long> {

    List<Book> findByName(String name);

    @Query(value = "SELECT count(b.name) FROM Book b where b.author.name = ?1")
    int findCountOfBooksByAuthorName(String authorName);
}