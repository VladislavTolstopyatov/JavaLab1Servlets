package dao;

import entities.Purchase;
import exceptions.DataBaseException;
import org.apache.log4j.Logger;
import util.ConnectionManager;
import util.TimeUtils;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDao implements Dao<Integer, Purchase> {
    private static final ConnectionManager connectionManager = new ConnectionManager();
    private static final Logger logger = Logger.getLogger(PurchaseDao.class);
    private static final String FIND_ALL_PURCHASES = """
            SELECT purchase_id, purchase_date, promocode_id, user_id, game_id, keyStr
            FROM purchases
            """;
    private static final String SAVE_SQL = """
            INSERT INTO purchases(purchase_date, promocode_id, user_id, game_id, keyStr)
            VALUES (?, ?, ?, ?, ?)
            """;

    private static final String FIND_PURCHASE_BY_ID = """
            SELECT purchase_id, purchase_date, promocode_id, user_id, game_id, keyStr
            FROM purchases
            WHERE purchase_id = ?
            """;

    private static final String DELETE_SQL = """
            DELETE FROM purchases
            WHERE purchase_id = ?
            """;
    private static final String UPDATE_SQL = """
            UPDATE purchases
            SET purchase_date = ?, promocode_id = ?
            WHERE purchase_id = ?
            """;

    private static final String FIND_BY_USER_ID = """
            SELECT purchase_id, purchase_date, promocode_id, user_id, game_id, keyStr
            FROM purchases
            WHERE user_id = ?
            """;

    private static final String FIND_BY_GAME_ID = """
            SELECT purchase_id, purchase_date, promocode_id, user_id, game_id, keyStr
            FROM purchases
            WHERE game_id = ?
            """;

    private static final String FIND_BY_PURCHASE_DATE = """
            SELECT purchase_id, purchase_date, promocode_id, user_id, game_id, keyStr
            FROM purchases
            WHERE purchase_date = ?
            """;


    @Override
    public Purchase save(Purchase purchase) throws DataBaseException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, purchase.getDateOfPurchase());
            preparedStatement.setObject(2, purchase.getPromocodeId());
            preparedStatement.setObject(3, purchase.getUserId());
            preparedStatement.setObject(4, purchase.getGameId());
            preparedStatement.setObject(5, purchase.getKeyStr());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                purchase.setId(generatedKeys.getObject(1, Integer.class));
            }
            return purchase;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DataBaseException(e.getMessage());
        }
    }

    @Override
    public Purchase findById(Integer id) throws DataBaseException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_PURCHASE_BY_ID)) {
            preparedStatement.setObject(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            Purchase purchase = null;
            if (resultSet.next()) {
                purchase = buildPurchaseEntity(resultSet);
            }
            return purchase;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DataBaseException(e.getMessage());
        }
    }

    @Override
    public List<Purchase> findAll() throws DataBaseException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PURCHASES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Purchase> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildPurchaseEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DataBaseException(e.getMessage());
        }
    }

    public List<Purchase> findAllByGameID(Integer id) throws DataBaseException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_GAME_ID)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Purchase> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildPurchaseEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DataBaseException(e.getMessage());
        }
    }

    public List<Purchase> findAllByUserID(Integer id) throws DataBaseException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_USER_ID)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Purchase> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildPurchaseEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    public List<Purchase> findAllByPurchaseDate(LocalDate localDate) throws DataBaseException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_PURCHASE_DATE)) {
            preparedStatement.setObject(1, localDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Purchase> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildPurchaseEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DataBaseException(e.getMessage());
        }
    }

    @Override
    public void update(Purchase purchase) throws DataBaseException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setObject(1, purchase.getDateOfPurchase());
            preparedStatement.setObject(2, purchase.getPromocodeId());
            preparedStatement.setObject(3, purchase.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DataBaseException(e.getMessage());
        }
    }

    @Override
    public boolean deleteById(Integer id) throws DataBaseException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setObject(1, id);
            int executeUpdate = preparedStatement.executeUpdate();
            return executeUpdate > 0;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DataBaseException(e.getMessage());
        }
    }

    private Purchase buildPurchaseEntity(ResultSet resultSet) throws SQLException {
        Purchase purchase = new Purchase();

        purchase.setId(resultSet.getInt("purchase_id"));
        purchase.setDateOfPurchase(TimeUtils.convertToLocalDateViaSqlDate(resultSet.getDate("purchase_date")));
        purchase.setPromocodeId(resultSet.getInt("promocode_id"));
        purchase.setUserId(resultSet.getInt("user_id"));
        purchase.setGameId(resultSet.getInt("game_id"));
        purchase.setKeyStr(resultSet.getString("keyStr"));

        return purchase;
    }
}
