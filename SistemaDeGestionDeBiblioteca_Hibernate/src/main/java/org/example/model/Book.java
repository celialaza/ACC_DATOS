package org.example.model;



import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String genre;

    @ManyToOne
    @JoinColumn(name = "author_id") // Nombre de la columna en la tabla books
    private Author author;

    // Constructor conveniente
    public Book(String title, String genre) {
        this.title = title;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{id=" + id + ", title='" + title + "'}";
    }
}