package dao;

import entity.Game;
import exceptions.GameWithSuchTitleAlreadyExistsException;
import util.ConnectionManager;
import util.TimeUtils;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameDao implements Dao<Integer, Game> {

    private final String GAME_WITH_SUCH_TITLE_ALREADY_EXISTS = "Игра с таким названием уже существует!";

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

    @Override
    public Game save(Game game) throws GameWithSuchTitleAlreadyExistsException {
        Game gameCheck = findByTitle(game.getTitle());
        if (gameCheck != null) {
            throw new GameWithSuchTitleAlreadyExistsException(GAME_WITH_SUCH_TITLE_ALREADY_EXISTS);
        }

        try (final Connection connection = ConnectionManager.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL,
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public Game findById(Integer game_id) {
        Game game = null;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_GAME_BY_ID)) {

            preparedStatement.setLong(1, game_id);
            // выборка данных с помощью select
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    game = buildGameEntity(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return game;
    }

    @Override
    public List<Game> findAll() {
        List<Game> games = new ArrayList<>();

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement pst = connection.prepareStatement(FIND_ALL_GAMES)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    games.add(buildGameEntity(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return games;
    }

    @Override
    public void update(Game game) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setDouble(1, game.getPrice());
            //executeUpdate: выполняет такие команды, как INSERT, UPDATE, DELETE, CREATE TABLE, DROP TABLE
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(Integer game_id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, game_id);

            // проверка, что кол-во затронутых строк > 0
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Game findByTitle(String title) {
        Game game = null;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_TITLE)) {
            preparedStatement.setString(1, title);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    game = buildGameEntity(rs);
                    return game;
                }
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
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
        return game;
    }
}
