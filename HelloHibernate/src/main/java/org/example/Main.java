package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        var hib = new Configuration().configure();
        SessionFactory factory = hib.buildSessionFactory();

        //Mostrar todos los juegos
        try (Session session = factory.openSession()) {
            List<Game> games = session.createQuery("from Game").list();
            games.forEach(System.out::println);
        }
        /*  try (Session session = factory.openSession()) {
            Query<Game> query = session.createQuery("from Game");
            List<Game> games = query.list();
            for (Game game : games) {
                System.out.println(game);*/


        //Guardar un juego
        Game game = new Game();
        game.setTitle("Title");
        game.setPlatform("PC");
        game.setYear(2024);
        game.setDescription("Description");
        game.setUser_id(1);
        game.setImage_url("http://image.url");


        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(game);
            session.getTransaction().commit();
        }

        /*factory.inTransaction(session) -> {
            session.persist(game);
        };*/


        //Actualizar un juego
        try(Session session = factory.openSession()) {
            session.beginTransaction();
        Game g5 = session.find(Game.class, 5);
        System.out.println(g5);
        g5.setTitle("ciberpunk 333");
        session.merge(g5);
        session.getTransaction().commit();
        System.out.println(g5);}



        //Eliminar un juego
            factory.inTransaction((Session session) ->{
            Game g = session.find(Game.class, 45);
            if(g != null)session.remove(g);
                }
                    );

            try(Session session = factory.openSession()) {
            List<Game> games = session.createQuery("from Game").list();
            games.forEach(System.out::println);
        }

        System.out.println("__----------------------------------__");
            factory.inSession((Session session) -> {
                Query<Game> query = session.createQuery("from Game where year = :year");
                query.setParameter("year", 1998);
                List<Game> games = query.getResultList();
                games.forEach(System.out::println);
            });

            }

        }



