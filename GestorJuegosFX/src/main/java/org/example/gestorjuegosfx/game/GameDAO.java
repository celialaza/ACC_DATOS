package org.example.gestorjuegosfx.game;

import org.example.gestorjuegosfx.data.DAO;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class GameDAO implements DAO<Game> {


    //Variale para guardar la conexión a la base de datos
    private final DataSource datasource;
    private final Logger logger = Logger.getLogger(GameDAO.class.getName());

    //Constructor:recibe el DataSource(la conexión) y la guarda
    public GameDAO(DataSource datasource) {
        this.datasource = datasource;
    }
    // Método mapper que convierte un resultado de la BD (ResultSet)
    // en un objeto Java Game.
    private Game mapper(ResultSet rs) throws SQLException {
        return new Game(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("platform"),
                rs.getInt("year"),
                rs.getString("description"),
                rs.getInt("user_id"),
                rs.getString("image_url")
        );
    }
    public List<Game> findAllByUserId(Integer userId) {
        logger.info("Finding all Games by user id " + userId);
        List<Game> games = new ArrayList<>();
        String sql= "SELECT * FROM games WHERE user_id = ?";

        try(Connection conn= datasource.getConnection();
            PreparedStatement pstmt= conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs= pstmt.executeQuery();
            while(rs.next()){
                games.add(mapper(rs));
            }
        } catch (SQLException e) {
            logger.info("Error finding games by user id"+ e.getMessage());
            throw new RuntimeException(e);
        }
        return games;
    }


    @Override
    public Optional<Game> save(Game game) {
        logger.info("Saving Game " + game.getTitle());
        String sql = "INSERT INTO games (title, platform, year, description, user_id, image_url) VALUES (?, ?, ?, ?, ?, ?)";

        try(Connection conn= datasource.getConnection();
        PreparedStatement pstmt= conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, game.getTitle());
            pstmt.setString(2, game.getPlatform());
            pstmt.setInt(3, game.getYear());
            pstmt.setString(4, game.getDescription());
            pstmt.setInt(5, game.getUser_id());
            pstmt.setString(6, game.getImage_url());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                logger.info("No se guardó el juego " + game.getTitle());
                return Optional.empty();
            }
            try(ResultSet generatedKeys =pstmt.getGeneratedKeys()) {
                if(generatedKeys.next()){
                    game.setId(generatedKeys.getInt(1));
                    return Optional.of(game);
                }else{
                    logger.info("No se pudo obtener el ID generado para el juego " + game.getTitle());
                    return Optional.empty();
                }
            }

        } catch (SQLException e) {
            logger.info("Error saving game: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Game> update(Game game) {
        logger.info("Updating Game " + game.getTitle());
        String sql = "UPDATE games SET title = ?, platform = ?, year = ?, description = ?, user_id = ?, image_url = ? WHERE id = ?";

        try(Connection conn= datasource.getConnection();
            PreparedStatement pstmt= conn.prepareStatement(sql)) {

            pstmt.setString(1, game.getTitle());
            pstmt.setString(2, game.getPlatform());
            pstmt.setInt(3, game.getYear());
            pstmt.setString(4, game.getDescription());
            pstmt.setInt(5, game.getUser_id());
            pstmt.setString(6, game.getImage_url());
            pstmt.setInt(7, game.getId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Se actualizó el juego " + game.getTitle());
                return Optional.of(game);
            }

        } catch (SQLException e) {
            logger.info("Error updating game: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Game> delete(Game game) {
        return Optional.empty();
    }

    @Override
    public List<Game> findAll() {
        return List.of();
    }

    @Override
    public Optional<Game> findById(Integer id) {
        return Optional.empty();
    }
}
