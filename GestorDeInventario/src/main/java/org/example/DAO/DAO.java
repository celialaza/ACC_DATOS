package org.example.DAO;

import org.example.MODELO.Producto;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    //---------MÉTODS DE ESCRITURA---------
    Optional<T> crearProduto(T t)throws SQLException;
    Optional<T> actualizarProducto(T t)throws SQLException;
    Optional<T> eliminarProducto(int id)throws SQLException;

    //-----------MÉTODOS DE LECTURA--------
    List<Producto> findAllProductos()throws SQLException;
    List<Producto> buscarPorNombre(String nombre)throws SQLException;


}

