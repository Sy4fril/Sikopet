/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package usersForm.Bendahara;
import DataAccessObject.WargaDAO;

/**
 *
 * @author HYPE AMD
 */
public class panelTabunganWarga extends javax.swing.JPanel {

    // Konstruktor panel, inisialisasi komponen
    public panelTabunganWarga() {
        initComponents();
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
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cbWargaNarik = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        txtNominalNarik = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        taKetNarik = new javax.swing.JTextArea();
        btnNarik = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbWargaNabung = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txtNominalNabung = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taKetNabung = new javax.swing.JTextArea();
        btnNabung = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taOutputTabungan = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Tabungan");

        jLabel2.setText("Bendahara /");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Tabungan");

        jPanel1.setBackground(new java.awt.Color(245, 245, 245));

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setText("Menarik");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Warga");

        cbWargaNarik.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Nominal");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Keterangan");

        taKetNarik.setColumns(20);
        taKetNarik.setRows(5);
        jScrollPane3.setViewportView(taKetNarik);

        btnNarik.setBackground(new java.awt.Color(255, 0, 0));
        btnNarik.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnNarik.setText("TARIK");
        btnNarik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNarikActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbWargaNarik, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNominalNarik)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnNarik, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbWargaNarik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNominalNarik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNarik)
                .addContainerGap(123, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 255, 51));
        jLabel5.setText("Menabung");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Warga");

        cbWargaNabung.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Nominal");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Keterangan");

        taKetNabung.setColumns(20);
        taKetNabung.setRows(5);
        jScrollPane1.setViewportView(taKetNabung);

        btnNabung.setBackground(new java.awt.Color(51, 255, 51));
        btnNabung.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnNabung.setText("TABUNG");
        btnNabung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNabungActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbWargaNabung, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNominalNabung)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnNabung, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbWargaNabung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNominalNabung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNabung)
                .addContainerGap(123, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Output");

        taOutputTabungan.setColumns(20);
        taOutputTabungan.setRows(5);
        jScrollPane2.setViewportView(taOutputTabungan);

        jButton1.setBackground(new java.awt.Color(0, 153, 153));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private int idLingkungan;
    private int idPengguna; // jika perlu log aktivitas

    // Setter untuk idLingkungan dan load combo warga
    public void setIdLingkungan(int idLingkungan) {
        this.idLingkungan = idLingkungan;
        loadComboWarga();
    }

    // Setter untuk idLingkungan dari parent (misal: BendaharaToolsPage)
    public void setIdLingkunganFromParent(int idLingkungan) {
        setIdLingkungan(idLingkungan);
    }

    // Mengisi combo box warga berdasarkan lingkungan
    private void loadComboWarga() {
          System.out.println("DEBUG idLingkungan = " + idLingkungan);
             
        cbWargaNabung.removeAllItems();
        cbWargaNarik.removeAllItems();
        DataAccessObject.WargaDAO wargaDAO = new DataAccessObject.WargaDAO();
        java.util.List<model.Warga> list = wargaDAO.getAllWargaByLingkungan(idLingkungan);
        System.out.println("DEBUG jumlah warga = " + (list != null ? list.size() : "null"));
        for (model.Warga w : list) {
            cbWargaNabung.addItem(w.getNamaWarga() + " (" + w.getId() + ")");
            cbWargaNarik.addItem(w.getNamaWarga() + " (" + w.getId() + ")");
        }
    }

    // Setter untuk idPengguna
    public void setIdPengguna(int idPengguna) {
        this.idPengguna = idPengguna;
    }

    // Event handler tombol tabung
    private void btnNabungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNabungActionPerformed
        String[] options = {"Manual", "QRIS"};
        int choice = javax.swing.JOptionPane.showOptionDialog(
            this,
            "Pilih metode tabungan:",
            "Metode Tabungan",
            javax.swing.JOptionPane.DEFAULT_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
        if (choice == 1) { // QRIS
            javax.swing.ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/icons/qrDana.jpg"));
            javax.swing.JLabel label = new javax.swing.JLabel(icon);
            javax.swing.JOptionPane.showMessageDialog(this, label, "Scan QRIS Dana", javax.swing.JOptionPane.PLAIN_MESSAGE);
        } else if (choice == 0) {
            int idx = cbWargaNabung.getSelectedIndex();
            if (idx < 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Pilih warga terlebih dahulu!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
            String selected = (String) cbWargaNabung.getSelectedItem();
            int idWarga = Integer.parseInt(selected.replaceAll(".*\\((\\d+)\\).*", "$1"));
            String namaWarga = selected.replaceAll("\\s*\\(\\d+\\)", "");
            String nominalStr = txtNominalNabung.getText().trim().replaceAll("[^\\d.]", "");
            String ket = taKetNabung.getText().trim();
            if (nominalStr.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, "Nominal tidak boleh kosong!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
            double nominal;
            try {
                nominal = Double.parseDouble(nominalStr);
                if (nominal <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Nominal harus berupa angka lebih dari 0!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                java.sql.Connection conn = database.Koneksi.getConnection();
                String sql = "INSERT INTO tabungan (id_warga, jenis, jumlah, tanggal, metode, keterangan) VALUES (?, 'setoran', ?, NOW(), 'tunai', ?)";
                java.sql.PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, idWarga);
                ps.setDouble(2, nominal);
                ps.setString(3, ket);
                int result = ps.executeUpdate();
                if (result > 0) {
                    // Update saldo_tabungan setelah setoran
                    String cekSql = "SELECT saldo FROM saldo_tabungan WHERE id_warga = ?";
                    java.sql.PreparedStatement cekPs = conn.prepareStatement(cekSql);
                    cekPs.setInt(1, idWarga);
                    java.sql.ResultSet rs = cekPs.executeQuery();
                    if (rs.next()) {
                        // Sudah ada, update saldo
                        String updateSql = "UPDATE saldo_tabungan SET saldo = saldo + ?, last_update = NOW() WHERE id_warga = ?";
                        java.sql.PreparedStatement updatePs = conn.prepareStatement(updateSql);
                        updatePs.setDouble(1, nominal);
                        updatePs.setInt(2, idWarga);
                        updatePs.executeUpdate();
                        updatePs.close();
                    } else {
                        // Belum ada, insert baru
                        String insertSql = "INSERT INTO saldo_tabungan (id_warga, saldo, last_update) VALUES (?, ?, NOW())";
                        java.sql.PreparedStatement insertPs = conn.prepareStatement(insertSql);
                        insertPs.setInt(1, idWarga);
                        insertPs.setDouble(2, nominal);
                        insertPs.executeUpdate();
                        insertPs.close();
                    }
                    rs.close();
                    cekPs.close();
                    database.Koneksi.commit();
                    javax.swing.JOptionPane.showMessageDialog(this, "Setoran tabungan manual berhasil dicatat!", "Sukses", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    // Output ke text area
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String timestamp = sdf.format(new java.util.Date());
                    String output = String.format("[%s] Jenis: setoran\nWarga: %s\nNominal: Rp %,.0f\nMetode: tunai\nKeterangan: %s\n----------------------------------------\n",
                        timestamp, namaWarga, nominal, ket);
                    taOutputTabungan.append(output);
                } else {
                    database.Koneksi.rollback();
                    javax.swing.JOptionPane.showMessageDialog(this, "Gagal mencatat setoran tabungan!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
                ps.close();
            } catch (Exception e) {
                try { database.Koneksi.rollback(); } catch (Exception ex) {}
                javax.swing.JOptionPane.showMessageDialog(this, "Error database: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnNabungActionPerformed

    // Event handler tombol tarik
    private void btnNarikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNarikActionPerformed
        String[] options = {"Tunai", "Transfer Rekening"};
        int choice = javax.swing.JOptionPane.showOptionDialog(
            this,
            "Pilih metode penarikan:",
            "Metode Penarikan",
            javax.swing.JOptionPane.DEFAULT_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
        int idx = cbWargaNarik.getSelectedIndex();
        if (idx < 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Pilih warga terlebih dahulu!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        String selected = (String) cbWargaNarik.getSelectedItem();
        int idWarga = Integer.parseInt(selected.replaceAll(".*\\((\\d+)\\).*", "$1"));
        String namaWarga = selected.replaceAll("\\s*\\(\\d+\\)", "");
        String nominalStr = txtNominalNarik.getText().trim().replaceAll("[^\\d.]", "");
        String ket = taKetNarik.getText().trim();
        if (nominalStr.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Nominal tidak boleh kosong!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        double nominal;
        try {
            nominal = Double.parseDouble(nominalStr);
            if (nominal <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Nominal harus berupa angka lebih dari 0!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        String metode = "tunai";
        String keterangan = ket;
        if (choice == 1) { // Transfer Rekening
            javax.swing.JPanel panel = new javax.swing.JPanel();
            javax.swing.JComboBox<String> cbBank = new javax.swing.JComboBox<>(new String[]{"BCA", "BRI", "BNI", "Mandiri", "CIMB Niaga", "Bank Lain"});
            javax.swing.JTextField tfRek = new javax.swing.JTextField(15);
            panel.setLayout(new java.awt.GridLayout(2, 2, 5, 5));
            panel.add(new javax.swing.JLabel("Bank Tujuan:"));
            panel.add(cbBank);
            panel.add(new javax.swing.JLabel("No. Rekening:"));
            panel.add(tfRek);
            int result = javax.swing.JOptionPane.showConfirmDialog(this, panel, "Input Transfer Rekening", javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);
            if (result != javax.swing.JOptionPane.OK_OPTION) return;
            String bank = (String) cbBank.getSelectedItem();
            String norek = tfRek.getText();
            if (norek.trim().isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, "Nomor rekening tidak boleh kosong!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
            metode = "transfer";
            keterangan = (ket.isEmpty() ? "" : ket + " | ") + "Transfer ke bank " + bank + ", No. Rek: " + norek;
        }
        // Cek saldo sebelum penarikan
        try {
            java.sql.Connection conn = database.Koneksi.getConnection();
            String cekSql = "SELECT saldo FROM saldo_tabungan WHERE id_warga = ?";
            java.sql.PreparedStatement cekPs = conn.prepareStatement(cekSql);
            cekPs.setInt(1, idWarga);
            java.sql.ResultSet rs = cekPs.executeQuery();
            double saldo = 0;
            if (rs.next()) {
                saldo = rs.getDouble("saldo");
            }
            rs.close();
            cekPs.close();
            if (saldo < nominal) {
                javax.swing.JOptionPane.showMessageDialog(this, "Saldo tabungan tidak cukup!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Insert ke tabel tabungan
            String sql = "INSERT INTO tabungan (id_warga, jenis, jumlah, tanggal, metode, keterangan) VALUES (?, 'penarikan', ?, NOW(), ?, ?)";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idWarga);
            ps.setDouble(2, nominal);
            ps.setString(3, metode);
            ps.setString(4, keterangan);
            int result = ps.executeUpdate();
            if (result > 0) {
                // Update saldo_tabungan setelah penarikan
                String updateSql = "UPDATE saldo_tabungan SET saldo = saldo - ?, last_update = NOW() WHERE id_warga = ?";
                java.sql.PreparedStatement updatePs = conn.prepareStatement(updateSql);
                updatePs.setDouble(1, nominal);
                updatePs.setInt(2, idWarga);
                updatePs.executeUpdate();
                updatePs.close();
                database.Koneksi.commit();
                javax.swing.JOptionPane.showMessageDialog(this, "Penarikan tabungan berhasil dicatat!", "Sukses", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                // Output ke text area
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String timestamp = sdf.format(new java.util.Date());
                String output = String.format("[%s] Jenis: penarikan\nWarga: %s\nNominal: Rp %,.0f\nMetode: %s\nKeterangan: %s\n----------------------------------------\n",
                    timestamp, namaWarga, nominal, metode, keterangan);
                taOutputTabungan.append(output);
            } else {
                database.Koneksi.rollback();
                javax.swing.JOptionPane.showMessageDialog(this, "Gagal mencatat penarikan tabungan!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
            ps.close();
        } catch (Exception e) {
            try { database.Koneksi.rollback(); } catch (Exception ex) {}
            javax.swing.JOptionPane.showMessageDialog(this, "Error database: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnNarikActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Ambil parent window dan set idLingkungan dari parent jika memungkinkan
        java.awt.Window parent = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (parent instanceof usersForm.Bendahara.BendaharaToolsPage) {
            int idLingkungan = ((usersForm.Bendahara.BendaharaToolsPage)parent).getIdLingkunganAktif();
            setIdLingkunganFromParent(idLingkungan);
        } else {
            // fallback jika parent bukan BendaharaToolsPage
            loadComboWarga();
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNabung;
    private javax.swing.JButton btnNarik;
    private javax.swing.JComboBox<String> cbWargaNabung;
    private javax.swing.JComboBox<String> cbWargaNarik;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea taKetNabung;
    private javax.swing.JTextArea taKetNarik;
    private javax.swing.JTextArea taOutputTabungan;
    private javax.swing.JTextField txtNominalNabung;
    private javax.swing.JTextField txtNominalNarik;
    // End of variables declaration//GEN-END:variables
}
