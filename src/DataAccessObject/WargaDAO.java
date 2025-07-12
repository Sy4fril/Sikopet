package DataAccessObject;

import database.Koneksi;
import model.Warga;
import java.sql.*;

public class WargaDAO {
    // Mengambil data warga berdasarkan id pengguna dan status aktif
    public Warga getWargaByPenggunaId(int idPengguna) {
        String sql = "SELECT * FROM warga WHERE id_pengguna = ? AND status = 'aktif'";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPengguna);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Warga(
                        rs.getInt("id"),
                        rs.getInt("id_pengguna"),
                        rs.getInt("id_lingkungan"),
                        rs.getString("nama_warga"),
                        rs.getString("nik"),
                        rs.getString("no_hp"),
                        rs.getString("email"),
                        rs.getString("alamat"),
                        rs.getString("status"),
                        rs.getTimestamp("dibuat_pada")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Menyisipkan data warga baru ke database
    public boolean insertWarga(Warga w) {
        String sql = "INSERT INTO warga (id_pengguna, id_lingkungan, nama_warga, nik, no_hp, email, alamat, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, w.getIdPengguna());
            ps.setInt(2, w.getIdLingkungan());
            ps.setString(3, w.getNamaWarga());
            ps.setString(4, w.getNik());
            ps.setString(5, w.getNoHp());
            ps.setString(6, w.getEmail());
            ps.setString(7, w.getAlamat());
            ps.setString(8, w.getStatus());
            int result = ps.executeUpdate();
            if (result > 0) {
                Koneksi.commit();
                return true;
            }
        } catch (SQLException e) {
            Koneksi.rollback();
            e.printStackTrace();
        }
        return false;
    }

    // Memperbarui data warga di database
    public boolean updateWarga(Warga w) {
        String sql = "UPDATE warga SET id_pengguna=?, id_lingkungan=?, nama_warga=?, nik=?, no_hp=?, email=?, alamat=?, status=? WHERE id=?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, w.getIdPengguna());
            ps.setInt(2, w.getIdLingkungan());
            ps.setString(3, w.getNamaWarga());
            ps.setString(4, w.getNik());
            ps.setString(5, w.getNoHp());
            ps.setString(6, w.getEmail());
            ps.setString(7, w.getAlamat());
            ps.setString(8, w.getStatus());
            ps.setInt(9, w.getId());
            int result = ps.executeUpdate();
            if (result > 0) {
                Koneksi.commit();
                return true;
            }
        } catch (SQLException e) {
            Koneksi.rollback();
            e.printStackTrace();
        }
        return false;
    }

    // Menghapus data warga berdasarkan ID
    public boolean deleteWarga(int id) {
        String sql = "DELETE FROM warga WHERE id=?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            if (result > 0) {
                Koneksi.commit();
                return true;
            }
        } catch (SQLException e) {
            Koneksi.rollback();
            e.printStackTrace();
        }
        return false;
    }

    // Mengambil seluruh data warga berdasarkan id lingkungan dan status aktif
    public java.util.List<Warga> getAllWargaByLingkungan(int idLingkungan) {
        java.util.List<Warga> list = new java.util.ArrayList<>();
        String sql = "SELECT * FROM warga WHERE id_lingkungan = ? AND status = 'aktif'";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idLingkungan);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Warga w = new Warga(
                        rs.getInt("id"),
                        rs.getInt("id_pengguna"),
                        rs.getInt("id_lingkungan"),
                        rs.getString("nama_warga"),
                        rs.getString("nik"),
                        rs.getString("no_hp"),
                        rs.getString("email"),
                        rs.getString("alamat"),
                        rs.getString("status"),
                        rs.getTimestamp("dibuat_pada")
                    );
                    list.add(w);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
} 