package org.example.DAO;

import org.example.MODELO.Producto;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;



public class ProductoDAO implements DAO<Producto> {

    private final DataSource datasource;
    private final Logger logger= Logger.getLogger(ProductoDAO.class.getName());

    public ProductoDAO(DataSource dataSource) {
        this.datasource = dataSource;
    }
    private Producto mapper(ResultSet rs) throws SQLException {
        return new Producto(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getDouble("precio"),
                rs.getInt("stock")
        );
    }


    @Override
    public Optional<Producto> crearProduto(Producto producto) throws SQLException {
        logger.info("creando producto " + producto.getNombre());
        String sql ="INSERT INTO productos (nombre, precio, stock) VALUES (?, ?, ?)";
        try(Connection conn= datasource.getConnection();
            PreparedStatement pstmt= conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1,producto.getNombre());
            pstmt.setDouble(2,  producto.getPrecio());
            pstmt.setInt(3, producto.getStock());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                logger.info("No se guardó el producto " + producto.getNombre());
                return Optional.empty();
            }
            try(ResultSet generatedKeys =pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    producto.setId(generatedKeys.getInt(1));
                    return Optional.of(producto);
                } else {
                    logger.info("No se pudo obtener el ID generado para el producto " + producto.getNombre());
                    return Optional.empty();
                }
            }
        }
    }


    @Override
    public Optional<Producto> actualizarProducto(Producto producto) throws SQLException {
        logger.info(" Actualizando producto" + producto.getNombre());
        String sql = "UPDATE productos SET nombre= ?, precio = ?, stock = ? WHERE id = ?";

        try(Connection conn= datasource.getConnection();
            PreparedStatement pstmt= conn.prepareStatement(sql)) {

            pstmt.setString(1, producto.getNombre());
            pstmt.setDouble(2, producto.getPrecio());
            pstmt.setInt(3, producto.getStock());
            pstmt.setInt(4, producto.getId());


            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Se actualizó el producto " +producto.getNombre());
                return Optional.of(producto);
            }

        } catch (SQLException e) {
            logger.info("Error updating producto: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }




    @Override
    public Optional<Producto> eliminarProducto(int id) throws SQLException {
        logger.info(" Eliminando producto" +id);
        String sql = "DELETE FROM productos WHERE id = ?";

        try(Connection conn= datasource.getConnection();
            PreparedStatement pstmt= conn.prepareStatement(sql)) {


            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Se borró el producto " +id);
                return Optional.empty();
            }

        } catch (SQLException e) {
            logger.info("Error al eliminar: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }


    @Override
    public List<Producto> findAllProductos() throws SQLException {

        logger.info("Buscando productos");
        List<Producto> productos = new ArrayList<>();
        String sql= "SELECT * FROM productos";

        try(Connection conn= datasource.getConnection();
            PreparedStatement pstmt= conn.prepareStatement(sql)) {


            ResultSet rs= pstmt.executeQuery();
            while(rs.next()){
                productos.add(mapper(rs));
            }
        } catch (SQLException e) {
            logger.info("Error buscando los productos"+ e.getMessage());
            throw new RuntimeException(e);
        }
        return productos;
    }


    @Override
    public List<Producto> buscarPorNombre(String nombre) throws SQLException {
        logger.info("Iniciando busqueda de productos por nombre: " + nombre);
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE nombre=?";

        try(Connection conn=datasource.getConnection();
            PreparedStatement pstmt=conn.prepareStatement(sql)){
            pstmt.setString(1,nombre);
            ResultSet rs= pstmt.executeQuery();
            while(rs.next()){
                productos.add(mapper(rs));
            }

        } catch (SQLException e) {
            logger.info("Error al obtener los productos"+e.getMessage());
            throw new RuntimeException(e);
        }
        return productos;


    }
}
