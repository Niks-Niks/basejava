package main;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConnectPostgresSQL {
    private static final String USER = "postgres";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/resumes";
    private static final String PASS = "Niks322";

    private static Connection conn = null;
    private static Statement ps = null;
    private static ResultSet rs = null;

    public static void main(String[] args) {
//        checkConnect();
    }

    private static void checkConnect() {
        System.out.println("Start to connect... ");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. " + e);
            return;
        }

        System.out.println("PostgreSQL JDBC Driver successfully connected");

//insert into resume (uuid, full_name) values ('121212','Name12')

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            ps = conn.createStatement();
            rs = ps.executeQuery("select * from resume");
            while (rs.next()) {
                System.out.print(rs.getString("uuid") + " ");
                System.out.print(rs.getString("full_name") + "\n");
            }
        } catch (SQLException e) {
            System.out.println("Connection failed. " + e);
            return;
        }
    }
}