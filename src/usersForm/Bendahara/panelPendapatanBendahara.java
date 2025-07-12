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
import java.util.Date;
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
public class panelPendapatanBendahara extends javax.swing.JPanel {

    private int idLingkungan;
    private int idPengguna;
    private LogAktivitasDAO logDAO;
    private Runnable dashboardRefreshCallback;
    private DefaultTableModel pendapatanTableModel;
    
    // Konstruktor panel, inisialisasi komponen dan logika
    public panelPendapatanBendahara() {
        initComponents();
        initLogic();
    }
    
    // Inisialisasi logika tabel dan event listener
    private void initLogic() {
        logDAO = new LogAktivitasDAO();

        String[] columnNames = {"ID", "Tanggal", "Jenis", "Sumber", "Nominal", "Keterangan"};
        pendapatanTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblRiwayatPendapatan.setModel(pendapatanTableModel);
        // Set warna header tabel dengan custom renderer agar tidak diabaikan L&F
        javax.swing.table.JTableHeader header = tblRiwayatPendapatan.getTableHeader();
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
        for (int i = 0; i < tblRiwayatPendapatan.getColumnCount(); i++) {
            tblRiwayatPendapatan.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        // Set warna header tabel
        // header.setBackground(new java.awt.Color(0, 51, 102)); // This line is now redundant due to custom renderer
        // header.setForeground(java.awt.Color.WHITE); // This line is now redundant due to custom renderer
        // header.setFont(header.getFont().deriveFont(java.awt.Font.BOLD)); // This line is now redundant due to custom renderer
        tblRiwayatPendapatan.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // menyembunyikan kolom id
        TableColumn idColumn = tblRiwayatPendapatan.getColumnModel().getColumn(0);
        idColumn.setMinWidth(0);
        idColumn.setMaxWidth(0);
        idColumn.setWidth(0);
        // mengatur lebar kolom
        tblRiwayatPendapatan.getColumnModel().getColumn(1).setPreferredWidth(120); // Tanggal
        tblRiwayatPendapatan.getColumnModel().getColumn(2).setPreferredWidth(100); // Jenis
        tblRiwayatPendapatan.getColumnModel().getColumn(3).setPreferredWidth(100); // Sumber
        tblRiwayatPendapatan.getColumnModel().getColumn(4).setPreferredWidth(100); // Nominal
        tblRiwayatPendapatan.getColumnModel().getColumn(5).setPreferredWidth(200); // Keterangan
        // Tambahkan renderer untuk kolom Nominal (index 4)
        tblRiwayatPendapatan.getColumnModel().getColumn(4).setCellRenderer(new CurrencyIDRCellRenderer());
        addEventListeners();
    }

    // Menambahkan event listener pada komponen input dan tabel
    private void addEventListeners() {
        nominalPendapatan.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    tambahPendapatan();
                }
            }
        });

        nominalPendapatan.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                formatCurrencyInput();
            }
        });

        tblRiwayatPendapatan.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblRiwayatPendapatan.getSelectedRow() != -1) {
                populateFieldsFromTable();
            }
        });
    }

    // Mengisi field input dari baris yang dipilih di tabel
    private void populateFieldsFromTable() {
        int selectedRow = tblRiwayatPendapatan.getSelectedRow();
        if (selectedRow != -1) {
            int modelRow = tblRiwayatPendapatan.convertRowIndexToModel(selectedRow);
            
            jenisPendapatan.setText(pendapatanTableModel.getValueAt(modelRow, 2).toString());
            sumberPendapatan.setText(pendapatanTableModel.getValueAt(modelRow, 3).toString());
            
            // memformat nominal dalam bentuk IDR untuk ditampilkan
            int nominal = (int) pendapatanTableModel.getValueAt(modelRow, 4);
            NumberFormat format = NumberFormat.getInstance(new Locale("id", "ID"));
            nominalPendapatan.setText(format.format(nominal));
            
            Object keteranganObj = pendapatanTableModel.getValueAt(modelRow, 5);
            keteranganPendapatan.setText(keteranganObj != null ? keteranganObj.toString() : "");
        }
    }

    /**
     * Set the environment ID for this panel
     * @param idLingkungan The environment ID
     */
    // Setter untuk idLingkungan dan refresh tabel
    public void setIdLingkungan(int idLingkungan) {
        this.idLingkungan = idLingkungan;
        refreshTabelPendapatan();
    }
    
    /**
     * Set ID pengguna untuk panel ini
     * @param idPengguna The user ID
     */
    // Setter untuk idPengguna
    public void setIdPengguna(int idPengguna) {
        this.idPengguna = idPengguna;
    }
    
    /**
     * Set callback refresh dashboard
     * @param callback The callback to refresh dashboard data
     */
    // Setter untuk callback refresh dashboard
    public void setDashboardRefreshCallback(Runnable callback) {
        this.dashboardRefreshCallback = callback;
    }
    
    /**
     * Memformat input nominal ke format mata uang
     */
    // Memformat input nominal ke format mata uang
    private void formatCurrencyInput() {
        String text = nominalPendapatan.getText().trim();
        if (!text.isEmpty()) {
            try {
                NumberFormat format = NumberFormat.getInstance(new Locale("id", "ID"));
                Number number = format.parse(text);
                int value = number.intValue();
                nominalPendapatan.setText(format.format(value));
            } catch (Exception e) {
                // abaikan kesalahan parsing, biarkan teks sebagai is untuk user untuk membetulkan
            }
        }
    }
    
    /**
     * Menambah data pendapatan ke database
     */
    // Menambah data pendapatan ke database
    private void tambahPendapatan() {
        if (!validateIds()) return;

        try {
            String jenis = jenisPendapatan.getText().trim();
            String sumber = sumberPendapatan.getText().trim();
            String keterangan = keteranganPendapatan.getText().trim();
            String nominalStr = nominalPendapatan.getText().trim();

            if (jenis.isEmpty() || sumber.isEmpty() || nominalStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int nominal = parseNominal(nominalStr);
            if (nominal <= 0) return;

            Connection conn = Koneksi.getConnection();
            String sql = "INSERT INTO transaksi (id_lingkungan, tipe, jenis, jumlah, tanggal, sumber, keterangan, id_dibuat_oleh) VALUES (?, 'pemasukan', ?, ?, NOW(), ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idLingkungan);
            ps.setString(2, jenis);
            ps.setInt(3, nominal);
            ps.setString(4, sumber);
            ps.setString(5, keterangan);
            ps.setInt(6, idPengguna);

            int result = ps.executeUpdate();
            if (result > 0) {
                Koneksi.commit();
                logAktivitas("Menambahkan pendapatan: " + jenis + " sebesar " + formatCurrency(nominal));
                JOptionPane.showMessageDialog(this, "Pendapatan berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                refreshTabelPendapatan();
                if (dashboardRefreshCallback != null) dashboardRefreshCallback.run();
            } else {
                Koneksi.rollback();
                JOptionPane.showMessageDialog(this, "Gagal menambahkan pendapatan!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            ps.close();
        } catch (SQLException e) {
            Koneksi.rollback();
            JOptionPane.showMessageDialog(this, "Error database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Mengedit data pendapatan yang dipilih
    private void editPendapatan() {
        int selectedRow = tblRiwayatPendapatan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diubah.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validateIds()) return;

        try {
            int modelRow = tblRiwayatPendapatan.convertRowIndexToModel(selectedRow);
            int idTransaksi = (int) pendapatanTableModel.getValueAt(modelRow, 0);

            String jenis = jenisPendapatan.getText().trim();
            String sumber = sumberPendapatan.getText().trim();
            String keterangan = keteranganPendapatan.getText().trim();
            String nominalStr = nominalPendapatan.getText().trim();

            if (jenis.isEmpty() || sumber.isEmpty() || nominalStr.isEmpty()) {
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
            ps.setString(3, sumber);
            ps.setString(4, keterangan);
            ps.setInt(5, idTransaksi);

            int result = ps.executeUpdate();
            if (result > 0) {
                Koneksi.commit();
                logAktivitas("Mengubah data pendapatan ID: " + idTransaksi);
                JOptionPane.showMessageDialog(this, "Data pendapatan berhasil diubah!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                refreshTabelPendapatan();
                if (dashboardRefreshCallback != null) dashboardRefreshCallback.run();
            } else {
                Koneksi.rollback();
                JOptionPane.showMessageDialog(this, "Gagal mengubah data pendapatan!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            ps.close();
        } catch (SQLException e) {
            Koneksi.rollback();
            JOptionPane.showMessageDialog(this, "Error database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Menghapus data pendapatan yang dipilih
    private void hapusPendapatan() {
        int selectedRow = tblRiwayatPendapatan.getSelectedRow();
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
            int modelRow = tblRiwayatPendapatan.convertRowIndexToModel(selectedRow);
            int idTransaksi = (int) pendapatanTableModel.getValueAt(modelRow, 0);

            Connection conn = Koneksi.getConnection();
            String sql = "DELETE FROM transaksi WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idTransaksi);

            int result = ps.executeUpdate();
            if (result > 0) {
                Koneksi.commit();
                logAktivitas("Menghapus data pendapatan ID: " + idTransaksi);
                JOptionPane.showMessageDialog(this, "Data pendapatan berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                refreshTabelPendapatan();
                if (dashboardRefreshCallback != null) dashboardRefreshCallback.run();
            } else {
                Koneksi.rollback();
                JOptionPane.showMessageDialog(this, "Gagal menghapus data pendapatan!", "Error", JOptionPane.ERROR_MESSAGE);
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
                nominalPendapatan.requestFocus();
                return -1;
            }
            return nominal;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Format nominal tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
            nominalPendapatan.requestFocus();
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
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return format.format(amount);
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
        jenisPendapatan.setText("");
        sumberPendapatan.setText("");
        nominalPendapatan.setText("");
        keteranganPendapatan.setText("");
        tblRiwayatPendapatan.clearSelection();
        jenisPendapatan.requestFocus();
    }

    // Mengambil dan menampilkan data pendapatan ke tabel
    public void refreshTabelPendapatan() {
        if (idLingkungan <= 0) return;
        pendapatanTableModel.setRowCount(0); // Clear table

        try {
            Connection conn = Koneksi.getConnection();
            String sql = "SELECT id, tanggal, jenis, sumber, jumlah, keterangan FROM transaksi " +
                         "WHERE id_lingkungan = ? AND tipe = 'pemasukan' ORDER BY tanggal DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idLingkungan);
            ResultSet rs = ps.executeQuery();

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("id", "ID"));

            while (rs.next()) {
                int id = rs.getInt("id");
                Timestamp tanggal = rs.getTimestamp("tanggal");
                String jenis = rs.getString("jenis");
                String sumber = rs.getString("sumber");
                int jumlah = rs.getInt("jumlah");
                String keterangan = rs.getString("keterangan");

                pendapatanTableModel.addRow(new Object[]{
                    id,
                    sdf.format(tanggal),
                    jenis,
                    sumber,
                    jumlah, // menyimpan sebagai integer untuk pengurutan/perhitungan
                    keterangan
                });
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error memuat data pendapatan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        jenisPendapatan = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        sumberPendapatan = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        nominalPendapatan = new javax.swing.JTextField();
        btnTambahPendapatan = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        keteranganPendapatan = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRiwayatPendapatan = new javax.swing.JTable();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Pendapatan");

        jLabel2.setText("Bendahara /");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Pendapatan");

        jPanel1.setBackground(new java.awt.Color(245, 245, 245));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Jenis Pendapatan");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Sumber");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Nominal");

        btnTambahPendapatan.setBackground(new java.awt.Color(0, 255, 0));
        btnTambahPendapatan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTambahPendapatan.setText("TAMBAH PENDAPATAN");
        btnTambahPendapatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahPendapatanActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Keterangan");

        tblRiwayatPendapatan.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblRiwayatPendapatan);

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
                    .addComponent(jenisPendapatan)
                    .addComponent(sumberPendapatan)
                    .addComponent(nominalPendapatan)
                    .addComponent(keteranganPendapatan)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnTambahPendapatan, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(jenisPendapatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sumberPendapatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nominalPendapatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(keteranganPendapatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTambahPendapatan)))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 517, Short.MAX_VALUE)
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

    // Event handler tombol tambah pendapatan
    private void btnTambahPendapatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahPendapatanActionPerformed
        tambahPendapatan();
    }//GEN-LAST:event_btnTambahPendapatanActionPerformed

    // Event handler tombol edit pendapatan
    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        editPendapatan();
    }//GEN-LAST:event_btnEditActionPerformed

    // Event handler tombol hapus pendapatan
    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
       hapusPendapatan();
    }//GEN-LAST:event_btnHapusActionPerformed

    // Event handler tombol clear form
    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearForm();
    }//GEN-LAST:event_btnClearActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnTambahPendapatan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jenisPendapatan;
    private javax.swing.JTextField keteranganPendapatan;
    private javax.swing.JTextField nominalPendapatan;
    private javax.swing.JTextField sumberPendapatan;
    private javax.swing.JTable tblRiwayatPendapatan;
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
