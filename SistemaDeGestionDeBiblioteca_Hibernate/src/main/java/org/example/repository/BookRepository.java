package org.example.repository;


import org.example.model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;



    public class BookRepository implements Repository<Book> {

        private final SessionFactory sessionFactory;

        // Inyección de dependencias a través del constructor
        public BookRepository(SessionFactory sessionFactory) {
            this.sessionFactory = sessionFactory;
        }

        // --- MÉTODOS DE LA INTERFAZ REPOSITORY ---

        @Override
        public Book save(Book entity) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                // Usamos merge en lugar de persist para que sirva tanto para guardar (nuevo)
                // como para actualizar (existente).
                Book savedBook = session.merge(entity);
                session.getTransaction().commit();
                return savedBook;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public Optional<Book> delete(Book entity) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                // session.remove necesita que la entidad esté adjunta (managed).
                // Si el objeto viene "suelto", merge lo re-adjunta antes de borrarlo.
                session.remove(session.contains(entity) ? entity : session.merge(entity));
                session.getTransaction().commit();
                return Optional.of(entity);
            } catch (Exception e) {
                e.printStackTrace();
                return Optional.empty();
            }
        }

        @Override
        public Optional<Book> deleteById(Long id) {
            try (Session session = sessionFactory.openSession()) {
                // Primero buscamos si existe
                Book book = session.find(Book.class, id);
                if (book != null) {
                    session.beginTransaction();
                    session.remove(book);
                    session.getTransaction().commit();
                    return Optional.of(book);
                }
                return Optional.empty();
            }
        }

        @Override
        public Optional<Book> findById(Long id) {
            try (Session session = sessionFactory.openSession()) {
                // session.find devuelve null si no existe, Optional.ofNullable maneja eso bien
                return Optional.ofNullable(session.find(Book.class, id));
            }
        }

        @Override
        public List<Book> findAll() {
            try (Session session = sessionFactory.openSession()) {
                // HQL: Referencia a la Clase 'Book', no a la tabla 'books'
                return session.createQuery("FROM Book", Book.class).list();
            }
        }

        @Override
        public Long count() {
            try (Session session = sessionFactory.openSession()) {
                return session.createQuery("SELECT count(b) FROM Book b", Long.class).getSingleResult();
            }
        }

        // --- MÉTODOS ADICIONALES (ESPECÍFICOS DE BOOK) ---

        /**
         * Busca libros que contengan una palabra clave en su título.
         * Ejemplo de uso de parámetros HQL para evitar inyección SQL.
         */
        public List<Book> findByTitleContaining(String keyword) {
            try (Session session = sessionFactory.openSession()) {
                String hql = "FROM Book b WHERE b.title LIKE :keyword";
                Query<Book> query = session.createQuery(hql, Book.class);

                // Los % son comodines de SQL para buscar "que contenga"
                query.setParameter("keyword", "%" + keyword + "%");

                return query.list();
            }
        }

        /**
         * Método extra: Buscar todos los libros de un Autor concreto por su ID.
         * Útil para comprobar la relación ManyToOne.
         */
        public List<Book> findByAuthorId(Long authorId) {
            try (Session session = sessionFactory.openSession()) {
                // Observa cómo navegamos por la propiedad 'author.id'
                String hql = "FROM Book b WHERE b.author.id = :id";
                return session.createQuery(hql, Book.class)
                        .setParameter("id", authorId)
                        .list();
            }
        }
    }