package org.example.DAO;

import org.example.MODELO.Tarea;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TareaDAO implements DAO<Tarea> {

private final DataSource dataSource;
Logger log = Logger.getLogger(String.valueOf(TareaDAO.class));

public TareaDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    private Tarea mapper(ResultSet rs) throws SQLException {
    return  new Tarea(
            rs.getInt("id"),
            rs.getString("descripcion"),
            rs.getInt("completada")
    );
    }

    @Override
    public Optional<Tarea> save(Tarea tarea) {
    log.info("Iniciando TareaDAO save");
    String sql = "INSERT INTO tareas (descripcion, completada) VALUES (?, ?)";
    try(Connection conn=dataSource.getConnection();
        PreparedStatement pstmt= conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        pstmt.setString(1, tarea.getDescripcion());
        pstmt.setInt(2, tarea.getCompletada());

        int affectedRows = pstmt.executeUpdate();
        if (affectedRows == 0) {
            return Optional.empty();
        }
        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            if(generatedKeys.next()) {
                tarea.setId(generatedKeys.getInt(1));
                return Optional.of(tarea);
            }else{
                return Optional.empty();
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    }

    @Override
    public Optional<Tarea> update(Tarea tarea) {
    log.info("Iniciando TareaDAO update");
    String sql = "UPDATE tareas SET descripcion=?, completada=? WHERE id=?";
    try(Connection conn = dataSource.getConnection();
    PreparedStatement pstmt= conn.prepareStatement(sql)) {
        pstmt.setString(1, tarea.getDescripcion());
        pstmt.setInt(2, tarea.getCompletada());
        pstmt.setInt(3, tarea.getId());

        int affectedRows = pstmt.executeUpdate();
        if (affectedRows> 0) {
            return Optional.of(tarea);
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
        return Optional.empty();
    }

    @Override
    public Optional<Tarea> delete(int id) {
    log.info("Iniciando TareaDAO delete");
    String sql = "DELETE FROM tareas WHERE id=?";
    try(Connection conn=dataSource.getConnection();
    PreparedStatement pstmt= conn.prepareStatement(sql)){
        pstmt.setInt(1,id);
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows>0) {
            log.info("Tarea eliminada"+id);
            return Optional.empty();
        }else{
            log.info("No existe la tarea");
            return Optional.empty();
        }
    } catch (SQLException e) {
        log.info("Error al eliminar: "+e.getMessage());
        throw new RuntimeException(e);
    }

    }

    @Override
    public List<Tarea> findAll() {
    log.info("Iniciando TareaDAO findAll");
    List<Tarea> tareas = new ArrayList<>();
    String sql = "SELECT * FROM tareas";
    try(Connection conn=dataSource.getConnection();
    PreparedStatement pstmt=conn.prepareStatement(sql)){

        ResultSet rs= pstmt.executeQuery();
        while(rs.next()){
            tareas.add(mapper(rs));
        }
    } catch (SQLException e) {
        log.info("Error al obtener tareas"+e.getMessage());
        throw new RuntimeException(e);
    }
        return tareas;
    }

    @Override
    public List<Tarea> findById(Integer id) {
    log.info("Iniciando TareaDAO findById");
    List<Tarea> tareas = new ArrayList<>();
    String sql = "SELECT * FROM tareas WHERE id=?";

    try(Connection conn=dataSource.getConnection();
    PreparedStatement pstmt=conn.prepareStatement(sql)){
        pstmt.setInt(1,id);
        ResultSet rs= pstmt.executeQuery();
        while(rs.next()){
            tareas.add(mapper(rs));
        }

    } catch (SQLException e) {
        log.info("Error al obtener tareas"+e.getMessage());
        throw new RuntimeException(e);
    }
        return tareas;
    }
}
