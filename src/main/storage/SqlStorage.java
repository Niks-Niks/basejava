package main.storage;

import main.exception.NotExistStorageException;
import main.exception.StorageException;
import main.model.*;
import main.sql.SqlHelper;
import main.util.JsonParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    public SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.helper("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            Resume r;
            try (PreparedStatement ps = conn.prepareStatement("select * from resume where uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                r = new Resume(uuid, rs.getString("full_name"));
            }

            try (PreparedStatement ps = conn.prepareStatement("select * from contact where resume_uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContact(r, rs);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("select * from section where resume_uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(r, rs);
                }
            }


            return r;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                        ps.setString(1, r.getFullName());
                        ps.setString(2, r.getUuid());
                        if (ps.executeUpdate() == 0) {
                            throw new NotExistStorageException(null);
                        }
                    }
                    deleteContact(conn, r);
                    deleteSection(conn, r);
                    insertContact(conn, r);
                    insertSection(conn, r);
                    return null;
                }
        );
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, r.getUuid());
                        ps.setString(2, r.getFullName());

                        ps.execute();
                    }
                    insertContact(conn, r);
                    insertSection(conn, r);
                    return null;
                }
        );
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
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> map = new LinkedHashMap<>();

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    map.put(uuid, new Resume(uuid.trim(), rs.getString("full_name")));
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = map.get(rs.getString("resume_uuid"));
                    addContact(resume, rs);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section\n")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = map.get(rs.getString("resume_uuid"));
                    addSection(resume, rs);
                }
            }

            return new ArrayList<>(map.values());
        });
    }

    @Override
    public int size() {
        String sql = "SELECT count(*) FROM resume;";
        return sqlHelper.helper(sql, ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void addContact(Resume r, ResultSet rs) throws SQLException {
        if (rs.getString("value") != null) {
            r.addContact(ContactType.valueOf(rs.getString("type").trim()), rs.getString("value").trim());
        }
    }

    private void addSection(Resume r, ResultSet rs) throws SQLException {
        if (rs.getString("value") != null) {
            r.addSection(SectionType.valueOf(rs.getString("type").trim()), JsonParser.read(rs.getString("value").trim(), AbstractSection.class));
        }
    }

    private void insertContact(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSection(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> e : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                AbstractSection abstractSection = e.getValue();
                ps.setString(3, JsonParser.write(abstractSection, AbstractSection.class));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContact(Connection conn, Resume r) throws SQLException {
        String sql = "DELETE FROM contact WHERE resume_uuid = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    private void deleteSection(Connection conn, Resume r) throws SQLException {
        String sql = "DELETE FROM section WHERE resume_uuid = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    private String writeToSection(String sectionType, AbstractSection section) {
        switch (SectionType.valueOf(sectionType)) {
            case PERSONAL:
            case OBJECTIVE:
                return ((TextSection) section).getText();
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return String.join("\n", ((ListSection) section).getList());
            case EXPERIENCE:
            case EDUCATION:
                OrganizationSection orgS = (OrganizationSection) section;
                List<Organization> org = new ArrayList<>();
                for (Organization O : orgS.getList()) {
                    List<Organization.Place> place = new ArrayList<>();
                    String hp = O.getHomePage().getHomePage();
                    String url = O.getHomePage().getLink();
                    for (Organization.Place p : O.getList()) {
                        LocalDate start = p.getDateStart();
                        LocalDate end = p.getDateEnd();
                        String title = p.getTitle();
                        String des = p.getDescription();
                        place.add(new Organization.Place(start, end, title, des));
                    }
                    org.add(new Organization(new Organization.Link(hp, url), place));
                }
                return new OrganizationSection(org).toString();
            default:
                throw new StorageException("Error in writeToSection");
        }
    }

    private AbstractSection readFromSection(SectionType type, String text) {
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextSection(text);
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                ArrayList<String> list = new ArrayList<>();
                for (String q : text.split("\n")) {
                    list.add(q);
                }
                return new ListSection(list);
            case EDUCATION:
            case EXPERIENCE:
                String link = text.substring(text.indexOf("link->") + 6, text.indexOf("), ") + 1);
                String Link_title = link.substring(2, link.indexOf(","));
                String Link_url = link.substring(link.indexOf(",") + 2, link.length() - 2);
                ArrayList<Organization.Place> listPlace = new ArrayList<>();
                for (int i = 0; i < text.length(); i++) {
                    if (text.contains("Place")) {
                        text = text.substring(text.indexOf("Place"));
                        String Place_dateS = text.substring(text.indexOf("dateStart") + 10, text.indexOf(", "));
                        String Place_dateE = text.substring(text.indexOf("dateEnd") + 8, text.indexOf(", t"));
                        String Place_title = text.substring(text.indexOf("title=") + 7, text.indexOf(", des") - 1);
                        String Place_des = text.substring(text.indexOf("description=") + 13, text.indexOf("'}"));
                        listPlace.add(new Organization.Place(LocalDate.parse(Place_dateS), LocalDate.parse(Place_dateE), Place_title, Place_des));
                        text = text.substring(10);
                    }
                }
                return new OrganizationSection(new Organization(Link_url, Link_title, listPlace));
            default:
                throw new StorageException("Error in writeToSection");
        }
    }
}