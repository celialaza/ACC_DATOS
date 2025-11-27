package org.example.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "authors")

public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nationality;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
     book.setAuthor(this);
        this.books.add(book);
    }
    public Author(String name, String nationality) {
        this.name = name;
        this.nationality = nationality;
    }
}
