package dao;

import entities.Promocode;
import exceptions.DataBaseException;
import util.ConnectionManager;
import org.apache.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PromocodeDao implements Dao<Integer, Promocode> {
    private static final ConnectionManager connectionManager = new ConnectionManager();
    private static final Logger logger = Logger.getLogger(PromocodeDao.class);
    private static final String FIND_ALL_PROMOCODES = """
            SELECT promocode_id, promocode, discount
            FROM promocodes
            """;
    private static final String SAVE_SQL = """
            INSERT INTO promocodes(promocode, discount)
            VALUES (?, ?)
            """;

    private static final String FIND_PROMOCODE_BY_ID = """
            SELECT promocode_id, promocode, discount
            FROM promocodes
            WHERE promocode_id = ?
            """;

    private static final String DELETE_SQL = """
            DELETE FROM promocodes
            WHERE promocode_id = ?
            """;
    private static final String UPDATE_SQL = """
            UPDATE promocodes
            SET promocode = ?, discount = ?
            WHERE promocode_id = ?
            """;

    private static final String FIND_BY_PROMOCODE = """
            SELECT promocode_id, promocode, discount
            FROM promocodes
            WHERE promocode = ?
            """;


    @Override
    public Promocode save(Promocode promocode) throws DataBaseException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, promocode.getPromoStr());
            preparedStatement.setObject(2, promocode.getDiscount());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                promocode.setId(generatedKeys.getObject(1, Integer.class));
            }
            return promocode;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DataBaseException(e.getMessage());
        }
    }

    @Override
    public Promocode findById(Integer id) throws DataBaseException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_PROMOCODE_BY_ID)) {
            preparedStatement.setObject(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            Promocode promocode = null;
            if (resultSet.next()) {
                promocode = buildPromocodeEntity(resultSet);
            }
            return promocode;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DataBaseException(e.getMessage());
        }
    }

    @Override
    public List<Promocode> findAll() throws DataBaseException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PROMOCODES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Promocode> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildPromocodeEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DataBaseException(e.getMessage());
        }
    }

    @Override
    public void update(Promocode promocode) throws DataBaseException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setObject(1, promocode.getPromoStr());
            preparedStatement.setObject(2, promocode.getDiscount());
            preparedStatement.setObject(3, promocode.getId());
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

    public Promocode findByPromocode(String promoStr) throws DataBaseException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_PROMOCODE)) {
            preparedStatement.setObject(1, promoStr);

            ResultSet resultSet = preparedStatement.executeQuery();
            Promocode promocode = null;
            if (resultSet.next()) {
                promocode = buildPromocodeEntity(resultSet);
            }
            return promocode;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DataBaseException(e.getMessage());
        }
    }

    private Promocode buildPromocodeEntity(ResultSet resultSet) throws SQLException {
        Promocode promocode = new Promocode();

        promocode.setId(resultSet.getInt("promocode_id"));
        promocode.setPromoStr(resultSet.getString("promocode"));
        promocode.setDiscount(resultSet.getDouble("discount"));

        return promocode;
    }
}
