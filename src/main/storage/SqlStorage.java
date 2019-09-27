package main.storage;

import main.exception.NotExistStorageException;
import main.exception.StorageException;
import main.model.Resume;
import main.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        String sql = "DELETE FROM resume";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException("Error is clear sql  ", e);
        }
    }

    @Override
    public Resume get(String uuid) {
        String sql = "SELECT * FROM resume r WHERE r.uuid =?";
        ResultSet rs = SqlHelper(null, sql, "get", uuid, 1);
        try {
            return new Resume(uuid, rs.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException("Error sql in get", e);
        }
    }

    @Override
    public void update(Resume r) {
        String sql = "UPDATE resume r SET uuid = ?, full_name = ? WHERE uuid = r.uuid";
        SqlHelper(r, sql, "update", null, 0);
    }

    @Override
    public void save(Resume r) {
        String sql = "INSERT INTO resume (uuid, full_name) VALUES (?,?)";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException("Error in sql save", e);
        }
    }

    @Override
    public void delete(String uuid) {
        String sql = "DELETE FROM resume WHERE uuid = ?, full_name = ?";
        SqlHelper(null, sql, "delete", uuid, 0);
    }

    @Override
    public List<Resume> getAllSorted() {
        String sql = "SELECT * FROM resume";
        ResultSet rs = SqlHelper(null, sql, "GetAll", null, 1);
        List<Resume> resume = new ArrayList<>();
        try {
            while (rs.next()) {
                resume.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
        } catch (SQLException e) {
            throw new StorageException("Error sql in getAll", e);
        }
        return resume;
    }

    @Override
    public int size() {
        String sql = "SELECT MAX(uuid) FROM resume;";
        ResultSet rs = SqlHelper(null, sql, "size", null, 1);
        try {
            return rs.getInt("uuid");
        } catch (
                SQLException e) {
            throw new StorageException("Error sql in size", e);
        }
    }

    private ResultSet SqlHelper(Resume r, String sql, String nameMethods, String uuid, int bool) {
        ResultSet rs = null;
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (bool == 0) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            } else if (bool == 1) {
                ps.setString(1, uuid);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
            } else {
                ps.execute();
            }
        } catch (SQLException e) {
            throw new StorageException("Error in sql " + nameMethods, e);
        }
        return rs;
    }
}

