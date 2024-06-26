package dao;

import entities.Game;
import exceptions.DataBaseException;
import exceptions.GameWithSuchTitleAlreadyExistsException;
import util.ConnectionManager;
import util.TimeUtils;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameDao implements Dao<Integer, Game> {
    private static final ConnectionManager connectionManager = new ConnectionManager();
    private static final Logger logger = Logger.getLogger(GameDao.class);
    private static final String GAME_WITH_SUCH_TITLE_ALREADY_EXISTS = "Игра с таким названием уже существует!";

    // найти все игры
    private static final String FIND_ALL_GAMES = """
            SELECT game_id, title, description, price, release_date
            FROM games
            """;

    // вставить новую игру
    private static final String SAVE_SQL = """
            INSERT INTO games(title, description, price, release_date)
            VALUES (?, ?, ?, ?)
            """;

    // найти игру по id
    private static final String FIND_GAME_BY_ID = """
            SELECT game_id, title, description, price, release_date
            FROM games
            WHERE game_id = ?
            """;

    // удалить игру по id
    private static final String DELETE_SQL = """
            DELETE FROM games
            WHERE game_id = ?
            """;

    // изменить игру по id
    private static final String UPDATE_SQL = """
            UPDATE games
            SET price = ?
            WHERE game_id = ?
            """;

    private static final String FIND_BY_TITLE = """
            SELECT game_id, title, description, price, release_date
            FROM games
            WHERE title = ?
            """;

    private static final String FIND_TITLE_BY_ID = """
            SELECT title
            FROM games
            WHERE  game_id = ?
            """;

    private static final String FIND_ID_BY_TITLE = """
            SELECT game_id
            FROM games
            WHERE  title = ?
            """;

    @Override
    public Game save(Game game) throws GameWithSuchTitleAlreadyExistsException, DataBaseException {
        Game gameCheck = findByTitle(game.getTitle());
        if (gameCheck != null) {
            throw new GameWithSuchTitleAlreadyExistsException(GAME_WITH_SUCH_TITLE_ALREADY_EXISTS);
        }

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, game.getTitle());
            preparedStatement.setObject(2, game.getDescription());
            preparedStatement.setObject(3, game.getPrice());
            preparedStatement.setObject(4, game.getDateOfRelease());

            preparedStatement.executeUpdate();
            final ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                game.setId(generatedKeys.getObject(1, Integer.class));
            }
            return game;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DataBaseException(e.getMessage());
        }
    }

    @Override
    public Game findById(Integer game_id) throws DataBaseException {
        Game game = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_GAME_BY_ID)) {

            preparedStatement.setLong(1, game_id);
            // выборка данных с помощью select
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    game = buildGameEntity(rs);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DataBaseException(e.getMessage());
        }
        return game;
    }

    @Override
    public List<Game> findAll() throws DataBaseException {
        List<Game> games = new ArrayList<>();

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement pst = connection.prepareStatement(FIND_ALL_GAMES)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    games.add(buildGameEntity(rs));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DataBaseException(e.getMessage());
        }

        return games;
    }

    @Override
    public void update(Game game) throws DataBaseException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setDouble(1, game.getPrice());
            preparedStatement.setObject(2, game.getId());
            //executeUpdate: выполняет такие команды, как INSERT, UPDATE, DELETE, CREATE TABLE, DROP TABLE
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DataBaseException(e.getMessage());
        }
    }

    public Integer findIdByTitle(String title) throws DataBaseException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ID_BY_TITLE)) {
            preparedStatement.setString(1, title);
            //executeUpdate: выполняет такие команды, как INSERT, UPDATE, DELETE, CREATE TABLE, DROP TABLE
            Integer result;
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    result = rs.getInt("game_id");
                    return result;
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DataBaseException(e.getMessage());
        }
        return null;
    }

    public String findTitleById(Integer id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_TITLE_BY_ID)) {
            preparedStatement.setInt(1, id);
            //executeUpdate: выполняет такие команды, как INSERT, UPDATE, DELETE, CREATE TABLE, DROP TABLE
            String result;
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    result = rs.getString("title");
                    return result;
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
        return null;
    }

    @Override
    public boolean deleteById(Integer game_id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, game_id);

            // проверка, что кол-во затронутых строк > 0
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Game findByTitle(String title) throws DataBaseException {
        Game game = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_TITLE)) {
            preparedStatement.setString(1, title);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    game = buildGameEntity(rs);
                    return game;
                }
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DataBaseException(e.getMessage());
        }
        return game;
    }

    private Game buildGameEntity(ResultSet resultSet) throws SQLException {
        Game game = new Game();

        game.setId(resultSet.getInt("game_id"));
        game.setTitle(resultSet.getString("title"));
        game.setDescription(resultSet.getString("description"));
        game.setPrice(resultSet.getDouble("price"));
        game.setDateOfRelease(TimeUtils.convertToLocalDateViaSqlDate(resultSet.getDate("release_date")));
        game.setKeys(null);
        return game;
    }
}
