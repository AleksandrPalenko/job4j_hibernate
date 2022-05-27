package ru.job4j.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.model.Author;
import ru.job4j.model.Book;

import java.util.ArrayList;
import java.util.List;

public class HbmRunForAuthorAndBooks {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            List<Book> list = new ArrayList<>();
            Book one = Book.of("Chapter1");
            Book two = Book.of("Chapter2");
            Book free = Book.of("Chapter3");
            Book four = Book.of("Chapter4");
            Book five = Book.of("Chapter5");
            list.add(one);
            list.add(two);
            list.add(free);
            list.add(four);
            list.add(five);

            Book first = Book.of("firstBoor");
            Book second = Book.of("secondBook");

            Author author = Author.of("RussianAuthor");
            author.getBooks().addAll(list);

            Author author2 = Author.of("AmericanAuthor");
            author2.getBooks().add(first);
            author2.getBooks().add(second);

            session.persist(author);
            session.persist(author2);
            /**
            Author author3 = session.get(Author.class, 2);
            session.remove(author3);
            */
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}


