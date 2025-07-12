/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package usersForm.Warga;

/**
 *
 * @author HYPE AMD
 */
public class TabunganWarga extends javax.swing.JPanel {

    // Tambahan variabel untuk id warga
    private int idWarga = -1;

    public void setIdWarga(int idWarga) {
        this.idWarga = idWarga;
        refreshData();
    }

    public void refreshData() {
        if (idWarga <= 0) {
            lblTotalSaldoTabungan.setText("Rp.0");
            lblPemasukan.setText("Rp. 0");
            lblPengeluaran.setText("Rp. 0");
            javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel();
            tblTransaksiTerakhir.setModel(model);
            return;
        }
        try {
            java.sql.Connection conn = database.Koneksi.getConnection();
            // Ambil saldo
            String sqlSaldo = "SELECT saldo FROM saldo_tabungan WHERE id_warga = ?";
            java.sql.PreparedStatement psSaldo = conn.prepareStatement(sqlSaldo);
            psSaldo.setInt(1, idWarga);
            java.sql.ResultSet rsSaldo = psSaldo.executeQuery();
            if (rsSaldo.next()) {
                double saldo = rsSaldo.getDouble("saldo");
                lblTotalSaldoTabungan.setText(String.format("Rp.%,.0f", saldo));
            } else {
                lblTotalSaldoTabungan.setText("Rp.0");
            }
            rsSaldo.close();
            psSaldo.close();

            // Ambil total pemasukan
            String sqlPemasukan = "SELECT SUM(jumlah) as total FROM tabungan WHERE id_warga = ? AND jenis = 'setoran'";
            java.sql.PreparedStatement psPemasukan = conn.prepareStatement(sqlPemasukan);
            psPemasukan.setInt(1, idWarga);
            java.sql.ResultSet rsPemasukan = psPemasukan.executeQuery();
            if (rsPemasukan.next()) {
                double pemasukan = rsPemasukan.getDouble("total");
                lblPemasukan.setText(String.format("Rp. %,.0f", pemasukan));
            } else {
                lblPemasukan.setText("Rp. 0");
            }
            rsPemasukan.close();
            psPemasukan.close();

            // Ambil total pengeluaran
            String sqlPengeluaran = "SELECT SUM(jumlah) as total FROM tabungan WHERE id_warga = ? AND jenis = 'penarikan'";
            java.sql.PreparedStatement psPengeluaran = conn.prepareStatement(sqlPengeluaran);
            psPengeluaran.setInt(1, idWarga);
            java.sql.ResultSet rsPengeluaran = psPengeluaran.executeQuery();
            if (rsPengeluaran.next()) {
                double pengeluaran = rsPengeluaran.getDouble("total");
                lblPengeluaran.setText(String.format("Rp. %,.0f", pengeluaran));
            } else {
                lblPengeluaran.setText("Rp. 0");
            }
            rsPengeluaran.close();
            psPengeluaran.close();

            // Ambil riwayat transaksi terakhir (10 transaksi)
            String sqlTransaksi = "SELECT tanggal, jenis, jumlah, metode, keterangan FROM tabungan WHERE id_warga = ? ORDER BY tanggal DESC LIMIT 10";
            java.sql.PreparedStatement psTransaksi = conn.prepareStatement(sqlTransaksi);
            psTransaksi.setInt(1, idWarga);
            java.sql.ResultSet rsTransaksi = psTransaksi.executeQuery();
            javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
                new Object[]{"Tanggal", "Jenis", "Jumlah", "Metode", "Keterangan"}, 0
            );
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");
            while (rsTransaksi.next()) {
                String tanggal = "";
                try {
                    java.sql.Timestamp ts = rsTransaksi.getTimestamp("tanggal");
                    if (ts != null) tanggal = sdf.format(ts);
                } catch (Exception e) {}
                String jenis = rsTransaksi.getString("jenis");
                double jumlah = rsTransaksi.getDouble("jumlah");
                String metode = rsTransaksi.getString("metode");
                String ket = rsTransaksi.getString("keterangan");
                model.addRow(new Object[]{tanggal, jenis, String.format("Rp. %,.0f", jumlah), metode, ket});
            }
            tblTransaksiTerakhir.setModel(model);
            // Set warna header tabel dengan custom renderer agar tidak diabaikan L&F
            javax.swing.table.JTableHeader header = tblTransaksiTerakhir.getTableHeader();
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
            // Custom renderer untuk pewarnaan baris berdasarkan jenis dan alignment center
            tblTransaksiTerakhir.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
                @Override
                public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    int jenisCol = -1;
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        if ("Jenis".equalsIgnoreCase(table.getColumnName(i))) {
                            jenisCol = i;
                            break;
                        }
                    }
                    if (jenisCol >= 0) {
                        String jenis = (String) table.getModel().getValueAt(row, jenisCol);
                        if ("Penarikan".equalsIgnoreCase(jenis)) {
                            c.setBackground(new java.awt.Color(255, 204, 204)); // merah muda
                            c.setForeground(java.awt.Color.RED);
                        } else if ("Setoran".equalsIgnoreCase(jenis)) {
                            c.setBackground(new java.awt.Color(204, 255, 204)); // hijau muda
                            c.setForeground(new java.awt.Color(0, 153, 0));
                        } else {
                            c.setBackground(java.awt.Color.WHITE);
                            c.setForeground(java.awt.Color.BLACK);
                        }
                    } else {
                        c.setBackground(java.awt.Color.WHITE);
                        c.setForeground(java.awt.Color.BLACK);
                    }
                    setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    return c;
                }
            });
            rsTransaksi.close();
            psTransaksi.close();
        } catch (Exception e) {
            lblTotalSaldoTabungan.setText("Rp.0");
            lblPemasukan.setText("Rp. 0");
            lblPengeluaran.setText("Rp. 0");
            javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel();
            tblTransaksiTerakhir.setModel(model);
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal mengambil data tabungan: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Creates new form TabunganWarga
     */
    public TabunganWarga() {
        initComponents();
        refreshData();
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
        lblTotalSaldoTabungan = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lblPemasukan = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lblPengeluaran = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTransaksiTerakhir = new javax.swing.JTable();
        btnRefresh = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Tabungan");

        jLabel2.setText("Warga /");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Tabungan");

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 255, 153));
        jLabel4.setText("Saldo Tabungan Kamu :");

        lblTotalSaldoTabungan.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTotalSaldoTabungan.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalSaldoTabungan.setText("Rp.0");

        jPanel2.setBackground(new java.awt.Color(0, 0, 51));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Pemasukan");

        lblPemasukan.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblPemasukan.setForeground(new java.awt.Color(0, 204, 0));
        lblPemasukan.setText("Rp. 0");

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Increase.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7))
                    .addComponent(lblPemasukan))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPemasukan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Transaksi Terakhir");

        jPanel4.setBackground(new java.awt.Color(0, 0, 51));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Pengeluaran");

        lblPengeluaran.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblPengeluaran.setForeground(new java.awt.Color(255, 0, 0));
        lblPengeluaran.setText("Rp. 0");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Decrease_1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5))
                    .addComponent(lblPengeluaran))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPengeluaran)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        tblTransaksiTerakhir.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblTransaksiTerakhir);

        btnRefresh.setBackground(new java.awt.Color(153, 153, 0));
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
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTotalSaldoTabungan)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRefresh)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalSaldoTabungan)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(btnRefresh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 568, Short.MAX_VALUE)
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

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        refreshData();
    }//GEN-LAST:event_btnRefreshActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefresh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPemasukan;
    private javax.swing.JLabel lblPengeluaran;
    private javax.swing.JLabel lblTotalSaldoTabungan;
    private javax.swing.JTable tblTransaksiTerakhir;
    // End of variables declaration//GEN-END:variables
}
