package org.bruno.jpqlTestProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootApplication
public class JpqlTestProjectApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(JpqlTestProjectApplication.class, args);
        Repository repository = context.getBean(Repository.class);
        AuthorRepository authorRepository = context.getBean(AuthorRepository.class);

        Author nabokov = new Author("Vladimir Nabokov");
        Author herbert = new Author("Herbert Wells");
        authorRepository.save(nabokov);
        authorRepository.save(herbert);

        repository.save(new Book("Lolita", nabokov, LocalDate.of(1955,11,11)));
        repository.save(new Book("War of Worlds", herbert, LocalDate.of(1981,12,20)));
        repository.save(new Book("Time machine", herbert, LocalDate.of(1957,1,1)));
        Book timeMachine = repository.findByName("Time machine").get(0);
        Iterable<Author> authors = authorRepository.findAll();
        authors.forEach(author -> System.out.println(author));
        System.out.println(timeMachine);
    }
}

