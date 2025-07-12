package DataAccessObject;

import database.Koneksi;
import model.Pengguna;
import java.sql.*;
import java.util.*;

public class PenggunaDAO {
    // Mengambil seluruh data pengguna dari database
    public List<Pengguna> getAllPengguna() {
        List<Pengguna> list = new ArrayList<>();
        String sql = "SELECT * FROM pengguna";
        try (Connection conn = Koneksi.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Pengguna p = new Pengguna(
                    rs.getInt("id"),
                    rs.getString("nama_pengguna"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getString("email"),
                    rs.getString("status"),
                    rs.getInt("id_lingkungan"),
                    rs.getTimestamp("dibuat_pada")
                );
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Menyisipkan data pengguna baru ke database
    public boolean insertPengguna(Pengguna p) {
        String sql = "INSERT INTO pengguna (nama_pengguna, username, password, role, email, status, id_lingkungan) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Koneksi.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, p.getNamaPengguna());
            ps.setString(2, p.getUsername());
            ps.setString(3, p.getPassword());
            ps.setString(4, p.getRole());
            ps.setString(5, p.getEmail());
            ps.setString(6, p.getStatus());
            ps.setInt(7, p.getIdLingkungan());
            int result = ps.executeUpdate();
            if (result > 0) {
                Koneksi.commit();
                return true;
            }
        } catch (SQLException e) {
            Koneksi.rollback();
            e.printStackTrace();
        } finally {
            Koneksi.closeResources(ps, null);
        }
        return false;
    }

    // Memperbarui data pengguna di database
    public boolean updatePengguna(Pengguna p) {
        String sql = "UPDATE pengguna SET nama_pengguna=?, username=?, password=?, role=?, email=?, status=?, id_lingkungan=? WHERE id=?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Koneksi.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, p.getNamaPengguna());
            ps.setString(2, p.getUsername());
            ps.setString(3, p.getPassword());
            ps.setString(4, p.getRole());
            ps.setString(5, p.getEmail());
            ps.setString(6, p.getStatus());
            ps.setInt(7, p.getIdLingkungan());
            ps.setInt(8, p.getId());
            int result = ps.executeUpdate();
            if (result > 0) {
                Koneksi.commit();
                return true;
            }
        } catch (SQLException e) {
            Koneksi.rollback();
            e.printStackTrace();
        } finally {
            Koneksi.closeResources(ps, null);
        }
        return false;
    }

    // Menghapus data pengguna berdasarkan ID
    public boolean deletePengguna(int id) {
        String sql = "DELETE FROM pengguna WHERE id=?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Koneksi.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            if (result > 0) {
                Koneksi.commit();
                return true;
            }
        } catch (SQLException e) {
            Koneksi.rollback();
            e.printStackTrace();
        } finally {
            Koneksi.closeResources(ps, null);
        }
        return false;
    }

    // Memperbarui status pengguna (aktif/nonaktif) berdasarkan ID
    public boolean updateStatusPengguna(int id, String status) {
        String sql = "UPDATE pengguna SET status=? WHERE id=?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Koneksi.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, id);
            int result = ps.executeUpdate();
            if (result > 0) {
                Koneksi.commit();
                return true;
            }
        } catch (SQLException e) {
            Koneksi.rollback();
            e.printStackTrace();
        } finally {
            Koneksi.closeResources(ps, null);
        }
        return false;
    }

    // Menghitung jumlah seluruh pengguna di database
    public int getTotalPengguna() {
        String sql = "SELECT COUNT(*) FROM pengguna";
        try (Connection conn = Koneksi.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Menghitung jumlah pengguna yang statusnya aktif
    public int getTotalPenggunaAktif() {
        String sql = "SELECT COUNT(*) FROM pengguna WHERE status = 'aktif'";
        try (Connection conn = Koneksi.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Menghitung jumlah pengguna yang statusnya nonaktif
    public int getTotalPenggunaNonaktif() {
        String sql = "SELECT COUNT(*) FROM pengguna WHERE status = 'nonaktif'";
        try (Connection conn = Koneksi.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Melakukan login pengguna berdasarkan username/email dan password
    public Pengguna login(String usernameOrEmail, String password) {
        String sql = "SELECT * FROM pengguna WHERE (username = ? OR email = ?) AND password = ? AND status = 'aktif'";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usernameOrEmail);
            ps.setString(2, usernameOrEmail);
            ps.setString(3, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Pengguna(
                        rs.getInt("id"),
                        rs.getString("nama_pengguna"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("email"),
                        rs.getString("status"),
                        rs.getInt("id_lingkungan"),
                        rs.getTimestamp("dibuat_pada")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Mengambil ID terakhir yang diinsert ke database
    public int getLastInsertedId() {
        int id = -1;
        String sql = "SELECT LAST_INSERT_ID()";
        try (Connection conn = Koneksi.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
} 