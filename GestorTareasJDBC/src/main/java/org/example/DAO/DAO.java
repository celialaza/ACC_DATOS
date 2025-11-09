package org.example.DAO;

import org.example.MODELO.Tarea;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    /* Métodos de escritura */
    Optional<T> save(T t);
    Optional<T> update(T t);
    Optional<T> delete(int id);

    /* Métodos de lectura */
    List<Tarea> findAll();
    List<Tarea> findById(Integer id);
}
