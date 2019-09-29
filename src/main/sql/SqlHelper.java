package main.sql;

import main.exception.ExistStorageException;
import main.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    private static ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public void helper(String sql) {
        helper(sql, PreparedStatement::execute);
    }

    public <T> T helper(String sql, ExecuteCode<T> lambda) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return lambda.execute(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException(null);
            } else throw new StorageException("Error in helper", e);
        }
    }

    public interface ExecuteCode<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }

}
