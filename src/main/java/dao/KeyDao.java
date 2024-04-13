package dao;

import entities.Key;
import util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KeyDao implements Dao<Integer, Key> {
    private static final String FIND_ALL_KEYS = """
            SELECT key_id, game_key, game_id
            FROM keys
            """;
    private static final String SAVE_SQL = """
            INSERT INTO keys(game_key, game_id)
            VALUES (?, ?)
            """;

    private static final String FIND_KEY_BY_ID = """
            SELECT key_id, game_key, game_id
            FROM keys
            WHERE key_id = ?
            """;

    private static final String DELETE_SQL = """
            DELETE FROM keys
            WHERE key_id = ?
            """;

    // изменить игру по id
    private static final String UPDATE_SQL = """
            UPDATE keys
            SET game_key = ?, game_id = ?
            WHERE key_id = ?
            """;

    private static final String FIND_BY_GAME_ID = """
            SELECT key_id, game_key, game_id
            FROM keys
            WHERE game_id = ?
            """;

    private static final String FIND_BY_GAME_TITLE = """
            SELECT key_id, game_key, game_id
            FROM keys
            WHERE game_id = (SELECT game_id
            FROM games
            WHERE title = ?
            )
            """;

    @Override
    public Key save(Key key) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, key.getKeyStr());
            preparedStatement.setObject(2, key.getGame_id());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                key.setId(generatedKeys.getObject(1, Integer.class));
            }
            return key;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Key findById(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_KEY_BY_ID)) {
            preparedStatement.setObject(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            Key key = null;
            if (resultSet.next()) {
                key = buildKeyEntity(resultSet);
            }
            return key;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Key> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_KEYS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Key> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildKeyEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Key> findAllByGameId(Integer game_id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_GAME_ID)) {
            preparedStatement.setObject(1, game_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Key> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildKeyEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Key> findAllByTitle(String title) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_GAME_TITLE)) {
            preparedStatement.setObject(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Key> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildKeyEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Key key) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setObject(1, key.getKeyStr());
            preparedStatement.setObject(2, key.getGame_id());
            preparedStatement.setObject(3, key.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setObject(1, id);
            int executeUpdate = preparedStatement.executeUpdate();
            return executeUpdate > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Key buildKeyEntity(ResultSet resultSet) throws SQLException {
        Key key = new Key();

        key.setId(resultSet.getInt("key_id"));
        key.setKeyStr(resultSet.getString("game_key"));
        key.setGame_id(resultSet.getInt("game_id"));
        return key;
    }
}
