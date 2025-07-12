package DataAccessObject;

import database.Koneksi;
import model.Lingkungan;
import java.sql.*;
import java.util.*;

public class LingkunganDAO {
    // Mengambil semua data lingkungan yang statusnya aktif
    public List<Lingkungan> getAllLingkungan() {
        List<Lingkungan> list = new ArrayList<>();
        String sql = "SELECT * FROM lingkungan WHERE status = 'aktif'";
        try (Connection conn = Koneksi.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Lingkungan l = new Lingkungan(
                    rs.getInt("id"),
                    rs.getString("rt"),
                    rs.getString("rw"),
                    rs.getString("nama_ketua"),
                    rs.getString("alamat"),
                    rs.getString("nama_daerah"),
                    rs.getString("status"),
                    rs.getTimestamp("dibuat_pada")
                );
                list.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Menyisipkan data lingkungan baru ke database
    public boolean insertLingkungan(Lingkungan l) {
        String sql = "INSERT INTO lingkungan (rt, rw, nama_ketua, alamat, nama_daerah, status) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Koneksi.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, l.getRt());
            ps.setString(2, l.getRw());
            ps.setString(3, l.getNamaKetua());
            ps.setString(4, l.getAlamat());
            ps.setString(5, l.getNamaDaerah());
            ps.setString(6, l.getStatus());
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

    // Memperbarui data lingkungan di database
    public boolean updateLingkungan(Lingkungan l) {
        String sql = "UPDATE lingkungan SET rt=?, rw=?, nama_ketua=?, alamat=?, nama_daerah=? WHERE id=?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Koneksi.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, l.getRt());
            ps.setString(2, l.getRw());
            ps.setString(3, l.getNamaKetua());
            ps.setString(4, l.getAlamat());
            ps.setString(5, l.getNamaDaerah());
            ps.setInt(6, l.getId());
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

    // Menghapus data lingkungan berdasarkan ID
    public boolean deleteLingkungan(int id) {
        String sql = "DELETE FROM lingkungan WHERE id=?";
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

    // Memperbarui status lingkungan (aktif/nonaktif) berdasarkan ID
    public boolean updateStatusLingkungan(int id, String status) {
        String sql = "UPDATE lingkungan SET status=? WHERE id=?";
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

    // Mengambil semua data lingkungan, bisa semua status atau hanya yang aktif
    public List<Lingkungan> getAllLingkungan(boolean allStatus) {
        List<Lingkungan> list = new ArrayList<>();
        String sql = allStatus ? "SELECT * FROM lingkungan" : "SELECT * FROM lingkungan WHERE status = 'aktif'";
        try (Connection conn = Koneksi.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Lingkungan l = new Lingkungan(
                    rs.getInt("id"),
                    rs.getString("rt"),
                    rs.getString("rw"),
                    rs.getString("nama_ketua"),
                    rs.getString("alamat"),
                    rs.getString("nama_daerah"),
                    rs.getString("status"),
                    rs.getTimestamp("dibuat_pada")
                );
                list.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Mengambil data lingkungan berdasarkan ID dan status aktif
    public Lingkungan getLingkunganById(int id) {
        String sql = "SELECT * FROM lingkungan WHERE id = ? AND status = 'aktif'";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Lingkungan(
                        rs.getInt("id"),
                        rs.getString("rt"),
                        rs.getString("rw"),
                        rs.getString("nama_ketua"),
                        rs.getString("alamat"),
                        rs.getString("nama_daerah"),
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

    // Mengambil daftar lingkungan berdasarkan status tertentu
    public List<Lingkungan> getLingkunganByStatus(String status) {
        List<Lingkungan> list = new ArrayList<>();
        String sql = "SELECT * FROM lingkungan WHERE status = ?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Lingkungan l = new Lingkungan(
                        rs.getInt("id"),
                        rs.getString("rt"),
                        rs.getString("rw"),
                        rs.getString("nama_ketua"),
                        rs.getString("alamat"),
                        rs.getString("nama_daerah"),
                        rs.getString("status"),
                        rs.getTimestamp("dibuat_pada")
                    );
                    list.add(l);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
} 