/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package usersForm.Ketua;

/**
 *
 * @author HYPE AMD
 */
public class panelDashboardKetua extends javax.swing.JPanel {

    private int idLingkungan = -1;
    private int idPengguna = -1;
    private javax.swing.table.DefaultTableModel transaksiTableModel;
    private javax.swing.Timer autoRefreshTimer;

    // Konstruktor panel, inisialisasi komponen dan logika
    public panelDashboardKetua() {
        System.out.println("[DEBUG] Konstruktor panelDashboardKetua dipanggil");
        initComponents();
        initLogic();
        // loadDashboardData(); // Dihapus agar data hanya di-load setelah idLingkungan valid
    }

    // Inisialisasi logika tabel transaksi dan renderer
    private void initLogic() {
        transaksiTableModel = new javax.swing.table.DefaultTableModel(
            new Object[]{"Tanggal", "Tipe", "Jenis", "Jumlah", "Sumber", "Keterangan", "Status"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblTransaksiTerbaru.setModel(transaksiTableModel);
        // Set warna header tabel dengan custom renderer agar tidak diabaikan L&F
        javax.swing.table.JTableHeader header = tblTransaksiTerbaru.getTableHeader();
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
        for (int i = 0; i < tblTransaksiTerbaru.getColumnCount(); i++) {
            tblTransaksiTerbaru.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        // Custom renderer untuk pewarnaan status dan tipe
        tblTransaksiTerbaru.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String status = (String) table.getModel().getValueAt(row, 6);
                String tipe = (String) table.getModel().getValueAt(row, 1);
                if (column == 6 || column == 1) {
                    if ("ditolak".equalsIgnoreCase(status)) {
                        c.setForeground(java.awt.Color.RED);
                    } else if ("disetujui".equalsIgnoreCase(status)) {
                        c.setForeground(new java.awt.Color(0, 153, 0)); // hijau
                    } else {
                        c.setForeground(java.awt.Color.BLACK);
                    }
                } else {
                    c.setForeground(java.awt.Color.BLACK);
                }
                return c;
            }
        });
    }

    // Setter untuk idLingkungan dan idPengguna, lalu load data dashboard dan mulai auto-refresh
    public void setLingkunganDanPengguna(int idLingkungan, int idPengguna) {
        this.idLingkungan = idLingkungan;
        this.idPengguna = idPengguna;
        loadDashboardData();
        System.out.println("DashboardKetua: idLingkungan=" + idLingkungan + ", idPengguna=" + idPengguna);
        // Mulai/restart auto-refresh polling
        if (autoRefreshTimer != null) autoRefreshTimer.stop();
        autoRefreshTimer = new javax.swing.Timer(5000, e -> loadDashboardData());
        autoRefreshTimer.start();
    }

    // Mengambil dan menampilkan data dashboard (saldo, pending, warga, transaksi)
    private void loadDashboardData() {
        System.out.println("[DEBUG] loadDashboardData() dipanggil, idLingkungan=" + idLingkungan + ", idPengguna=" + idPengguna);
        if (idLingkungan <= 0) return;
        try {
            java.sql.Connection conn = database.Koneksi.getConnection();
            // Saldo
            String sqlSaldo = "SELECT COALESCE((SELECT SUM(jumlah) FROM transaksi WHERE id_lingkungan=? AND tipe='pemasukan'),0) - COALESCE((SELECT SUM(jumlah) FROM transaksi WHERE id_lingkungan=? AND tipe='pengeluaran' AND id IN (SELECT id_transaksi FROM verifikasi_transaksi WHERE status='disetujui')),0) AS saldo";
            java.sql.PreparedStatement psSaldo = conn.prepareStatement(sqlSaldo);
            psSaldo.setInt(1, idLingkungan);
            psSaldo.setInt(2, idLingkungan);
            java.sql.ResultSet rsSaldo = psSaldo.executeQuery();
            if (rsSaldo.next()) {
                int saldo = rsSaldo.getInt("saldo");
                System.out.println("[DEBUG] Saldo hasil query: " + saldo);
                lblSaldoLingkungan.setText("Rp." + String.format("%,d", saldo));
            } else {
                System.out.println("[DEBUG] Saldo hasil query: kosong");
                lblSaldoLingkungan.setText("Rp.0");
            }
            rsSaldo.close();
            psSaldo.close();

            // Menunggu Persetujuan
            String sqlPending = "SELECT COUNT(*) FROM verifikasi_transaksi v JOIN transaksi t ON v.id_transaksi=t.id WHERE t.id_lingkungan=? AND v.status='pending'";
            java.sql.PreparedStatement psPending = conn.prepareStatement(sqlPending);
            psPending.setInt(1, idLingkungan);
            java.sql.ResultSet rsPending = psPending.executeQuery();
            if (rsPending.next()) {
                int pending = rsPending.getInt(1);
                System.out.println("[DEBUG] Pending hasil query: " + pending);
                lblMenungguPersetujuan.setText(String.valueOf(pending));
            } else {
                System.out.println("[DEBUG] Pending hasil query: kosong");
                lblMenungguPersetujuan.setText("0");
            }
            rsPending.close();
            psPending.close();

            // Jumlah Warga
            String sqlWarga = "SELECT COUNT(*) FROM warga WHERE id_lingkungan=? AND status='aktif'";
            java.sql.PreparedStatement psWarga = conn.prepareStatement(sqlWarga);
            psWarga.setInt(1, idLingkungan);
            java.sql.ResultSet rsWarga = psWarga.executeQuery();
            if (rsWarga.next()) {
                int jumlahWarga = rsWarga.getInt(1);
                System.out.println("[DEBUG] Jumlah warga hasil query: " + jumlahWarga);
                lblJumlahWarga.setText(String.valueOf(jumlahWarga));
            } else {
                System.out.println("[DEBUG] Jumlah warga hasil query: kosong");
                lblJumlahWarga.setText("0");
            }
            rsWarga.close();
            psWarga.close();

            // Transaksi Terbaru
            transaksiTableModel.setRowCount(0);
            String sqlTransaksi = "SELECT t.tanggal, t.tipe, t.jenis, t.jumlah, t.sumber, t.keterangan, COALESCE(v.status, '-') as status FROM transaksi t LEFT JOIN verifikasi_transaksi v ON t.id = v.id_transaksi WHERE t.id_lingkungan=? ORDER BY t.tanggal DESC LIMIT 5";
            java.sql.PreparedStatement psTransaksi = conn.prepareStatement(sqlTransaksi);
            psTransaksi.setInt(1, idLingkungan);
            java.sql.ResultSet rsTransaksi = psTransaksi.executeQuery();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm");
            int rowCount = 0;
            while (rsTransaksi.next()) {
                String tanggal = sdf.format(rsTransaksi.getTimestamp("tanggal"));
                String tipe = rsTransaksi.getString("tipe");
                String jenis = rsTransaksi.getString("jenis");
                int jumlah = rsTransaksi.getInt("jumlah");
                String sumber = rsTransaksi.getString("sumber");
                String keterangan = rsTransaksi.getString("keterangan");
                String status = rsTransaksi.getString("status");
                transaksiTableModel.addRow(new Object[]{tanggal, tipe, jenis, String.format("Rp.%,d", jumlah), sumber, keterangan, status});
                rowCount++;
            }
            System.out.println("[DEBUG] Jumlah transaksi terbaru hasil query: " + rowCount);
            rsTransaksi.close();
            psTransaksi.close();
            System.out.println("[DEBUG] loadDashboardData() selesai tanpa error");
        } catch (Exception e) {
            lblSaldoLingkungan.setText("Rp.0");
            lblMenungguPersetujuan.setText("0");
            lblJumlahWarga.setText("0");
            transaksiTableModel.setRowCount(0);
            e.printStackTrace();
        }
    }

    // Menghentikan auto refresh dashboard
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
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblSaldoLingkungan = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblMenungguPersetujuan = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblJumlahWarga = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTransaksiTerbaru = new javax.swing.JTable();
        btnRefresh = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Dashboard");

        jLabel2.setText("Bendahara /");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Dashboard");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Lingkungan");

        jPanel1.setBackground(new java.awt.Color(0, 0, 51));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Saldo");

        jLabel11.setBackground(new java.awt.Color(0, 0, 0));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Dollar Bag.png"))); // NOI18N

        lblSaldoLingkungan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblSaldoLingkungan.setForeground(new java.awt.Color(255, 255, 255));
        lblSaldoLingkungan.setText("Rp.1.000.000,00");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                        .addGap(104, 104, 104))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblSaldoLingkungan)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addComponent(lblSaldoLingkungan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 0, 51));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Menunggu Persetujuan");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/What I Do.png"))); // NOI18N

        lblMenungguPersetujuan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblMenungguPersetujuan.setForeground(new java.awt.Color(255, 255, 255));
        lblMenungguPersetujuan.setText("10");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                        .addGap(8, 8, 8))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblMenungguPersetujuan)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addComponent(lblMenungguPersetujuan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(0, 0, 51));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Jumlah Warga");

        lblJumlahWarga.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblJumlahWarga.setForeground(new java.awt.Color(255, 255, 255));
        lblJumlahWarga.setText("24");

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/People_1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblJumlahWarga)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)))
                .addContainerGap(85, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(22, 22, 22)
                .addComponent(lblJumlahWarga)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Transaksi Terbaru");

        tblTransaksiTerbaru.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblTransaksiTerbaru);

        btnRefresh.setBackground(new java.awt.Color(0, 204, 204));
        btnRefresh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addGap(22, 22, 22))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnRefresh)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(7, 7, 7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
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
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9))
                    .addComponent(btnRefresh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Event handler tombol refresh dashboard
    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadDashboardData();
    }//GEN-LAST:event_btnRefreshActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefresh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JLabel lblJumlahWarga;
    private javax.swing.JLabel lblMenungguPersetujuan;
    private javax.swing.JLabel lblSaldoLingkungan;
    private javax.swing.JTable tblTransaksiTerbaru;
    // End of variables declaration//GEN-END:variables
}
