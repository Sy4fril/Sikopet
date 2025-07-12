/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdminTools;

import database.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class AdminAuth {
    private static final Logger LOGGER = Logger.getLogger(AdminAuth.class.getName());
    
    /**
     * Memvalidasi input untuk mencegah SQL injection
     * @param input string yang akan divalidasi
     * @return true jika valid, false jika tidak valid
     */
    private static boolean validateInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        
        // Cek karakter berbahaya untuk SQL injection (tidak termasuk karakter email yang valid)
        String dangerousChars = "';\"\\/*+(){}[]|&^%$#!~`";
        for (char c : dangerousChars.toCharArray()) {
            if (input.contains(String.valueOf(c))) {
                LOGGER.warning("Input mengandung karakter berbahaya: " + c);
                return false;
            }
        }
        
        // Cek panjang input (maksimal 100 karakter)
        if (input.length() > 100) {
            LOGGER.warning("Input terlalu panjang: " + input.length());
            return false;
        }
        
        return true;
    }
    
    /**
     * Memvalidasi master key admin
     * @param masterKey master key yang dimasukkan
     * @return true jika valid, false jika tidak valid
     */
    public static boolean validateMasterKey(String masterKey) {
        // Validasi input terlebih dahulu
        if (!validateInput(masterKey)) {
            LOGGER.warning("Master key tidak valid atau mengandung karakter berbahaya");
            return false;
        }
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = Koneksi.getConnection();
            
            // Query untuk mencari admin dengan master key yang sesuai
            String query = "SELECT nama_admin FROM admin WHERE master_key = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, masterKey.trim());
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                String adminName = rs.getString("nama_admin");
                LOGGER.info("Master key valid untuk admin: " + adminName);
                return true;
            } else {
                LOGGER.warning("Master key tidak valid");
                return false;
            }
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saat validasi master key", e);
            JOptionPane.showMessageDialog(null, 
                "Error Koneksi database: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            Koneksi.closeResources(ps, rs);
        }
    }
    
    /**
     * Menampilkan dialog untuk input master key
     * @return master key yang dimasukkan, atau null jika dibatalkan
     */
    public static String showMasterKeyDialog() {
        String masterKey = JOptionPane.showInputDialog(null,
            "Masukkan Master Key Admin:",
            "Autentikasi Admin",
            JOptionPane.PLAIN_MESSAGE);
        
        return masterKey;
    }
    
    /**
     * Proses autentikasi admin lengkap
     * @return true jika berhasil login, false jika gagal
     */
    public static boolean authenticateAdmin() {
        int maxAttempts = 3;
        int attempts = 0;
        
        while (attempts < maxAttempts) {
            String masterKey = showMasterKeyDialog();
            
            // Jika user membatalkan dialog
            if (masterKey == null) {
                JOptionPane.showMessageDialog(null,
                    "Autentikasi dibatalkan",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
            
            // Validasi master key
            if (validateMasterKey(masterKey)) {
                JOptionPane.showMessageDialog(null,
                    "Autentikasi berhasil! Selamat datang Admin.",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                attempts++;
                int remainingAttempts = maxAttempts - attempts;
                
                if (remainingAttempts > 0) {
                    JOptionPane.showMessageDialog(null,
                        "Master Key salah! Sisa percobaan: " + remainingAttempts,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                        "Master Key salah! Maksimal percobaan tercapai.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
        return false;
    }
    
    /**
     * Mendapatkan informasi admin berdasarkan master key
     * @param masterKey master key admin
     * @return nama admin atau null jika tidak ditemukan
     */
    public static String getAdminName(String masterKey) {
        // Validasi input terlebih dahulu
        if (!validateInput(masterKey)) {
            return null;
        }
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = Koneksi.getConnection();
            
            String query = "SELECT nama_admin FROM admin WHERE master_key = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, masterKey.trim());
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getString("nama_admin");
            }
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saat mengambil nama admin", e);
        } finally {
            Koneksi.closeResources(ps, rs);
        }
        
        return null;
    }
    
    /**
     * Memvalidasi kombinasi nama_admin dan master_key
     * @param adminName nama admin
     * @param masterKey master key admin
     * @return true jika valid, false jika tidak valid
     */
    public static boolean validateAdminCredentials(String adminName, String masterKey) {
        // Validasi input terlebih dahulu
        if (!validateInput(adminName) || !validateInput(masterKey)) {
            LOGGER.warning("Input admin credentials tidak valid");
            return false;
        }
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = Koneksi.getConnection();
            
            String query = "SELECT nama_admin FROM admin WHERE nama_admin = ? AND master_key = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, adminName.trim());
            ps.setString(2, masterKey.trim());
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                LOGGER.info("Credentials valid untuk admin: " + adminName);
                return true;
            } else {
                LOGGER.warning("Credentials tidak valid untuk admin: " + adminName);
                return false;
            }
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saat validasi credentials", e);
            return false;
        } finally {
            Koneksi.closeResources(ps, rs);
        }
    }
}
