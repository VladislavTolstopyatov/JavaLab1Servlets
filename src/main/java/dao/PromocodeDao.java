package dao;

import entity.Key;
import entity.Promocode;
import util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PromocodeDao implements Dao<Integer, Promocode> {

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
    public Promocode save(Promocode promocode) {
        try (Connection connection = ConnectionManager.getConnection();
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public Promocode findById(Integer id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_PROMOCODE_BY_ID)) {
            preparedStatement.setObject(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            Promocode promocode = null;
            if (resultSet.next()) {
                promocode = buildPromocodeEntity(resultSet);
            }
            return promocode;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Promocode> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PROMOCODES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Promocode> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(buildPromocodeEntity(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Promocode promocode) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setObject(1, promocode.getPromoStr());
            preparedStatement.setObject(2, promocode.getDiscount());
            preparedStatement.setObject(3, promocode.getId());
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

    public Promocode findByPromocode(String promoStr) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_PROMOCODE)) {
            preparedStatement.setObject(1, promoStr);

            ResultSet resultSet = preparedStatement.executeQuery();
            Promocode promocode = null;
            if (resultSet.next()) {
                promocode = buildPromocodeEntity(resultSet);
            }
            return promocode;
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
