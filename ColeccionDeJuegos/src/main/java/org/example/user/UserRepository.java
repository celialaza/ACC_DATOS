package org.example.user;


import org.example.utils.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class UserRepository implements Repository<User> {

    private SessionFactory sessionFactory;

    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User save(User entity) {
        return null;
    }

    @Override
    public Optional<User> delete(User entity) {
        return Optional.empty();
    }

    @Override
    public Optional<User> deleteById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public Long count() {
        return 0L;
    }

    public Optional<User> findByEmail(String email) {
        // try-with-resources: Abre la sesión y la CIERRA automáticamente al terminar el bloque {}.
        // session.openSession() es como decir "Abro una línea telefónica con la base de datos".
        try(Session session = sessionFactory.openSession()) {

            // HQL (Hibernate Query Language):
            // No escribimos "SELECT * FROM user", escribimos "from User".
            // Usamos el nombre de la CLASE Java, no de la tabla SQL.
            // :email es un parámetro seguro para evitar hackeos (SQL Injection).
            Query<User> q = session.createQuery(
                    "from User where email=:email",User.class);

            // Rellenamos el hueco :email con el valor real.
            q.setParameter("email", email);

            // uniqueResult(): Devuelve el usuario si lo encuentra, o null si no.
            return Optional.ofNullable(q.uniqueResult());
        }
    }
}