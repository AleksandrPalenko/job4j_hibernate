package ru.job4j.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String Name;

    public Book() {
    }

    public static Book of(String name) {
        Book book = new Book();
        book.Name = name;
        return book;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book that = (Book) o;
        return id == that.id && Objects.equals(Name, that.Name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Name);
    }

    @Override
    public String toString() {
        return "Book{"
                + "id=" + id
                + ", Name='" + Name + '\''
                + '}';
    }
}
