/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package usersForm.Ketua;

/**
 *
 * @author HYPE AMD
 */
public class panelDaftarPengajuan extends javax.swing.JPanel {

    private int idLingkungan = -1;
    private int idPengguna = -1;
    private javax.swing.table.DefaultTableModel pengajuanTableModel;
    private javax.swing.Timer autoRefreshTimer;

    // Konstruktor panel, inisialisasi komponen dan logika
    public panelDaftarPengajuan() {
        initComponents();
        initLogic();
    }

    // Inisialisasi logika tabel pengajuan
    private void initLogic() {
        pengajuanTableModel = new javax.swing.table.DefaultTableModel(
            new Object[]{"ID", "Tanggal", "Jenis", "Jumlah", "Sumber", "Keterangan"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblDaftarPengajuan.setModel(pengajuanTableModel);
        // Set warna header tabel dengan custom renderer agar tidak diabaikan L&F
        javax.swing.table.JTableHeader header = tblDaftarPengajuan.getTableHeader();
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
        // Set seluruh isi tabel rata tengah
        javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        for (int i = 0; i < tblDaftarPengajuan.getColumnCount(); i++) {
            tblDaftarPengajuan.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        tblDaftarPengajuan.getColumnModel().getColumn(0).setMinWidth(0);
        tblDaftarPengajuan.getColumnModel().getColumn(0).setMaxWidth(0);
        tblDaftarPengajuan.getColumnModel().getColumn(0).setWidth(0);
    }

    // Setter untuk idLingkungan dan idPengguna, lalu load daftar pengajuan
    public void setLingkunganDanPengguna(int idLingkungan, int idPengguna) {
        this.idLingkungan = idLingkungan;
        this.idPengguna = idPengguna;
        loadDaftarPengajuan();
//        if (autoRefreshTimer != null) autoRefreshTimer.stop();
//        autoRefreshTimer = new javax.swing.Timer(5000, e -> loadDaftarPengajuan());
//        autoRefreshTimer.start();
    }

    // Mengambil dan menampilkan daftar pengajuan ke tabel
    private void loadDaftarPengajuan() {
        System.out.println("[DEBUG] loadDaftarPengajuan: idLingkungan=" + idLingkungan);
        pengajuanTableModel.setRowCount(0);
        if (idLingkungan <= 0) return;
        try {
            java.sql.Connection conn = database.Koneksi.getConnection();
            String sql = "SELECT t.id, t.tanggal, t.jenis, t.jumlah, t.sumber, t.keterangan " +
                         "FROM transaksi t JOIN verifikasi_transaksi v ON t.id = v.id_transaksi " +
                         "WHERE t.id_lingkungan = ? AND t.tipe = 'pengeluaran' AND v.status = 'pending' " +
                         "ORDER BY t.tanggal ASC";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idLingkungan);
            java.sql.ResultSet rs = ps.executeQuery();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm");
            while (rs.next()) {
                pengajuanTableModel.addRow(new Object[]{
                    rs.getInt("id"),
                    sdf.format(rs.getTimestamp("tanggal")),
                    rs.getString("jenis"),
                    String.format("Rp.%,d", rs.getInt("jumlah")),
                    rs.getString("sumber"),
                    rs.getString("keterangan")
                });
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            pengajuanTableModel.setRowCount(0);
            e.printStackTrace();
        }
    }

    // Menyetujui pengajuan yang dipilih
    private void setujuiPengajuan() {
        int selectedRow = tblDaftarPengajuan.getSelectedRow();
        if (selectedRow == -1) return;
        int modelRow = tblDaftarPengajuan.convertRowIndexToModel(selectedRow);
        int idTransaksi = (int) pengajuanTableModel.getValueAt(modelRow, 0);
        String alasan = lblKomentarPengajuan.getText().trim();
        try {
            java.sql.Connection conn = database.Koneksi.getConnection();
            String sql = "UPDATE verifikasi_transaksi SET status='disetujui', alasan=? WHERE id_transaksi=? AND status='pending'";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, alasan.isEmpty() ? "Disetujui oleh ketua" : alasan);
            ps.setInt(2, idTransaksi);
            int result = ps.executeUpdate();
            conn.commit();
            ps.close();
            if (result > 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Pengajuan berhasil disetujui!", "Sukses", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                loadDaftarPengajuan();
                lblKomentarPengajuan.setText("");
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Gagal menyetujui pengajuan!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Terjadi error: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    // Menolak pengajuan yang dipilih
    private void tolakPengajuan() {
        int selectedRow = tblDaftarPengajuan.getSelectedRow();
        System.out.println("[DEBUG] Tombol Tolak diklik, selectedRow=" + selectedRow);
        if (selectedRow == -1) {
            System.out.println("[DEBUG] Tidak ada row yang dipilih!");
            return;
        }
        int modelRow = tblDaftarPengajuan.convertRowIndexToModel(selectedRow);
        int idTransaksi = (int) pengajuanTableModel.getValueAt(modelRow, 0);
        String alasan = lblKomentarPengajuan.getText().trim();
        System.out.println("[DEBUG] idTransaksi yang dipilih: " + idTransaksi);
        System.out.println("[DEBUG] Alasan penolakan: '" + alasan + "'");
        if (alasan.isEmpty()) {
            System.out.println("[DEBUG] Alasan penolakan kosong!");
            javax.swing.JOptionPane.showMessageDialog(this, "Harap isi alasan penolakan!", "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            java.sql.Connection conn = database.Koneksi.getConnection();
            String sql = "UPDATE verifikasi_transaksi SET status='ditolak', alasan=? WHERE id_transaksi=? AND status='pending'";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, alasan);
            ps.setInt(2, idTransaksi);
            int result = ps.executeUpdate();
            conn.commit();
            ps.close();
            System.out.println("[DEBUG] Hasil update: " + result);
            if (result > 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Pengajuan berhasil ditolak!", "Sukses", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                loadDaftarPengajuan();
                lblKomentarPengajuan.setText("");
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Gagal menolak pengajuan!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Terjadi error: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    // Menghentikan auto refresh daftar pengajuan
    public void stopAutoRefresh() {
        if (autoRefreshTimer != null) autoRefreshTimer.stop();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDaftarPengajuan = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lblKomentarPengajuan = new javax.swing.JTextArea();
        btnTerima = new javax.swing.JButton();
        btnTolak = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Daftar Pengajuan");

        jLabel2.setText("Bendahara /");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Daftar Pengajuan");

        tblDaftarPengajuan.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblDaftarPengajuan);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Komentar :");

        lblKomentarPengajuan.setColumns(20);
        lblKomentarPengajuan.setRows(5);
        jScrollPane2.setViewportView(lblKomentarPengajuan);

        btnTerima.setBackground(new java.awt.Color(0, 255, 0));
        btnTerima.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTerima.setText("Setuju");
        btnTerima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTerimaActionPerformed(evt);
            }
        });

        btnTolak.setBackground(new java.awt.Color(255, 0, 0));
        btnTolak.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTolak.setText("Tolak");
        btnTolak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTolakActionPerformed(evt);
            }
        });

        btnRefresh.setBackground(new java.awt.Color(0, 204, 204));
        btnRefresh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnTerima, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnTolak, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnRefresh)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnRefresh)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTerima, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTolak, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 432, Short.MAX_VALUE)
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

    // Event handler tombol refresh
    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadDaftarPengajuan();
    }//GEN-LAST:event_btnRefreshActionPerformed

    // Event handler tombol tolak
    private void btnTolakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTolakActionPerformed
        tolakPengajuan();
    }//GEN-LAST:event_btnTolakActionPerformed

    // Event handler tombol terima
    private void btnTerimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTerimaActionPerformed
        setujuiPengajuan();
    }//GEN-LAST:event_btnTerimaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnTerima;
    private javax.swing.JButton btnTolak;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea lblKomentarPengajuan;
    private javax.swing.JTable tblDaftarPengajuan;
    // End of variables declaration//GEN-END:variables
}
