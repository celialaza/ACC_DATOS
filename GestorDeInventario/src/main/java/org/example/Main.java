package org.example;

import org.example.CONEXION.DataManager;
import org.example.DAO.ProductoDAO;
import org.example.MODELO.Producto;

import javax.sql.DataSource;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {
        DataSource ds = DataManager.getDataSource();
        ProductoDAO dao = new ProductoDAO(ds);
        Producto producto = new Producto("Teclado", 49.99, 100);
        Producto producto1 = new Producto("ratón", 25.95, 200);
        Producto producto2 = new Producto("monitor", 199.99, 50);
        Producto producto3 = new Producto("auriculares", 79.99, 150);
        Producto producto4 = new Producto("altavoces", 59.99, 80);
        Producto producto5 = new Producto("webcam", 89.99, 60);

        System.out.println("Comprobando productos iniciales");
        System.out.println(dao.findAllProductos());

        /*System.out.println("Creando productos...");
        System.out.println(dao.crearProduto(producto));
        System.out.println(dao.crearProduto(producto1));
        System.out.println(dao.crearProduto(producto2));
        System.out.println(dao.crearProduto(producto3));
        System.out.println(dao.crearProduto(producto4));
        System.out.println(dao.crearProduto(producto5));*/

        System.out.println("Comprobando stock tras inserciones");
        System.out.println(dao.findAllProductos());

        System.out.println("Buscando producto por nombre: ");
        System.out.println(dao.buscarPorNombre("monitor"));

        System.out.println("Eliminando producto con id: ");
        System.out.println(dao.eliminarProducto(6));
        System.out.println(dao.eliminarProducto(7));
        System.out.println(dao.eliminarProducto(8));
        System.out.println(dao.eliminarProducto(9));
        System.out.println(dao.eliminarProducto(10));
        System.out.println(dao.eliminarProducto(11));
        System.out.println(dao.eliminarProducto(12));
        System.out.println(dao.eliminarProducto(13));
        System.out.println(dao.eliminarProducto(14));
        System.out.println(dao.eliminarProducto(15));
        System.out.println(dao.eliminarProducto(16));
        System.out.println(dao.eliminarProducto(17));
        System.out.println(dao.eliminarProducto(18));
        System.out.println(dao.eliminarProducto(19));
        System.out.println(dao.eliminarProducto(20));
        System.out.println(dao.eliminarProducto(21));
        System.out.println(dao.eliminarProducto(22));
        System.out.println(dao.eliminarProducto(23));
        System.out.println(dao.eliminarProducto(24));
        System.out.println(dao.eliminarProducto(25));





        }
    }

