package org.example;



import org.example.database.DataProvider;
import org.example.model.Author;
import org.example.model.Book;
import org.example.repository.BookRepository;
import org.hibernate.SessionFactory;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Obtener la factoría
        SessionFactory sessionFactory = DataProvider.getSessionFactory();

        // 2. Crear datos (Objetos)
        Author author = new Author("J.K. Rowling", "British");

        Book b1 = new Book("Harry Potter 1", "Fantasy");
        Book b2 = new Book("Harry Potter 2", "Fantasy");
        Book b3 = new Book("Animales Fantásticos", "Fantasy");

        // 3. Establecer relaciones (Usando el método helper)
        author.addBook(b1);
        author.addBook(b2);
        author.addBook(b3);

        // 4. Guardar en Base de Datos
        // Nota: Como Author tiene CascadeType.ALL, si guardamos el Autor,
        // se guardan automáticamente los libros.
        // Pero vamos a usar un repositorio de Author para hacerlo bien.

        try (var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(author); // Esto guarda autor Y libros gracias al Cascade
            session.getTransaction().commit();
            System.out.println("Autor y libros guardados!");
        }

        // 5. Probar consultas con el Repository
        BookRepository bookRepo = new BookRepository(sessionFactory);

        System.out.println("--- Buscando libros que contengan 'Potter' ---");
        List<Book> potters = bookRepo.findByTitleContaining("Potter");

        for (Book b : potters) {
            System.out.println("Encontrado: " + b.getTitle() + " del autor " + b.getAuthor().getName());
        }

        // Cerrar factory al acabar la app
        sessionFactory.close();
    }
}