package org.example.departamento;

import org.example.utils.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class DepartamentoRepository implements Repository<Departamento> {
    SessionFactory sessionFactory;
    public DepartamentoRepository(SessionFactory sessionFactory) {
        this.sessionFactory=sessionFactory;
    }
    @Override
    public Departamento save(Departamento entity) {
        return null;
    }

    @Override
    public Optional<Departamento> delete(Departamento entity) {
        try(Session session=sessionFactory.openSession()){
            session.beginTransaction();
            session.remove(entity);
            session.getTransaction().commit();
            return Optional.ofNullable(entity);
        }

    }

    @Override
    public Optional<Departamento> deleteById(Long id) {
        try(Session session=sessionFactory.openSession()){
            Departamento dep = session.find(Departamento.class,id);
            if(dep!=null){
                session.beginTransaction();
                session.remove(dep);
                session.getTransaction().commit();
            }
            return Optional.ofNullable(dep);
        }
    }

    @Override
    public Optional<Departamento> findById(Long id) {
        try(Session session=sessionFactory.openSession()){
            return Optional.ofNullable(session.find(Departamento.class, id));
        }

    }

    @Override
    public List<Departamento> findAll() {
        try(Session session=sessionFactory.openSession()){
            return session.createQuery("from Departamento",Departamento.class).list();
        }
    }

    @Override
    public Long count() {
        try(Session session = sessionFactory.openSession()) {
            Long salida = session.createQuery("SELECT count(d) from Departamento d", Long.class).getSingleResult();
            return salida;
        }
    }
}
