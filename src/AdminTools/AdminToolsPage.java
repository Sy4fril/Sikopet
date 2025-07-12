/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package AdminTools;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;

/**
 *
 * @author HYPE AMD
 */
public class AdminToolsPage extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AdminToolsPage.class.getName());
    private pnlAddLingkungan addL;
    private pnlDashboardAdmin dashboardAdmin;
    private pnl_konfigSistem konfigsistem;
    private pnlTambahUser addUser;
    
    public AdminToolsPage() {
        initComponents();
        setLocationRelativeTo(null);
        tampilkanTanggalDanWaktu();
        inisiasiPanel();
        // Autentikasi master key terlebih dahulu menggunakan custom dialog
        MasterKeyDialog dialog = new MasterKeyDialog(this);
        dialog.setVisible(true);
        
        if (!dialog.isAuthenticated()) {
            // Jika autentikasi gagal, tutup aplikasi
            System.exit(0);
        }
        
        setTitle("Admin Tools - Sikopet");
        setLocationRelativeTo(null);
        tampilkanTanggalDanWaktu();
        
        // Set nama admin yang berhasil login
        String adminName = dialog.getAdminName();
        if (adminName != null && !adminName.trim().isEmpty()) {
            lblNamaAdmin.setText(adminName);
        }
    }
    
    private void inisiasiPanel(){
        addL = new pnlAddLingkungan();
        dashboardAdmin = new pnlDashboardAdmin();
        konfigsistem = new pnl_konfigSistem();
        addUser = new pnlTambahUser();
        
        // menambahkan panel ke panel
        actionPanel.add(addL);
        actionPanel.add(dashboardAdmin);
        actionPanel.add(konfigsistem);
        actionPanel.add(addUser);
        
        addL.setVisible(false);
        dashboardAdmin.setVisible(true);
        konfigsistem.setVisible(false);
        addUser.setVisible(false);
    }
    
    
    private void tampilkanTanggalDanWaktu() {
        // Tanggal
        SimpleDateFormat tanggalFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new java.util.Locale("id", "ID"));
        lblTanggal.setText(tanggalFormat.format(new Date()));

        // Timer update waktu
        Timer timer = new Timer(1000, e -> {
            SimpleDateFormat waktuFormat = new SimpleDateFormat("HH:mm:ss");
            lblWaktu.setText(waktuFormat.format(new Date()) + " WITA");
        });
        timer.start();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_sideBar = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblNamaAdmin = new javax.swing.JLabel();
        btnExit = new javax.swing.JButton();
        btn_dashboard = new javax.swing.JButton();
        btnAddLingkungan = new javax.swing.JButton();
        btnAddUser = new javax.swing.JButton();
        btnKonfigSistem = new javax.swing.JButton();
        lblTanggal = new javax.swing.JLabel();
        lblWaktu = new javax.swing.JLabel();
        pnl_content = new javax.swing.JPanel();
        pnl_header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        actionPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));

        pnl_sideBar.setBackground(new java.awt.Color(33, 52, 72));
        pnl_sideBar.setPreferredSize(new java.awt.Dimension(250, 342));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Administrator Male_2.png"))); // NOI18N
        jLabel2.setText("jLabel2");

        lblNamaAdmin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNamaAdmin.setForeground(new java.awt.Color(245, 245, 245));
        lblNamaAdmin.setText("Admin");

        btnExit.setBackground(new java.awt.Color(204, 0, 51));
        btnExit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnExit.setForeground(new java.awt.Color(245, 245, 245));
        btnExit.setText("EXIT");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btn_dashboard.setBackground(new java.awt.Color(0, 102, 102));
        btn_dashboard.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_dashboard.setForeground(new java.awt.Color(255, 255, 255));
        btn_dashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Menu.png"))); // NOI18N
        btn_dashboard.setText("Dashboard");
        btn_dashboard.setBorder(null);
        btn_dashboard.setBorderPainted(false);
        btn_dashboard.setHideActionText(true);
        btn_dashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dashboardActionPerformed(evt);
            }
        });

        btnAddLingkungan.setBackground(new java.awt.Color(0, 102, 102));
        btnAddLingkungan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAddLingkungan.setForeground(new java.awt.Color(255, 255, 255));
        btnAddLingkungan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/House With a Garden.png"))); // NOI18N
        btnAddLingkungan.setText("Manajemen Lingkungan");
        btnAddLingkungan.setBorder(null);
        btnAddLingkungan.setBorderPainted(false);
        btnAddLingkungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddLingkunganActionPerformed(evt);
            }
        });

        btnAddUser.setBackground(new java.awt.Color(0, 102, 102));
        btnAddUser.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAddUser.setForeground(new java.awt.Color(255, 255, 255));
        btnAddUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/People_1.png"))); // NOI18N
        btnAddUser.setText("Tambah Pengguna Baru");
        btnAddUser.setBorder(null);
        btnAddUser.setBorderPainted(false);
        btnAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUserActionPerformed(evt);
            }
        });

        btnKonfigSistem.setBackground(new java.awt.Color(0, 102, 102));
        btnKonfigSistem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnKonfigSistem.setForeground(new java.awt.Color(255, 255, 255));
        btnKonfigSistem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Administrative Tools.png"))); // NOI18N
        btnKonfigSistem.setText("Konfigurasi Sistem");
        btnKonfigSistem.setBorder(null);
        btnKonfigSistem.setBorderPainted(false);
        btnKonfigSistem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKonfigSistemActionPerformed(evt);
            }
        });

        lblTanggal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTanggal.setForeground(new java.awt.Color(255, 255, 255));
        lblTanggal.setText("Tanggal");

        lblWaktu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblWaktu.setForeground(new java.awt.Color(255, 255, 255));
        lblWaktu.setText("Jam");

        javax.swing.GroupLayout pnl_sideBarLayout = new javax.swing.GroupLayout(pnl_sideBar);
        pnl_sideBar.setLayout(pnl_sideBarLayout);
        pnl_sideBarLayout.setHorizontalGroup(
            pnl_sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_sideBarLayout.createSequentialGroup()
                .addGroup(pnl_sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_sideBarLayout.createSequentialGroup()
                        .addGroup(pnl_sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_sideBarLayout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnl_sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnExit)
                                    .addComponent(lblNamaAdmin)))
                            .addGroup(pnl_sideBarLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblTanggal)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_sideBarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnl_sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_dashboard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAddLingkungan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                            .addComponent(btnAddUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                            .addComponent(btnKonfigSistem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(pnl_sideBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblWaktu)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_sideBarLayout.setVerticalGroup(
            pnl_sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_sideBarLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(pnl_sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel2)
                    .addGroup(pnl_sideBarLayout.createSequentialGroup()
                        .addComponent(lblNamaAdmin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExit)))
                .addGap(29, 29, 29)
                .addComponent(btn_dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAddLingkungan, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnKonfigSistem, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 212, Short.MAX_VALUE)
                .addComponent(lblTanggal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblWaktu)
                .addContainerGap())
        );

        getContentPane().add(pnl_sideBar, java.awt.BorderLayout.LINE_START);

        pnl_content.setLayout(new java.awt.BorderLayout());

        pnl_header.setBackground(new java.awt.Color(84, 119, 146));
        pnl_header.setPreferredSize(new java.awt.Dimension(550, 70));

        jLabel1.setFont(new java.awt.Font("Kristen ITC", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Sikopet - Private Admin Tools");

        javax.swing.GroupLayout pnl_headerLayout = new javax.swing.GroupLayout(pnl_header);
        pnl_header.setLayout(pnl_headerLayout);
        pnl_headerLayout.setHorizontalGroup(
            pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_headerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(288, 288, 288))
        );
        pnl_headerLayout.setVerticalGroup(
            pnl_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_headerLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );

        pnl_content.add(pnl_header, java.awt.BorderLayout.PAGE_START);

        actionPanel.setLayout(new java.awt.CardLayout());
        pnl_content.add(actionPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnl_content, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_dashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dashboardActionPerformed
        addL.setVisible(false);
        dashboardAdmin.setVisible(true);
        konfigsistem.setVisible(false);
        addUser.setVisible(false);
    }//GEN-LAST:event_btn_dashboardActionPerformed

    private void btnAddLingkunganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddLingkunganActionPerformed
        addL.setVisible(true);
        dashboardAdmin.setVisible(false);
        konfigsistem.setVisible(false);
        addUser.setVisible(false);
    }//GEN-LAST:event_btnAddLingkunganActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUserActionPerformed
        addL.setVisible(false);
        dashboardAdmin.setVisible(false);
        konfigsistem.setVisible(false);
        addUser.setVisible(true);
    }//GEN-LAST:event_btnAddUserActionPerformed

    private void btnKonfigSistemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKonfigSistemActionPerformed
        addL.setVisible(false);
        dashboardAdmin.setVisible(false);
        konfigsistem.setVisible(true);
        addUser.setVisible(false);
    }//GEN-LAST:event_btnKonfigSistemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new AdminToolsPage().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionPanel;
    private javax.swing.JButton btnAddLingkungan;
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnKonfigSistem;
    private javax.swing.JButton btn_dashboard;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblNamaAdmin;
    private javax.swing.JLabel lblTanggal;
    private javax.swing.JLabel lblWaktu;
    private javax.swing.JPanel pnl_content;
    private javax.swing.JPanel pnl_header;
    private javax.swing.JPanel pnl_sideBar;
    // End of variables declaration//GEN-END:variables
}
