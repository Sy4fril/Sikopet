/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package usersForm.Ketua;

import javax.swing.JPanel;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author HYPE AMD
 */
public class panelBuatLaporan extends javax.swing.JPanel {
    private JPanel activeMenuBarPanel;
    private int idLingkungan = -1;
    private String modeLaporan = "keuangan"; // "keuangan" atau "tabungan"
    
    
    // Konstruktor panel, inisialisasi komponen dan menu
    public panelBuatLaporan() {
        initComponents();
        activeMenuBarPanel = null;
        updateMenuBarHighlight(null);
        modeLaporan = "keuangan";
    }

    // Setter untuk idLingkungan dan load data laporan
    public void setIdLingkungan(int idLingkungan) {
        this.idLingkungan = idLingkungan;
        loadDataLaporan();
    }

    // Mengambil dan menampilkan data laporan ke tabel sesuai mode
    private void loadDataLaporan() {
        javax.swing.table.DefaultTableModel model;
        if ("keuangan".equals(modeLaporan)) {
            // Laporan Keuangan
            model = new javax.swing.table.DefaultTableModel(
                new Object[]{"Tanggal", "Tipe", "Jenis", "Jumlah", "Sumber", "Keterangan"}, 0
            ) {
                @Override
                public boolean isCellEditable(int row, int column) { return false; }
            };
            try {
                java.sql.Connection conn = database.Koneksi.getConnection();
                String sql = "SELECT tanggal, tipe, jenis, jumlah, sumber, keterangan FROM transaksi WHERE id_lingkungan=? ORDER BY tanggal DESC";
                java.sql.PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, idLingkungan);
                java.sql.ResultSet rs = ps.executeQuery();
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm");
                while (rs.next()) {
                    String tanggal = rs.getTimestamp("tanggal") != null ? sdf.format(rs.getTimestamp("tanggal")) : "-";
                    String tipe = rs.getString("tipe");
                    String jenis = rs.getString("jenis");
                    int jumlah = rs.getInt("jumlah");
                    String sumber = rs.getString("sumber");
                    String keterangan = rs.getString("keterangan");
                    model.addRow(new Object[]{tanggal, tipe, jenis, String.format("Rp.%,d", jumlah), sumber, keterangan});
                }
                rs.close();
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Laporan Tabungan
            model = new javax.swing.table.DefaultTableModel(
                new Object[]{"Tanggal", "Nama Warga", "Jenis", "Jumlah", "Metode", "Keterangan"}, 0
            ) {
                @Override
                public boolean isCellEditable(int row, int column) { return false; }
            };
            try {
                java.sql.Connection conn = database.Koneksi.getConnection();
                String sql = "SELECT t.tanggal, w.nama_warga, t.jenis, t.jumlah, t.metode, t.keterangan " +
                        "FROM tabungan t JOIN warga w ON t.id_warga = w.id WHERE w.id_lingkungan = ? ORDER BY t.tanggal DESC";
                java.sql.PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, idLingkungan);
                java.sql.ResultSet rs = ps.executeQuery();
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm");
                while (rs.next()) {
                    String tanggal = rs.getTimestamp("tanggal") != null ? sdf.format(rs.getTimestamp("tanggal")) : "-";
                    String namaWarga = rs.getString("nama_warga");
                    String jenis = rs.getString("jenis");
                    double jumlah = rs.getDouble("jumlah");
                    String metode = rs.getString("metode");
                    String keterangan = rs.getString("keterangan");
                    model.addRow(new Object[]{tanggal, namaWarga, jenis, String.format("Rp.%,.2f", jumlah), metode, keterangan});
                }
                rs.close();
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        tblAksiLaporan.setModel(model);
        // Set warna header tabel dengan custom renderer agar tidak diabaikan L&F
        javax.swing.table.JTableHeader header = tblAksiLaporan.getTableHeader();
        header.setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(new java.awt.Color(0, 51, 102));
                c.setForeground(java.awt.Color.WHITE);
                c.setFont(c.getFont().deriveFont(java.awt.Font.BOLD));
                setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                return c;
            }
        });
        // Custom renderer untuk pewarnaan baris berdasarkan jenis, tipe, dan alignment center
        tblAksiLaporan.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                int jenisCol = -1;
                int tipeCol = -1;
                for (int i = 0; i < table.getColumnCount(); i++) {
                    if ("Jenis".equalsIgnoreCase(table.getColumnName(i))) {
                        jenisCol = i;
                    }
                    if ("Tipe".equalsIgnoreCase(table.getColumnName(i))) {
                        tipeCol = i;
                    }
                }
                boolean colored = false;
                if (jenisCol >= 0) {
                    String jenis = (String) table.getModel().getValueAt(row, jenisCol);
                    if ("Penarikan".equalsIgnoreCase(jenis)) {
                        c.setBackground(new java.awt.Color(255, 204, 204)); // merah muda
                        c.setForeground(java.awt.Color.RED);
                        colored = true;
                    } else if ("Setoran".equalsIgnoreCase(jenis)) {
                        c.setBackground(new java.awt.Color(204, 255, 204)); // hijau muda
                        c.setForeground(new java.awt.Color(0, 153, 0));
                        colored = true;
                    }
                }
                if (!colored && tipeCol >= 0) {
                    String tipe = (String) table.getModel().getValueAt(row, tipeCol);
                    if ("pemasukan".equalsIgnoreCase(tipe)) {
                        c.setBackground(new java.awt.Color(204, 255, 204)); // hijau muda
                        c.setForeground(new java.awt.Color(0, 153, 0));
                    } else if ("pengeluaran".equalsIgnoreCase(tipe)) {
                        c.setBackground(new java.awt.Color(255, 204, 204)); // merah muda
                        c.setForeground(java.awt.Color.RED);
                    } else {
                        c.setBackground(java.awt.Color.WHITE);
                        c.setForeground(java.awt.Color.BLACK);
                    }
                } else if (!colored) {
                    c.setBackground(java.awt.Color.WHITE);
                    c.setForeground(java.awt.Color.BLACK);
                }
                setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                return c;
            }
        });
    }

    // Mengatur highlight pada menu bar laporan
    private void updateMenuBarHighlight(JPanel activePanel) {
        // Reset all panel backgrounds
        pnlLaporanKeuangan.setBackground(new java.awt.Color(242,242,242));
        jLabel4.setForeground(new java.awt.Color(0,0,0));
        pnlLaporanTabungan.setBackground(new java.awt.Color(242,242,242));
        jLabel5.setForeground(new java.awt.Color(0,0,0));
        
        // Highlight active panel
        if (activePanel != null) {
            activePanel.setBackground(new java.awt.Color(33,52,72));
            activePanel.setForeground(new java.awt.Color(255,255,255));
            activeMenuBarPanel = activePanel;
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        pnlLaporanKeuangan = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        pnlLaporanTabungan = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAksiLaporan = new javax.swing.JTable();
        lblPilihan = new javax.swing.JLabel();
        pnlExport = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Buat Laporan");

        jLabel2.setText("Bendahara /");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Buat Laporan");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setForeground(new java.awt.Color(0, 0, 51));

        pnlLaporanKeuangan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlLaporanKeuanganMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlLaporanKeuanganMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlLaporanKeuanganMouseExited(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Laporan Keuangan");

        javax.swing.GroupLayout pnlLaporanKeuanganLayout = new javax.swing.GroupLayout(pnlLaporanKeuangan);
        pnlLaporanKeuangan.setLayout(pnlLaporanKeuanganLayout);
        pnlLaporanKeuanganLayout.setHorizontalGroup(
            pnlLaporanKeuanganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLaporanKeuanganLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel4)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        pnlLaporanKeuanganLayout.setVerticalGroup(
            pnlLaporanKeuanganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLaporanKeuanganLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlLaporanTabungan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlLaporanTabunganMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlLaporanTabunganMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlLaporanTabunganMouseExited(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Laporan Tabungan");

        javax.swing.GroupLayout pnlLaporanTabunganLayout = new javax.swing.GroupLayout(pnlLaporanTabungan);
        pnlLaporanTabungan.setLayout(pnlLaporanTabunganLayout);
        pnlLaporanTabunganLayout.setHorizontalGroup(
            pnlLaporanTabunganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLaporanTabunganLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel5)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        pnlLaporanTabunganLayout.setVerticalGroup(
            pnlLaporanTabunganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLaporanTabunganLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(pnlLaporanKeuangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlLaporanTabungan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlLaporanTabungan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlLaporanKeuangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        tblAksiLaporan.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblAksiLaporan);

        lblPilihan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPilihan.setText("Action");

        pnlExport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlExportMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlExportMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlExportMouseExited(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("EXPORT");

        javax.swing.GroupLayout pnlExportLayout = new javax.swing.GroupLayout(pnlExport);
        pnlExport.setLayout(pnlExportLayout);
        pnlExportLayout.setHorizontalGroup(
            pnlExportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlExportLayout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(29, 29, 29))
        );
        pnlExportLayout.setVerticalGroup(
            pnlExportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlExportLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblPilihan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlExport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlExport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPilihan, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 496, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Event handler klik export laporan ke CSV
    private void pnlExportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlExportMouseClicked
        javax.swing.table.TableModel model = tblAksiLaporan.getModel();
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Tidak ada data untuk diekspor!", "Export CSV", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Simpan Laporan sebagai CSV");
        // Set default file name dengan nama lingkungan dan RT/RW
        String tanggal = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        String namaFile;
        try {
            java.sql.Connection conn = database.Koneksi.getConnection();
            String sql = "SELECT nama_daerah, rt, rw FROM lingkungan WHERE id = ?";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idLingkungan);
            java.sql.ResultSet rs = ps.executeQuery();
            String namaLingkungan = "Lingkungan";
            String rt = "-";
            String rw = "-";
            if (rs.next()) {
                namaLingkungan = rs.getString("nama_daerah").replaceAll("[\\s,]", "_");
                rt = rs.getString("rt");
                rw = rs.getString("rw");
            }
            rs.close();
            ps.close();
            namaFile = String.format("Laporan_%s_%s_RT%s_RW%s_%s.csv",
                ("keuangan".equals(modeLaporan) ? "Keuangan" : "Tabungan"),
                namaLingkungan, rt, rw, tanggal);
        } catch (Exception e) {
            // fallback jika gagal ambil nama lingkungan
            namaFile = "Laporan_" + ("keuangan".equals(modeLaporan) ? "Keuangan" : "Tabungan") + "_" + tanggal + ".csv";
        }
        fileChooser.setSelectedFile(new java.io.File(namaFile));
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".csv")) {
                filePath += ".csv";
            }
            try (FileWriter csvWriter = new FileWriter(filePath)) {
                // Write header
                for (int i = 0; i < model.getColumnCount(); i++) {
                    csvWriter.append(model.getColumnName(i));
                    if (i < model.getColumnCount() - 1) csvWriter.append(",");
                }
                csvWriter.append("\n");
                // Write rows
                for (int row = 0; row < model.getRowCount(); row++) {
                    for (int col = 0; col < model.getColumnCount(); col++) {
                        Object value = model.getValueAt(row, col);
                        String cell = value != null ? value.toString().replaceAll(",", " ") : "";
                        csvWriter.append(cell);
                        if (col < model.getColumnCount() - 1) csvWriter.append(",");
                    }
                    csvWriter.append("\n");
                }
                csvWriter.flush();
                JOptionPane.showMessageDialog(this, "Export berhasil ke: " + filePath, "Export CSV", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Export gagal: " + ex.getMessage(), "Export CSV", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_pnlExportMouseClicked

    // Event handler hover masuk pada tombol export
    private void pnlExportMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlExportMouseEntered
        pnlExport.setBackground(new java.awt.Color(33,52,72));
        jLabel7.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_pnlExportMouseEntered

    // Event handler hover keluar pada tombol export
    private void pnlExportMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlExportMouseExited
        pnlExport.setBackground(new java.awt.Color(242,242,242));
        jLabel7.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_pnlExportMouseExited

    // Event handler klik menu laporan keuangan
    private void pnlLaporanKeuanganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLaporanKeuanganMouseClicked
        updateMenuBarHighlight(pnlLaporanKeuangan);
        jLabel4.setForeground(new java.awt.Color(255,255,255));
        modeLaporan = "keuangan";
        lblPilihan.setText("Laporan Keuangan");
        loadDataLaporan();
    }//GEN-LAST:event_pnlLaporanKeuanganMouseClicked

    // Event handler hover masuk pada menu laporan keuangan
    private void pnlLaporanKeuanganMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLaporanKeuanganMouseEntered
        if (activeMenuBarPanel != pnlLaporanKeuangan) {
            pnlLaporanKeuangan.setBackground(new java.awt.Color(33,52,72));
            jLabel4.setForeground(new java.awt.Color(255,255,255));
        }
    }//GEN-LAST:event_pnlLaporanKeuanganMouseEntered

    // Event handler hover keluar pada menu laporan keuangan
    private void pnlLaporanKeuanganMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLaporanKeuanganMouseExited
        if (activeMenuBarPanel != pnlLaporanKeuangan) {
            pnlLaporanKeuangan.setBackground(new java.awt.Color(242,242,242));
            jLabel4.setForeground(new java.awt.Color(0,0,0));
        }
    }//GEN-LAST:event_pnlLaporanKeuanganMouseExited

    // Event handler klik menu laporan tabungan
    private void pnlLaporanTabunganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLaporanTabunganMouseClicked
        updateMenuBarHighlight(pnlLaporanTabungan);
        jLabel5.setForeground(new java.awt.Color(255,255,255));
        modeLaporan = "tabungan";
        lblPilihan.setText("Laporan Tabungan");
        loadDataLaporan();
    }//GEN-LAST:event_pnlLaporanTabunganMouseClicked

    // Event handler hover masuk pada menu laporan tabungan
    private void pnlLaporanTabunganMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLaporanTabunganMouseEntered
        if (activeMenuBarPanel != pnlLaporanTabungan) {
            pnlLaporanTabungan.setBackground(new java.awt.Color(33,52,72));
            jLabel5.setForeground(new java.awt.Color(255,255,255));
        }
    }//GEN-LAST:event_pnlLaporanTabunganMouseEntered

    // Event handler hover keluar pada menu laporan tabungan
    private void pnlLaporanTabunganMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLaporanTabunganMouseExited
        if (activeMenuBarPanel != pnlLaporanTabungan) {
            pnlLaporanTabungan.setBackground(new java.awt.Color(242,242,242));
            jLabel5.setForeground(new java.awt.Color(0,0,0));
        }
    }//GEN-LAST:event_pnlLaporanTabunganMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPilihan;
    private javax.swing.JPanel pnlExport;
    private javax.swing.JPanel pnlLaporanKeuangan;
    private javax.swing.JPanel pnlLaporanTabungan;
    private javax.swing.JTable tblAksiLaporan;
    // End of variables declaration//GEN-END:variables
}
