package dao;

import entity.Promocode;
import entity.Purchase;
import util.ConnectionManager;
import util.TimeUtils;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PurchaseDao implements Dao<Integer, Purchase> {

    private static final String FIND_ALL_PURCHASES = """
            SELECT purchase_id, purchase_date, promocode_id, user_id, game_id
            FROM purchases
            """;
    private static final String SAVE_SQL = """
            INSERT INTO purchases(purchase_date, promocode_id, user_id, game_id)
            VALUES (?, ?, ?, ?)
            """;

    private static final String FIND_PURCHASE_BY_ID = """
            SELECT purchase_id, purchase_date, promocode_id, user_id, game_id
            FROM purchases
            WHERE purchase_id = ?
            """;

    private static final String DELETE_SQL = """
            DELETE FROM purchases
            WHERE purchase_id = ?
            """;
    private static final String UPDATE_SQL = """
            UPDATE purchases
            SET purchase_date = ?, promocode_id = ?, user_id = ?, game_id = ?
            WHERE purchase_id = ?
            """;

    private static final String FIND_BY_USER_ID = """
            SELECT purchase_id, purchase_date, promocode_id, user_id, game_id
            FROM purchases
            WHERE user_id = ?
            """;

    private static final String FIND_BY_GAME_ID = """
            SELECT purchase_id, purchase_date, promocode_id, user_id, game_id
            FROM purchases
            WHERE game_id = ?
            """;

    private static final String FIND_BY_PURCHASE_DATE = """
            SELECT purchase_id, purchase_date, promocode_id, user_id, game_id
            FROM purchases
            WHERE purchase_date = ?
            """;

    @Override
    public Purchase save(Purchase purchase) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, purchase.getDateOfPurchase());
            preparedStatement.setObject(2, purchase.getPromocode_id());
            preparedStatement.setObject(3, purchase.getUserId());
            preparedStatement.setObject(4, purchase.getGameId());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                purchase.setId(generatedKeys.getObject(1, Integer.class));
            }
            return purchase;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Purchase findById(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_PURCHASE_BY_ID)) {
            preparedStatement.setObject(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            Purchase purchase = null;
            if (resultSet.next()) {
                purchase = buildPurchaseEntity(resultSet);
            }
            return purchase;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Purchase> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PURCHASES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Purchase> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildPurchaseEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Purchase> findAllByGameID(Long id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_GAME_ID)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Purchase> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildPurchaseEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Purchase> findAllByUserID(Long id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_USER_ID)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Purchase> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildPurchaseEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Purchase> findAllByPurchaseDate(LocalDate localDate) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_PURCHASE_DATE)) {
            preparedStatement.setObject(1, localDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Purchase> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildPurchaseEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Purchase purchase) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setObject(1, purchase.getDateOfPurchase());
            preparedStatement.setObject(2, purchase.getPromocode_id());
            preparedStatement.setObject(3, purchase.getUserId());
            preparedStatement.setObject(4, purchase.getGameId());
            preparedStatement.setObject(5, purchase.getId());

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

    private Purchase buildPurchaseEntity(ResultSet resultSet) throws SQLException {
        Purchase purchase = new Purchase();

        purchase.setId(resultSet.getInt("purchase_id"));
        purchase.setDateOfPurchase(TimeUtils.convertToLocalDateViaSqlDate(resultSet.getDate("purchase_date")));
        purchase.setPromocode_id(resultSet.getInt("promocode_id"));
        purchase.setUserId(resultSet.getInt("user_id"));
        purchase.setGameId(resultSet.getInt("game_id"));

        return purchase;
    }
}
