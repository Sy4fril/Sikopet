package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Kelas untuk mengelola koneksi database MySQL
 * Menggunakan PreparedStatement untuk keamanan dari SQL Injection
 */
public class Koneksi {
    
    // Konfigurasi database
    private static final String URL = "jdbc:mysql://localhost:3306/sikopetdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    private static Connection connection = null;
    private static final Logger LOGGER = Logger.getLogger(Koneksi.class.getName());
    
    /**
     * Mendapatkan koneksi database
     * @return Connection object
     * @throws SQLException jika gagal koneksi
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Load driver MySQL
                Class.forName(DRIVER);
                
                // Buat koneksi
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                
                // Set auto commit false untuk transaksi
                connection.setAutoCommit(false);
                
                LOGGER.info("Koneksi database berhasil dibuat");
                
            } catch (ClassNotFoundException e) {
                LOGGER.log(Level.SEVERE, "Driver MySQL tidak ditemukan", e);
                throw new SQLException("Driver MySQL tidak ditemukan", e);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Gagal koneksi ke database", e);
                throw e;
            }
        }
        return connection;
    }
    
    /**
     * Menutup koneksi database
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                LOGGER.info("Koneksi database ditutup");
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Gagal menutup koneksi database", e);
            }
        }
    }
    
    /**
     * Menutup PreparedStatement dan ResultSet
     * @param ps PreparedStatement
     * @param rs ResultSet
     */
    public static void closeResources(PreparedStatement ps, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Gagal menutup ResultSet", e);
            }
        }
        
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Gagal menutup PreparedStatement", e);
            }
        }
    }
    
    /**
     * Commit transaksi
     */
    public static void commit() {
        if (connection != null) {
            try {
                connection.commit();
                LOGGER.info("Transaksi berhasil di-commit");
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Gagal commit transaksi", e);
            }
        }
    }
    
    /**
     * Rollback transaksi
     */
    public static void rollback() {
        if (connection != null) {
            try {
                connection.rollback();
                LOGGER.info("Transaksi di-rollback");
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Gagal rollback transaksi", e);
            }
        }
    }
    
    /**
     * Test koneksi database
     * @return true jika berhasil, false jika gagal
     */
    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            if (conn != null && !conn.isClosed()) {
                LOGGER.info("Test koneksi berhasil");
                return true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Test koneksi gagal", e);
        }
        return false;
    }
    
    /**
     * Mendapatkan informasi koneksi
     * @return String informasi koneksi
     */
    public static String getConnectionInfo() {
        return String.format("Database: %s, User: %s", URL, USERNAME);
    }
} 
