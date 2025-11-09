package org.example.DAO;

import org.example.MODELO.Contacto;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ContactoDAO<T> {

//---------MÉTODS DE ESCRITURA---------
    Optional<T> crearContacto(T t)throws SQLException;
    Optional<T> actualizarContacto(T t)throws SQLException;
   Optional<T> eliminarContacto(int id)throws SQLException;

//-----------MÉTODOS DE LECTURA--------
    List<Contacto> findAllContactos()throws SQLException;
    List<Contacto>buscarPorNombre(String nombre)throws SQLException;
    List<Contacto>buscarContactoPorId(Integer id)throws SQLException;
    List<Contacto>buscarContactoPorEmail(String email)throws SQLException;
   List<Contacto>buscarContactoPorTelefono(String telefono)throws SQLException;
}
