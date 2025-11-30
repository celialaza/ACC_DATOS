package org.example.empleado;

import org.example.utils.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class EmpleadoRepository implements Repository<Empleado> {

    private SessionFactory sessionFactory;

    public EmpleadoRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public Empleado save(Empleado entity) {
        return null;
    }

    @Override
    public Optional<Empleado> delete(Empleado entity) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(entity);
            session.getTransaction().commit();
            return Optional.ofNullable(entity);
        }

    }

    @Override
    public Optional<Empleado> deleteById(Long id) {
        try(Session session = sessionFactory.openSession()) {
            Empleado e =session.find(Empleado.class, id);
            if(e != null) {
                session.beginTransaction();
                session.remove(e);
                session.getTransaction().commit();
            }
            return Optional.ofNullable(e);
        }
    }

    @Override
    public Optional<Empleado> findById(Long id) {
        try(Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.find(Empleado.class, id));
        }
    }

    @Override
    public List<Empleado> findAll() {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("from Empleado",Empleado.class).list();
        }

    }

    @Override
    public Long count() {
        try(Session session = sessionFactory.openSession()) {
            Long salida =session.createQuery("select count(e) from Empleado e",Long.class).getSingleResult();
            return salida;
        }

    }
}
