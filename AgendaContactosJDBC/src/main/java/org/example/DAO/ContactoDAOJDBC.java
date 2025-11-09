package org.example.DAO;

import org.example.CONEXION.DataManager;
import org.example.MODELO.Contacto;
import org.example.DAO.ContactoDAO;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


public class ContactoDAOJDBC implements ContactoDAO<Contacto> {

    //Variable para guardar la conexión a la base de datos
    private final DataSource datasource;
    private final Logger logger = Logger.getLogger(String.valueOf(ContactoDAOJDBC.class));

    //Constructor: recibe el Datasource(conexión) y la guarda
    public ContactoDAOJDBC(DataSource datasource) {
        this.datasource = datasource;

    }

    //Método mapper que convierte un resultado de la BD(ResultSet)
    //en un objeto Java Contacto
    private Contacto mapper(ResultSet rs) throws SQLException {
        return new Contacto(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("email"),
                rs.getString("Telefono")
        );
    }

    @Override
    public Optional crearContacto(Contacto contacto) throws SQLException {
        logger.info("Creando contacto");
        String sql ="INSERT INTO contactos (nombre, email, Telefono) VALUES (?, ?, ?)";
        try(Connection conn= datasource.getConnection();
            PreparedStatement pstmt= conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, contacto.getNombre());
            pstmt.setString(2, contacto.getEmail());
            pstmt.setString(3, contacto.getTelefono());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                logger.info("No se guardó el contacto " + contacto.getNombre());
                return Optional.empty();
            }
            try(ResultSet generatedKeys =pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    contacto.setId(generatedKeys.getInt(1));
                    return Optional.of(contacto);
                } else {
                    logger.info("No se pudo obtener el ID generado para el contacto " + contacto.getNombre());
                    return Optional.empty();
                }
            }}}

    @Override
    public Optional actualizarContacto(Contacto contacto) throws SQLException {
        logger.info(" Actualizando contactos" + contacto.getNombre());
        String sql = "UPDATE contactos SET nombre= ?, email = ?, telefono = ? WHERE id = ?";

        try(Connection conn= datasource.getConnection();
            PreparedStatement pstmt= conn.prepareStatement(sql)) {

            pstmt.setString(1, contacto.getNombre());
            pstmt.setString(2, contacto.getEmail());
            pstmt.setString(3, contacto.getTelefono());
            pstmt.setInt(4, contacto.getId());


            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Se actualizó el contacto " +contacto.getNombre());
                return Optional.of(contacto);
            }

        } catch (SQLException e) {
            logger.info("Error updating game: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }



    @Override
    public Optional eliminarContacto(int id) throws SQLException {
        logger.info(" Eliminando contacto" +id);
        String sql = "DELETE FROM contactos WHERE id = ?";

        try(Connection conn= datasource.getConnection();
            PreparedStatement pstmt= conn.prepareStatement(sql)) {


            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Se borró el contacto " +id);
                return Optional.empty();
            }

        } catch (SQLException e) {
            logger.info("Error al eliminar: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }


    @Override
    public List<Contacto> findAllContactos() throws SQLException {

        logger.info("Buscando contactos");
        List<Contacto> contactos = new ArrayList<>();
        String sql= "SELECT * FROM contactos";

        try(Connection conn= datasource.getConnection();
            PreparedStatement pstmt= conn.prepareStatement(sql)) {


            ResultSet rs= pstmt.executeQuery();
            while(rs.next()){
              contactos.add(mapper(rs));
            }
        } catch (SQLException e) {
            logger.info("Error buscando los contactos"+ e.getMessage());
            throw new RuntimeException(e);
        }
        return contactos;
    }


    @Override
    public List<Contacto> buscarContactoPorId(Integer id) throws SQLException {
        logger.info("Buscando contactos por id");
        List<Contacto> contactos = new ArrayList<>();
        String sql= "SELECT * FROM contactos  WHERE id = ?";

        try(Connection conn= datasource.getConnection();
            PreparedStatement pstmt= conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs= pstmt.executeQuery();
            while(rs.next()){
                contactos.add(mapper(rs));
            }
        } catch (SQLException e) {
            logger.info("Error buscando los contactos"+ e.getMessage());
            throw new RuntimeException(e);
        }
        return contactos;
    }




    @Override
    public List<Contacto> buscarPorNombre(String nombre) throws SQLException {
        return List.of();
    }

   @Override
    public List<Contacto> buscarContactoPorEmail(String email) throws SQLException {
        return List.of();
    }

    @Override
    public List<Contacto> buscarContactoPorTelefono(String telefono) throws SQLException {
        return List.of();
    }


}
