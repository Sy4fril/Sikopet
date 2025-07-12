package DataAccessObject;

import database.Koneksi;
import model.LogAktivitas;
import java.sql.*;
import java.util.*;

public class LogAktivitasDAO {
    // Mengambil seluruh data log aktivitas dari database
    public List<LogAktivitas> getAllLogAktivitas() {
        List<LogAktivitas> list = new ArrayList<>();
        String sql = "SELECT * FROM log_aktivitas ORDER BY waktu DESC";
        try (Connection conn = Koneksi.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                LogAktivitas log = new LogAktivitas(
                    rs.getInt("id"),
                    rs.getObject("id_pengguna") == null ? null : rs.getInt("id_pengguna"),
                    rs.getString("aktivitas"),
                    rs.getTimestamp("waktu")
                );
                list.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Menyisipkan data log aktivitas baru ke database
    public boolean insertLogAktivitas(model.LogAktivitas log) {
        String sql = "INSERT INTO log_aktivitas (id_pengguna, aktivitas, waktu) VALUES (?, ?, CURRENT_TIMESTAMP)";
        try (Connection conn = database.Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (log.getIdPengguna() != null) {
                ps.setInt(1, log.getIdPengguna());
            } else {
                ps.setNull(1, java.sql.Types.INTEGER);
            }
            ps.setString(2, log.getAktivitas());
            int result = ps.executeUpdate();
            if (result > 0) {
                database.Koneksi.commit();
                return true;
            }
        } catch (SQLException e) {
            database.Koneksi.rollback();
            e.printStackTrace();
        }
        return false;
    }

    // Mengambil daftar log aktivitas berdasarkan id pengguna tertentu
    public List<LogAktivitas> getLogByPenggunaId(int idPengguna) {
        List<LogAktivitas> list = new ArrayList<>();
        String sql = "SELECT * FROM log_aktivitas WHERE id_pengguna = ? ORDER BY waktu DESC";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPengguna);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LogAktivitas log = new LogAktivitas(
                        rs.getInt("id"),
                        rs.getObject("id_pengguna") == null ? null : rs.getInt("id_pengguna"),
                        rs.getString("aktivitas"),
                        rs.getTimestamp("waktu")
                    );
                    list.add(log);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
} 