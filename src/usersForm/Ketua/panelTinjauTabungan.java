/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package usersForm.Ketua;

/**
 *
 * @author HYPE AMD
 */
public class panelTinjauTabungan extends javax.swing.JPanel {
      private int idLingkungan;
//    private int idLingkungan = -1;
//    private java.util.List<Integer> idWargaList = new java.util.ArrayList<>();
    private javax.swing.table.DefaultTableModel riwayatTableModel;

    // Konstruktor panel, inisialisasi komponen
    public panelTinjauTabungan() {
        initComponents();
    }

    // Setter untuk idLingkungan, inisialisasi logika dan load warga
    public void setIdLingkungan(int idLingkungan) {
        this.idLingkungan = idLingkungan;
        initLogic();
        loadWargaLingkungan();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cbWarga = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblSaldoTabunganWarga = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRiwayatTransaksiWarga = new javax.swing.JTable();

        setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Tinjau Tabungan");

        jLabel2.setText("Bendahara /");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Tinjau Tabungan");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Warga");

        cbWarga.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jPanel1.setBackground(new java.awt.Color(0, 153, 102));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Saldo Tabungan");

        jLabel11.setBackground(new java.awt.Color(0, 0, 0));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Dollar Bag.png"))); // NOI18N

        lblSaldoTabunganWarga.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblSaldoTabunganWarga.setForeground(new java.awt.Color(255, 255, 255));
        lblSaldoTabunganWarga.setText("Rp.0");

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
                        .addComponent(jLabel5))
                    .addComponent(lblSaldoTabunganWarga))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addComponent(lblSaldoTabunganWarga)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Riwayat Transaksi");

        tblRiwayatTransaksiWarga.setModel(new javax.swing.table.DefaultTableModel(
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
        // Set warna header tabel dengan custom renderer agar tidak diabaikan L&F
        javax.swing.table.JTableHeader header = tblRiwayatTransaksiWarga.getTableHeader();
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
        for (int i = 0; i < tblRiwayatTransaksiWarga.getColumnCount(); i++) {
            tblRiwayatTransaksiWarga.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        // Custom renderer untuk pewarnaan baris berdasarkan jenis
        tblRiwayatTransaksiWarga.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String jenis = (String) table.getModel().getValueAt(row, 1); // kolom 1 = Jenis
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
                setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                return c;
            }
        });
        jScrollPane1.setViewportView(tblRiwayatTransaksiWarga);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(cbWarga, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbWarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
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
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 442, Short.MAX_VALUE)
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
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Inisialisasi logika tabel riwayat dan event combo box
    private void initLogic() {
        riwayatTableModel = new javax.swing.table.DefaultTableModel(
            new Object[]{"Tanggal", "Jenis", "Jumlah", "Metode", "Keterangan"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblRiwayatTransaksiWarga.setModel(riwayatTableModel);
        // Set warna header tabel dengan custom renderer agar tidak diabaikan L&F
        javax.swing.table.JTableHeader header = tblRiwayatTransaksiWarga.getTableHeader();
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
        tblRiwayatTransaksiWarga.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String jenis = (String) table.getModel().getValueAt(row, 1); // kolom 1 = Jenis
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
                setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                return c;
            }
        });
        cbWarga.removeAllItems();
        cbWarga.addItem("Pilih Warga");
        cbWarga.addActionListener(e -> {
            if (cbWarga.getSelectedIndex() > 0) {
                String selected = (String) cbWarga.getSelectedItem();
                int idWarga = Integer.parseInt(selected.replaceAll(".*\\((\\d+)\\).*", "$1"));
                loadSaldoTabungan(idWarga);
                loadRiwayatTabungan(idWarga);
            } else {
                lblSaldoTabunganWarga.setText("Rp.0");
                riwayatTableModel.setRowCount(0);
            }
        });
    }
    
    // Mengisi combo box warga berdasarkan lingkungan
    private void loadWargaLingkungan() {
        cbWarga.removeAllItems();
        DataAccessObject.WargaDAO wargaDAO = new DataAccessObject.WargaDAO();
        java.util.List<model.Warga> list = wargaDAO.getAllWargaByLingkungan(idLingkungan);
        for (model.Warga w : list) {
            cbWarga.addItem(w.getNamaWarga() + " (" + w.getId() + ")");
        }
    }

    // Mengambil dan menampilkan saldo tabungan warga
    private void loadSaldoTabungan(int idWarga) {
        try {
            java.sql.Connection conn = database.Koneksi.getConnection();
            String sql = "SELECT saldo FROM saldo_tabungan WHERE id_warga=?";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idWarga);
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double saldo = rs.getDouble("saldo");
                lblSaldoTabunganWarga.setText("Rp." + String.format("%,.2f", saldo));
            } else {
                lblSaldoTabunganWarga.setText("Rp.0");
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            lblSaldoTabunganWarga.setText("Rp.0");
            e.printStackTrace();
        }
    }

    // Mengambil dan menampilkan riwayat tabungan warga
    private void loadRiwayatTabungan(int idWarga) {
        riwayatTableModel.setRowCount(0);
        try {
            java.sql.Connection conn = database.Koneksi.getConnection();
            String sql = "SELECT tanggal, jenis, jumlah, metode, keterangan FROM tabungan WHERE id_warga=? ORDER BY tanggal DESC";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idWarga);
            java.sql.ResultSet rs = ps.executeQuery();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm");
            while (rs.next()) {
                String tanggal = rs.getTimestamp("tanggal") != null ? sdf.format(rs.getTimestamp("tanggal")) : "-";
                String jenis = rs.getString("jenis");
                double jumlah = rs.getDouble("jumlah");
                String metode = rs.getString("metode");
                String keterangan = rs.getString("keterangan");
                riwayatTableModel.addRow(new Object[]{tanggal, jenis, String.format("Rp.%,.2f", jumlah), metode, keterangan});
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            riwayatTableModel.setRowCount(0);
            e.printStackTrace();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbWarga;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblSaldoTabunganWarga;
    private javax.swing.JTable tblRiwayatTransaksiWarga;
    // End of variables declaration//GEN-END:variables
}
