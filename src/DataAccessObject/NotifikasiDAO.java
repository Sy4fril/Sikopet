package DataAccessObject;

import database.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NotifikasiDAO {
    // Menyisipkan data notifikasi baru ke database
    public static boolean insertNotifikasi(Integer idWarga, String judul, String pesan) {
        String sql = "INSERT INTO notifikasi (id_warga, judul, pesan) VALUES (?, ?, ?)";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (idWarga != null) {
                ps.setInt(1, idWarga);
            } else {
                ps.setNull(1, java.sql.Types.INTEGER);
            }
            ps.setString(2, judul);
            ps.setString(3, pesan);
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

    // Mengambil daftar notifikasi berdasarkan id warga tertentu
    public static java.util.List<String> getNotifikasiByIdWarga(int idWarga) {
        java.util.List<String> list = new java.util.ArrayList<>();
        String sql = "SELECT judul, pesan, waktu FROM notifikasi WHERE id_warga = ? ORDER BY waktu DESC";
        try (java.sql.Connection conn = Koneksi.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idWarga);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String judul = rs.getString("judul");
                    String pesan = rs.getString("pesan");
                    String waktu = rs.getString("waktu");
                    list.add("[" + waktu + "] " + judul + ": " + pesan);
                }
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
} 