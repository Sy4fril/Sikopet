package DataAccessObject;

import database.Koneksi;
import model.Admin;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object untuk entitas Admin
 * Menangani autentikasi master key dan operasi database untuk tabel admin
 */
public class AdminDAO {
    private static final Logger LOGGER = Logger.getLogger(AdminDAO.class.getName());
    
    // query SQL disediakan untuk menamnbahkan propert admin pada database
    private static final String INSERT_ADMIN =  // menambah admin baru
        "INSERT INTO admin (nama_admin, master_key) VALUES (?, ?)";
    
    private static final String SELECT_ALL_ADMIN = // mengambil semua data pada tabel admin tetapi dikembalikan dalam bentuk nama admin saja
        "SELECT id, nama_admin, master_key, dibuat_pada FROM admin ORDER BY nama_admin";
    
    private static final String SELECT_ADMIN_BY_ID =  // mengambil data admin menggunakkan id admin
        "SELECT id, nama_admin, master_key, dibuat_pada FROM admin WHERE id = ?";
    
    private static final String SELECT_ADMIN_BY_MASTER_KEY =  // mengambil data admin menggunkana master key
        "SELECT id, nama_admin, master_key, dibuat_pada FROM admin WHERE master_key = ?";
    
    private static final String UPDATE_ADMIN = // menguupdate data (nama dan masterkey) admin
        "UPDATE admin SET nama_admin = ?, master_key = ? WHERE id = ?";
    
    private static final String DELETE_ADMIN = // menghapus admin
        "DELETE FROM admin WHERE id = ?";
    
    private static final String COUNT_ADMIN = // menghitung berpa banyak admin
        "SELECT COUNT(*) FROM admin";
    
    private static final String CHECK_MASTER_KEY_EXISTS =  // memerika apakah master_key ada
        "SELECT COUNT(*) FROM admin WHERE master_key = ?";
    
    /**
     * Autentikasi admin menggunakan master key
     * @param masterKey master key yang akan divalidasi
     * @return Admin objek jika berhasil, null jika gagal
     */
    // Melakukan autentikasi admin berdasarkan master key
    public Admin authenticate(String masterKey) {
        if (masterKey == null || masterKey.trim().isEmpty()) {
            LOGGER.warning("Master key kosong");
            return null;
        }
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Connection conn = Koneksi.getConnection();
            ps = conn.prepareStatement(SELECT_ADMIN_BY_MASTER_KEY);
            ps.setString(1, masterKey.trim());
            rs = ps.executeQuery();
            
            if (rs.next()) {
                Admin admin = mapResultSetToAdmin(rs);
                LOGGER.info("Autentikasi berhasil untuk admin: " + admin.getNamaAdmin());
                return admin;
            } else {
                LOGGER.warning("Autentikasi gagal: master key tidak ditemukan");
            }
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Gagal autentikasi admin", e);
        } finally {
            Koneksi.closeResources(ps, rs);
        }
        return null;
    }
    
    /**
     * Menambah admin baru
     * @param admin objek Admin yang akan ditambahkan
     * @return true jika berhasil, false jika gagal
     */
    // Menyisipkan data admin baru ke database
    public boolean insert(Admin admin) {
        if (!admin.isValid()) {
            LOGGER.warning("Data admin tidak valid: " + admin.getValidationMessage());
            return false;
        }
        
        // Cek apakah master key sudah ada
        if (isMasterKeyExists(admin.getMasterKey())) {
            LOGGER.warning("Master key sudah ada: " + admin.getMasterKey());
            return false;
        }
        
        PreparedStatement ps = null;
        try {
            Connection conn = Koneksi.getConnection();
            ps = conn.prepareStatement(INSERT_ADMIN, Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, admin.getNamaAdmin());
            ps.setString(2, admin.getMasterKey());
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    admin.setId(rs.getInt(1));
                }
                Koneksi.commit();
                LOGGER.info("Admin berhasil ditambahkan: " + admin);
                return true;
            }
            
        } catch (SQLException e) {
            Koneksi.rollback();
            LOGGER.log(Level.SEVERE, "Gagal menambahkan admin", e);
        } finally {
            Koneksi.closeResources(ps, null);
        }
        return false;
    }
    
    /**
     * Mengambil semua data admin
     * @return List<Admin> daftar semua admin
     */
    // Mengambil seluruh data admin dari database
    public List<Admin> getAll() {
        List<Admin> listAdmin = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Connection conn = Koneksi.getConnection();
            ps = conn.prepareStatement(SELECT_ALL_ADMIN);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Admin admin = mapResultSetToAdmin(rs);
                listAdmin.add(admin);
            }
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Gagal mengambil data admin", e);
        } finally {
            Koneksi.closeResources(ps, rs);
        }
        return listAdmin;
    }
    
    /**
     * Mengambil data admin berdasarkan ID
     * @param id ID admin
     * @return Admin objek atau null jika tidak ditemukan
     */
    // Mengambil data admin berdasarkan ID
    public Admin getById(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Connection conn = Koneksi.getConnection();
            ps = conn.prepareStatement(SELECT_ADMIN_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToAdmin(rs);
            }
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Gagal mengambil admin dengan ID: " + id, e);
        } finally {
            Koneksi.closeResources(ps, rs);
        }
        return null;
    }
    
    /**
     * Mengupdate data admin
     * @param admin objek Admin yang akan diupdate
     * @return true jika berhasil, false jika gagal
     */
    // Memperbarui data admin di database
    public boolean update(Admin admin) {
        if (!admin.isValid()) {
            LOGGER.warning("Data admin tidak valid: " + admin.getValidationMessage());
            return false;
        }
        
        // Cek apakah master key sudah ada (kecuali untuk admin yang sedang diupdate)
        Admin existingAdmin = getById(admin.getId());
        if (existingAdmin != null && !existingAdmin.getMasterKey().equals(admin.getMasterKey())) {
            if (isMasterKeyExists(admin.getMasterKey())) {
                LOGGER.warning("Master key sudah ada: " + admin.getMasterKey());
                return false;
            }
        }
        
        PreparedStatement ps = null;
        try {
            Connection conn = Koneksi.getConnection();
            ps = conn.prepareStatement(UPDATE_ADMIN);
            
            ps.setString(1, admin.getNamaAdmin());
            ps.setString(2, admin.getMasterKey());
            ps.setInt(3, admin.getId());
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows > 0) {
                Koneksi.commit();
                LOGGER.info("Admin berhasil diupdate: " + admin);
                return true;
            }
            
        } catch (SQLException e) {
            Koneksi.rollback();
            LOGGER.log(Level.SEVERE, "Gagal mengupdate admin", e);
        } finally {
            Koneksi.closeResources(ps, null);
        }
        return false;
    }
    
    /**
     * Menghapus data admin
     * @param id ID admin yang akan dihapus
     * @return true jika berhasil, false jika gagal
     */
    // Menghapus data admin berdasarkan ID
    public boolean delete(int id) {
        PreparedStatement ps = null;
        try {
            Connection conn = Koneksi.getConnection();
            ps = conn.prepareStatement(DELETE_ADMIN);
            ps.setInt(1, id);
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows > 0) {
                Koneksi.commit();
                LOGGER.info("Admin berhasil dihapus dengan ID: " + id);
                return true;
            }
            
        } catch (SQLException e) {
            Koneksi.rollback();
            LOGGER.log(Level.SEVERE, "Gagal menghapus admin dengan ID: " + id, e);
        } finally {
            Koneksi.closeResources(ps, null);
        }
        return false;
    }
    
    /**
     * Mengecek apakah master key sudah ada
     * @param masterKey master key yang akan dicek
     * @return true jika sudah ada, false jika belum
     */
    // Mengecek keberadaan master key di database
    public boolean isMasterKeyExists(String masterKey) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Connection conn = Koneksi.getConnection();
            ps = conn.prepareStatement(CHECK_MASTER_KEY_EXISTS);
            ps.setString(1, masterKey);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Gagal mengecek keberadaan master key", e);
        } finally {
            Koneksi.closeResources(ps, rs);
        }
        return false;
    }
    
    /**
     * Menghitung total admin
     * @return jumlah total admin
     */
    // Menghitung jumlah seluruh admin di database
    public int getTotalCount() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Connection conn = Koneksi.getConnection();
            ps = conn.prepareStatement(COUNT_ADMIN);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Gagal menghitung total admin", e);
        } finally {
            Koneksi.closeResources(ps, rs);
        }
        return 0;
    }
    
    /**
     * Mapping ResultSet ke objek Admin
     * @param rs ResultSet dari query
     * @return objek Admin
     * @throws SQLException jika terjadi error
     */
    // Mengubah hasil query ResultSet menjadi objek Admin
    private Admin mapResultSetToAdmin(ResultSet rs) throws SQLException {
        Admin admin = new Admin();
        admin.setId(rs.getInt("id"));
        admin.setNamaAdmin(rs.getString("nama_admin"));
        admin.setMasterKey(rs.getString("master_key"));
        admin.setDibuatPada(rs.getTimestamp("dibuat_pada"));
        return admin;
    }
} 