package ru.job4j.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String Name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Book> books = new HashSet<>();

    public Author() {
    }

    public static Author of(String name) {
        Author author = new Author();
        author.Name = name;
        return author;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public Set<Book> getBooks() {
        return books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Author that = (Author) o;
        return id == that.id && Objects.equals(Name, that.Name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Name);
    }

    @Override
    public String toString() {
        return "Author{"
                + "id=" + id
                + ", Name='" + Name + '\''
                + '}';
    }
}
