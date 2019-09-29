package main.storage;

import main.exception.NotExistStorageException;
import main.model.Resume;
import main.sql.SqlHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public SqlHelper sqlHelper;
    private ResultSet rs;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.helper("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        String sql = "SELECT * FROM resume r WHERE r.uuid =?";
        return sqlHelper.helper(sql, ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            while (!rs.next()) {
                throw new NotExistStorageException(null);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void update(Resume r) {
        String sql = "UPDATE resume r SET full_name = ? WHERE uuid = ?";
        sqlHelper.helper(sql, ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(null);
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        String sql = "INSERT INTO resume (uuid, full_name) VALUES (?,?)";
        sqlHelper.helper(sql, ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        String sql = "DELETE FROM resume WHERE uuid = ?";
        sqlHelper.helper(sql, ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(null);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        String sql = "SELECT * FROM resume";
        List<Resume> resume = new ArrayList<>();
        sqlHelper.helper(sql, ps -> {
            rs = ps.executeQuery();
            while (rs.next()) {
                resume.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return resume;
        });
        return resume;
    }

    @Override
    public int size() {
        String sql = "SELECT count(*) FROM resume;";
        return sqlHelper.helper(sql, ps -> {
            rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }
}

