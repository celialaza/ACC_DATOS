package org.example.game;


import org.example.utils.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class GameRepository implements Repository<Game> {

    SessionFactory sessionFactory;

    public GameRepository(SessionFactory sessionFactory) {
        this.sessionFactory=sessionFactory;
    }

    @Override
    public Game save(Game entity) {
        return null;
    }

    @Override
    public Optional<Game> delete(Game entity) {
        // 1. Abrimos conexión.
        try(Session session=sessionFactory.openSession()){
            // 2. Iniciamos TRANSACCIÓN (importante para modificar datos).
            // Es un "todo o nada". Si algo falla, no se guarda nada.
            session.beginTransaction();
            // 3. Borramos el objeto.
            session.remove(entity);
            // 4. COMMIT: "Confirma los cambios y escríbelos en el disco duro de la BD".
            session.getTransaction().commit();
            return Optional.ofNullable(entity);
        }
    }

    @Override
    public Optional<Game> deleteById(Long id) {
        try(Session session=sessionFactory.openSession()){
            // Primero buscamos si existe el juego con ese ID.
            Game game = session.find(Game.class,id);
            if(game!=null){
                session.beginTransaction();
                session.remove(game);
                session.getTransaction().commit();
            }
            return Optional.ofNullable(game);
        }
    }

    @Override
    public Optional<Game> findById(Long id) {
        try(Session session=sessionFactory.openSession()){
            // .find() es el método más básico: "Busca en la tabla Game la fila con este ID".
            // No necesita transacción porque es solo lectura.
            return Optional.ofNullable(session.find(Game.class, id));
        }
    }

    @Override
    public List<Game> findAll() {
        try(Session session=sessionFactory.openSession()){
            // HQL: "Dame todos los objetos de la clase Game".
            // .list() ejecuta la consulta y devuelve la lista de Java llena.
            return session.createQuery("from Game",Game.class).list();
        }
    }

    @Override
    public Long count() {
        try(Session session=sessionFactory.openSession()){
            // HQL: Cuenta cuántas filas (g) hay en Game.
            // getSingleResult() se usa cuando sabemos que la consulta devuelve UN solo dato (un número).
            Long salida = session.createQuery("SELECT count(g) from Game g",Long.class).getSingleResult();
            return salida;
        }
    }
}
