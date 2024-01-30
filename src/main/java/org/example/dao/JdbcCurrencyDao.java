package org.example.dao;

import org.example.DatabaseConnectionManager;
import org.example.entity.Currency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JdbcCurrencyDao implements CurrencyDao {

    @Override
    public Optional<Currency> findById(Long id) {
        final String query = "SELECT * FROM Currencies WHERE id = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(getCurrency(resultSet));
            }
        }
        catch (SQLException e) {
            // TODO: handle exception
        }
        return Optional.empty();
    }

    @Override
    public List<Currency> findAll() {
        final String query = "SELECT * FROM Currencies";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            List<Currency> currencies = new ArrayList<>();

            while (resultSet.next()) {
                currencies.add(getCurrency(resultSet));
            }

            return currencies;
        }
        catch (SQLException e) {
            // TODO: handle exception
            return Collections.emptyList();
        }
    }

    @Override
    public Currency save(Currency entity) {
        final String query = "INSERT INTO Currencies (code, full_name, sign) VALUES (?, ?, ?) RETURNING *";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, entity.getCode());
            statement.setString(2, entity.getFullName());
            statement.setString(3, entity.getSign());

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                throw new SQLException();
            }

            return getCurrency(resultSet);
        }
        catch (SQLException e) {
            // TODO: handle exception
        }
        return null;
    }

    @Override
    public Optional<Currency> update(Currency entity) {
        final String query = "UPDATE Currencies SET (code, full_name, sign) = (?, ?, ?) WHERE id = ? RETURNING *";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, entity.getCode());
            statement.setString(2, entity.getFullName());
            statement.setString(3, entity.getSign());
            statement.setLong(4, entity.getId());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(getCurrency(resultSet));
            }
        }
        catch (SQLException e) {
            // TODO: handle exception
        }
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        final String query = "DELETE FROM Currencies WHERE id = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            // TODO: handle exception
        }
    }

    @Override
    public Optional<Currency> findByCode(String code) {
        final String query = "SELECT * FROM Currencies WHERE code = ?";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, code);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(getCurrency(resultSet));
            }
        }
        catch (SQLException e) {
            // TODO: handle exception
        }
        return Optional.empty();
    }

    private static Currency getCurrency(ResultSet resultSet) throws SQLException {
        return new Currency(
                resultSet.getLong("id"),
                resultSet.getString("code"),
                resultSet.getString("full_name"),
                resultSet.getString("sign")
        );
    }
}
