package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Properties pros = loadProperties();
                String url = pros.getProperty("dburl");
                conn = DriverManager.getConnection(url, pros);
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    private static Properties loadProperties() {
        try {
            Properties pros = new Properties();
            try (var input = DB.class.getClassLoader().getResourceAsStream("db.properties")) {
                if (input == null) {
                    throw new DbException("db.properties file not found");
                }
                pros.load(input);
            }
            return pros;
        } catch (IOException e) {
            throw new DbException("Error loading db.properties: " + e.getMessage());
        }
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResult(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}
