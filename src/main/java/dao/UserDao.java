package dao;

import entity.Purchase;
import entity.Role;
import entity.User;
import exceptions.LoginAlreadyRegisteredException;
import util.ConnectionManager;
import util.TimeUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserDao implements Dao<Integer, User> {

    private final String LOGIN_ALREADY_REGISTERED_MSG = "Пользователь с таким логином уже зарегистрирован!";
    //private static final UserDao INSTANCE = new UserDao();

    private static final String FIND_BY_LOGIN_SQL = """
            SELECT user_id, login, user_password, balance, card_number, user_role
            FROM users
            WHERE login = ?
            """;

    public static final String SAVE_SQL = """
            INSERT INTO users(login, user_password, user_role, card_number) 
            VALUES (?, ?, ?, ?);
            """;

    public static final String FIND_BY_EMAIL_AND_PASSWORD = """
            SELECT user_id, login, user_password, role, balance, card_number
            FROM users
            WHERE login = ? AND user_password = ?;
            """;

    private static final String FIND_BY_PASSWORD = """
            SELECT user_id, login, user_password, role, balance, card_number
            FROM users
            WHERE password = ?
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT user_id, login, user_password, role, balance, card_number
            FROM users
            WHERE user_id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT user_id, login, user_password, role, balance, card_number
            FROM users
            """;

    private static final String DELETE_USER_BY_ID = """
            DELETE FROM users
            WHERE user_id = ?
            """;

    private static final String DELETE_USER_BY_LOGIN = """
            DELETE FROM users
            WHERE login = ?
            """;


    @Override
    public User save(User user) throws LoginAlreadyRegisteredException {

        User userCheck = findByLogin(user.getLogin());
        if (userCheck != null) {
            throw new LoginAlreadyRegisteredException(LOGIN_ALREADY_REGISTERED_MSG);
        }
        User user1 = null;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, user.getLogin());
            preparedStatement.setObject(2, Objects.hash(user.getPassword()));
            preparedStatement.setObject(3, String.valueOf(user.getRole()));
            preparedStatement.setObject(4, user.getCardNumber());
            preparedStatement.executeUpdate();
            final ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getObject(1, Integer.class));
            }
            return user1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findById(Integer id) {
        User user = null;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = buildUserEntity(resultSet);
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildUserEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {

    }

    public User findByLogin(String login) {
        User user = null;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_LOGIN_SQL)) {

            preparedStatement.setObject(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = buildUserEntity(resultSet);
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findByPassword(String password) {
        User user = null;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_PASSWORD)) {

            preparedStatement.setObject(1, Objects.hash(password));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = buildUserEntity(resultSet);
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean deleteById(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_BY_ID)) {
            preparedStatement.setObject(1, id);
            int executeUpdate = preparedStatement.executeUpdate();
            return executeUpdate > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteByLogin(String login) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_BY_LOGIN)) {
            preparedStatement.setObject(1, login);
            int executeUpdate = preparedStatement.executeUpdate();
            return executeUpdate > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findByLoginAndPassword(String login, String password) {
        User user = null;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_EMAIL_AND_PASSWORD)) {

            preparedStatement.setObject(1, login);
            preparedStatement.setObject(2, Objects.hash(password));

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = buildUserEntity(resultSet);
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User buildUserEntity(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setId(resultSet.getInt("user_id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getInt("user_password"));
        user.setBalance(resultSet.getDouble("balance"));
        user.setCardNumber(resultSet.getString("card_number"));
        user.setRole(Role.find(resultSet.getString("user_role")).orElse(null));

        return user;

    }
}