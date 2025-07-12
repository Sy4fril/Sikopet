/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package usersForm.Bendahara;

import DataAccessObject.LogAktivitasDAO;
import database.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import model.LogAktivitas;

/**
 *
 * @author HYPE AMD
 */
public class panelPengeluaranBendahara extends javax.swing.JPanel {

    private int idLingkungan;
    private int idPengguna;
    private LogAktivitasDAO logDAO;
    private Runnable dashboardRefreshCallback;
    private DefaultTableModel pengeluaranTableModel;

    // Konstruktor panel, inisialisasi komponen dan logika
    public panelPengeluaranBendahara() {
        initComponents();
        initLogic();
    }

    // Inisialisasi logika tabel dan event listener
    private void initLogic() {
        logDAO = new LogAktivitasDAO();

        String[] columnNames = {"ID", "Tanggal", "Jenis", "Sumber", "Nominal", "Keterangan"};
        pengeluaranTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblRiwayatPengeluaran.setModel(pengeluaranTableModel);
        // Set warna header tabel dengan custom renderer agar tidak diabaikan L&F
        javax.swing.table.JTableHeader header = tblRiwayatPengeluaran.getTableHeader();
        header.setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(new java.awt.Color(0, 51, 102));
                c.setForeground(java.awt.Color.WHITE);
                setHorizontalAlignment(CENTER);
                setFont(getFont().deriveFont(java.awt.Font.BOLD));
                return c;
            }
        });
        // Set semua isi tabel rata tengah
        javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        for (int i = 0; i < tblRiwayatPengeluaran.getColumnCount(); i++) {
            tblRiwayatPengeluaran.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        // Set custom renderer untuk kolom Nominal (override center jika perlu)
        tblRiwayatPengeluaran.getColumnModel().getColumn(4).setCellRenderer(new CurrencyIDRCellRenderer());
        tblRiwayatPengeluaran.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableColumn idColumn = tblRiwayatPengeluaran.getColumnModel().getColumn(0);
        idColumn.setMinWidth(0);
        idColumn.setMaxWidth(0);
        idColumn.setWidth(0);

        tblRiwayatPengeluaran.getColumnModel().getColumn(1).setPreferredWidth(120);
        tblRiwayatPengeluaran.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblRiwayatPengeluaran.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblRiwayatPengeluaran.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblRiwayatPengeluaran.getColumnModel().getColumn(5).setPreferredWidth(200);
        addEventListeners();
    }

    // Menambahkan event listener pada komponen input dan tabel
    private void addEventListeners() {
        nominalPengeluaran.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    tambahPengeluaran();
                }
            }
        });

        nominalPengeluaran.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                formatCurrencyInput();
            }
        });

        tblRiwayatPengeluaran.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblRiwayatPengeluaran.getSelectedRow() != -1) {
                populateFieldsFromTable();
            }
        });
    }

    // Mengisi field input dari baris yang dipilih di tabel
    private void populateFieldsFromTable() {
        int selectedRow = tblRiwayatPengeluaran.getSelectedRow();
        if (selectedRow != -1) {
            int modelRow = tblRiwayatPengeluaran.convertRowIndexToModel(selectedRow);
            
            jenisPengeluaran.setText(pengeluaranTableModel.getValueAt(modelRow, 2).toString());
            sumberPengeluaran.setText(pengeluaranTableModel.getValueAt(modelRow, 3).toString());
            
            int nominal = (int) pengeluaranTableModel.getValueAt(modelRow, 4);
            NumberFormat format = NumberFormat.getInstance(new Locale("id", "ID"));
            nominalPengeluaran.setText(format.format(nominal));
            
            Object keteranganObj = pengeluaranTableModel.getValueAt(modelRow, 5);
            keteranganPengeluaran.setText(keteranganObj != null ? keteranganObj.toString() : "");
        }
    }

    // Setter untuk idLingkungan dan refresh tabel
    public void setIdLingkungan(int idLingkungan) {
        this.idLingkungan = idLingkungan;
        refreshTabelPengeluaran();
    }

    // Setter untuk idPengguna
    public void setIdPengguna(int idPengguna) {
        this.idPengguna = idPengguna;
    }

    // Setter untuk callback refresh dashboard
    public void setDashboardRefreshCallback(Runnable callback) {
        this.dashboardRefreshCallback = callback;
    }

    // Memformat input nominal ke format mata uang
    private void formatCurrencyInput() {
        String text = nominalPengeluaran.getText().trim();
        if (!text.isEmpty()) {
            try {
                NumberFormat format = NumberFormat.getInstance(new Locale("id", "ID"));
                Number number = format.parse(text);
                int value = number.intValue();
                nominalPengeluaran.setText(format.format(value));
            } catch (Exception e) {
                // Abaikan
            }
        }
    }

    // Menambah data pengeluaran ke database dan mengajukan verifikasi
    private void tambahPengeluaran() {
        if (!validateIds()) return;

        try {
            String jenis = jenisPengeluaran.getText().trim();
            String tujuan = sumberPengeluaran.getText().trim();
            String keterangan = keteranganPengeluaran.getText().trim();
            String nominalStr = nominalPengeluaran.getText().trim();

            if (jenis.isEmpty() || tujuan.isEmpty() || nominalStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int nominal = parseNominal(nominalStr);
            if (nominal <= 0) return;

            Connection conn = Koneksi.getConnection();
            conn.setAutoCommit(false);
            try {
                // 1. Insert ke tabel transaksi
                String sqlTransaksi = "INSERT INTO transaksi (id_lingkungan, tipe, jenis, jumlah, tanggal, sumber, keterangan, id_dibuat_oleh) VALUES (?, 'pengeluaran', ?, ?, NOW(), ?, ?, ?)";
                PreparedStatement psTransaksi = conn.prepareStatement(sqlTransaksi, PreparedStatement.RETURN_GENERATED_KEYS);
                psTransaksi.setInt(1, idLingkungan);
                psTransaksi.setString(2, jenis);
                psTransaksi.setInt(3, nominal);
                psTransaksi.setString(4, tujuan);
                psTransaksi.setString(5, keterangan);
                psTransaksi.setInt(6, idPengguna);
                int resultTransaksi = psTransaksi.executeUpdate();

                if (resultTransaksi > 0) {
                    // 2. Ambil id transaksi yang baru saja di-insert
                    ResultSet rs = psTransaksi.getGeneratedKeys();
                    int idTransaksiBaru = -1;
                    if (rs.next()) {
                        idTransaksiBaru = rs.getInt(1);
                    }
                    rs.close();
                    psTransaksi.close();

                    if (idTransaksiBaru > 0) {
                        // 3. Insert ke tabel verifikasi_transaksi
                        String sqlVerif = "INSERT INTO verifikasi_transaksi (id_transaksi, status, alasan) VALUES (?, 'pending', ?)";
                        PreparedStatement psVerif = conn.prepareStatement(sqlVerif);
                        psVerif.setInt(1, idTransaksiBaru);
                        psVerif.setString(2, "Menunggu persetujuan ketua");
                        psVerif.executeUpdate();
                        psVerif.close();

                        conn.commit();
                        logAktivitas("Mengajukan pengeluaran: " + jenis + " sebesar " + formatCurrency(nominal) + " (menunggu persetujuan ketua)");
                        JOptionPane.showMessageDialog(this, "Pengeluaran berhasil diajukan. Menunggu persetujuan ketua.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        clearForm();
                        refreshTabelPengeluaran();
                        if (dashboardRefreshCallback != null) dashboardRefreshCallback.run();
                        return;
                    }
                }
                conn.rollback();
                JOptionPane.showMessageDialog(this, "Gagal mengajukan pengeluaran!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                conn.rollback();
                JOptionPane.showMessageDialog(this, "Error database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (conn != null && !conn.isClosed()) {
                        conn.setAutoCommit(true);
                        conn.close();
                    }
                } catch (Exception ex) { /* ignore */ }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Mengedit data pengeluaran yang dipilih
    private void editPengeluaran() {
        int selectedRow = tblRiwayatPengeluaran.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diubah.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validateIds()) return;

        try {
            int modelRow = tblRiwayatPengeluaran.convertRowIndexToModel(selectedRow);
            int idTransaksi = (int) pengeluaranTableModel.getValueAt(modelRow, 0);

            String jenis = jenisPengeluaran.getText().trim();
            String tujuan = sumberPengeluaran.getText().trim();
            String keterangan = keteranganPengeluaran.getText().trim();
            String nominalStr = nominalPengeluaran.getText().trim();

            if (jenis.isEmpty() || tujuan.isEmpty() || nominalStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int nominal = parseNominal(nominalStr);
            if (nominal <= 0) return;

            Connection conn = Koneksi.getConnection();
            String sql = "UPDATE transaksi SET jenis=?, jumlah=?, sumber=?, keterangan=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, jenis);
            ps.setInt(2, nominal);
            ps.setString(3, tujuan);
            ps.setString(4, keterangan);
            ps.setInt(5, idTransaksi);

            int result = ps.executeUpdate();
            if (result > 0) {
                Koneksi.commit();
                logAktivitas("Mengubah data pengeluaran ID: " + idTransaksi);
                JOptionPane.showMessageDialog(this, "Data pengeluaran berhasil diubah!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                refreshTabelPengeluaran();
                if (dashboardRefreshCallback != null) dashboardRefreshCallback.run();
            } else {
                Koneksi.rollback();
                JOptionPane.showMessageDialog(this, "Gagal mengubah data pengeluaran!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            ps.close();
        } catch (SQLException e) {
            Koneksi.rollback();
            JOptionPane.showMessageDialog(this, "Error database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Menghapus data pengeluaran yang dipilih
    private void hapusPengeluaran() {
        int selectedRow = tblRiwayatPengeluaran.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        if (!validateIds()) return;

        try {
            int modelRow = tblRiwayatPengeluaran.convertRowIndexToModel(selectedRow);
            int idTransaksi = (int) pengeluaranTableModel.getValueAt(modelRow, 0);

            Connection conn = Koneksi.getConnection();
            String sql = "DELETE FROM transaksi WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idTransaksi);

            int result = ps.executeUpdate();
            if (result > 0) {
                Koneksi.commit();
                logAktivitas("Menghapus data pengeluaran ID: " + idTransaksi);
                JOptionPane.showMessageDialog(this, "Data pengeluaran berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                refreshTabelPengeluaran();
                if (dashboardRefreshCallback != null) dashboardRefreshCallback.run();
            } else {
                Koneksi.rollback();
                JOptionPane.showMessageDialog(this, "Gagal menghapus data pengeluaran!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            ps.close();
        } catch (SQLException e) {
            Koneksi.rollback();
            JOptionPane.showMessageDialog(this, "Error database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Parsing string nominal ke integer, validasi > 0
    private int parseNominal(String nominalStr) {
        try {
            NumberFormat format = NumberFormat.getInstance(new Locale("id", "ID"));
            Number number = format.parse(nominalStr);
            int nominal = number.intValue();
            if (nominal <= 0) {
                JOptionPane.showMessageDialog(this, "Nominal harus lebih dari 0!", "Error", JOptionPane.ERROR_MESSAGE);
                return -1;
            }
            return nominal;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Format nominal tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }
    
    // Mencatat aktivitas ke log
    private void logAktivitas(String aktivitas) {
        LogAktivitas log = new LogAktivitas();
        log.setIdPengguna(idPengguna);
        log.setAktivitas(aktivitas);
        logDAO.insertLogAktivitas(log);
    }
    
    // Memformat angka ke format mata uang Indonesia
    private String formatCurrency(int amount) {
        return NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(amount);
    }
    
    // Validasi idLingkungan dan idPengguna
    private boolean validateIds() {
        if (idLingkungan <= 0 || idPengguna <= 0) {
            JOptionPane.showMessageDialog(this, "ID Lingkungan/Pengguna tidak valid! Silakan login ulang.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    // Mengosongkan form input
    private void clearForm() {
        jenisPengeluaran.setText("");
        sumberPengeluaran.setText("");
        nominalPengeluaran.setText("");
        keteranganPengeluaran.setText("");
        tblRiwayatPengeluaran.clearSelection();
        jenisPengeluaran.requestFocus();
    }
    
    // Mengambil dan menampilkan data pengeluaran ke tabel
    public void refreshTabelPengeluaran() {
        if (idLingkungan <= 0) return;
        pengeluaranTableModel.setRowCount(0);

        try {
            Connection conn = Koneksi.getConnection();
            // Tampilkan hanya pengeluaran yang sudah disetujui (sudah diverifikasi oleh ketua)
            String sql = "SELECT t.id, t.tanggal, t.jenis, t.sumber, t.jumlah, t.keterangan FROM transaksi t " +
                         "JOIN verifikasi_transaksi v ON t.id = v.id_transaksi " +
                         "WHERE t.id_lingkungan = ? AND t.tipe = 'pengeluaran' AND v.status = 'disetujui' " +
                         "ORDER BY t.tanggal DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idLingkungan);
            ResultSet rs = ps.executeQuery();

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            while (rs.next()) {
                pengeluaranTableModel.addRow(new Object[]{
                    rs.getInt("id"),
                    sdf.format(rs.getTimestamp("tanggal")),
                    rs.getString("jenis"),
                    rs.getString("sumber"),
                    rs.getInt("jumlah"),
                    rs.getString("keterangan")
                });
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error memuat data pengeluaran: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jenisPengeluaran = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        sumberPengeluaran = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        nominalPengeluaran = new javax.swing.JTextField();
        btnTambahPengeluaran = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        keteranganPengeluaran = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRiwayatPengeluaran = new javax.swing.JTable();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Pengeluaran");

        jLabel2.setText("Bendahara /");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Pengeluaran");

        jPanel1.setBackground(new java.awt.Color(245, 245, 245));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Jenis Pengeluaran");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Sumber");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Nominal");

        btnTambahPengeluaran.setBackground(new java.awt.Color(255, 0, 0));
        btnTambahPengeluaran.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTambahPengeluaran.setText("TAMBAH PENGELUARAN");
        btnTambahPengeluaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahPengeluaranActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Keterangan");

        tblRiwayatPengeluaran.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblRiwayatPengeluaran);

        btnEdit.setBackground(new java.awt.Color(255, 153, 0));
        btnEdit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEdit.setText("EDIT");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnHapus.setBackground(new java.awt.Color(255, 0, 0));
        btnHapus.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHapus.setText("HAPUS");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(0, 204, 204));
        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnClear.setText("BERSIHKAN");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jenisPengeluaran)
                    .addComponent(sumberPengeluaran)
                    .addComponent(nominalPengeluaran)
                    .addComponent(keteranganPengeluaran)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnTambahPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jenisPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sumberPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nominalPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(keteranganPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTambahPengeluaran)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 508, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addGap(22, 22, 22))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Event handler tombol tambah pengeluaran
    private void btnTambahPengeluaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahPengeluaranActionPerformed
        tambahPengeluaran();
    }//GEN-LAST:event_btnTambahPengeluaranActionPerformed

    // Event handler tombol edit pengeluaran
    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editPengeluaran();
    }//GEN-LAST:event_btnEditActionPerformed

    // Event handler tombol hapus pengeluaran
    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        hapusPengeluaran();
    }//GEN-LAST:event_btnHapusActionPerformed

    // Event handler tombol clear form
    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearForm();
    }//GEN-LAST:event_btnClearActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnTambahPengeluaran;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jenisPengeluaran;
    private javax.swing.JTextField keteranganPengeluaran;
    private javax.swing.JTextField nominalPengeluaran;
    private javax.swing.JTextField sumberPengeluaran;
    private javax.swing.JTable tblRiwayatPengeluaran;
    // End of variables declaration//GEN-END:variables

    // Renderer khusus untuk kolom nominal agar tampil format IDR
    private static class CurrencyIDRCellRenderer extends javax.swing.table.DefaultTableCellRenderer {
        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (value instanceof Number) {
                java.text.NumberFormat format = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("id", "ID"));
                setText(format.format(((Number) value).intValue()));
            } else {
                setText(value != null ? value.toString() : "");
            }
            c.setForeground(java.awt.Color.BLACK);
            return c;
        }
    }
}
