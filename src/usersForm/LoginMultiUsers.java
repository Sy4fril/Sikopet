/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package usersForm;

import DataAccessObject.PenggunaDAO;
import DataAccessObject.WargaDAO;
import DataAccessObject.LingkunganDAO;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import model.Pengguna;
import model.Warga;
import model.Lingkungan;
import javax.swing.JOptionPane;
import usersForm.Warga.WargaPage;

/**
 *
 * @author HYPE AMD
 */
public class LoginMultiUsers extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LoginMultiUsers.class.getName());

    private boolean isAuthenticated = false;
    private Pengguna authenticatedPengguna = null;
    private Warga authenticatedWarga = null;
    private Lingkungan authenticatedLingkungan = null;
    
    public LoginMultiUsers() {
        this((java.awt.Frame)null, false);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    public LoginMultiUsers(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTitle("Login Page");
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    public LoginMultiUsers(WargaPage parent) {
        this(parent, true); // Modal dialog
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        // Tambahkan listener jendela untuk menangani penutupan jendela
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                // Jika pengguna menutup jendela tanpa login, keluar dari aplikasi
                int choice = JOptionPane.showConfirmDialog(LoginMultiUsers.this, 
                    "Apakah Anda yakin ingin keluar dari aplikasi?", 
                    "Konfirmasi Keluar", 
                    JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        
        // Set parent window to be disabled while this dialog is open
        if (parent != null) {
            parent.setEnabled(false);
            addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    parent.setEnabled(true);
                }
            });
        }
    }

    public LoginMultiUsers(usersForm.Ketua.KetuaToolsPage parent) {
        this(parent, true); // Modal dialog
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        // Tambahkan listener jendela untuk menangani penutupan jendela
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                // Jika pengguna menutup jendela tanpa login, keluar dari aplikasi
                int choice = JOptionPane.showConfirmDialog(LoginMultiUsers.this, 
                    "Apakah Anda yakin ingin keluar dari aplikasi?", 
                    "Konfirmasi Keluar", 
                    JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        
        // Set parent window to be disabled while this dialog is open
        if (parent != null) {
            parent.setEnabled(false);
            addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    parent.setEnabled(true);
                }
            });
        }
    }
    
    public LoginMultiUsers(usersForm.Bendahara.BendaharaToolsPage parent) {
        this(parent, true); // Modal dialog
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        // Tambahkan listener jendela untuk menangani penutupan jendela
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                // Jika pengguna menutup jendela tanpa login, keluar dari aplikasi
                int choice = JOptionPane.showConfirmDialog(LoginMultiUsers.this, 
                    "Apakah Anda yakin ingin keluar dari aplikasi?", 
                    "Konfirmasi Keluar", 
                    JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        
        // Set jendela induk (parent) menjadi tidak aktif selama dialog ini terbuka
        if (parent != null) {
            parent.setEnabled(false);
            addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    parent.setEnabled(true);
                }
            });
        }
    }

    private void authenticate(){
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email dan password tidak boleh kosong!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PenggunaDAO penggunaDAO = new PenggunaDAO();
        Pengguna pengguna = penggunaDAO.login(email, password);

        if (pengguna == null) {
            JOptionPane.showMessageDialog(this, "Email atau password salah atau akun tidak aktif!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            txtEmail.setText("");
            txtPassword.setText("");
            return;
        }

        if (!"aktif".equalsIgnoreCase(pengguna.getStatus())) {
            JOptionPane.showMessageDialog(this, "Akun Anda tidak aktif!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            txtEmail.setText("");
            txtPassword.setText("");
            return;
        }

        WargaDAO wargaDAO = new WargaDAO();
        Warga warga = wargaDAO.getWargaByPenggunaId(pengguna.getId());
        Lingkungan lingkungan = null;
        if (warga != null) {
            LingkunganDAO lingkunganDAO = new LingkunganDAO();
            lingkungan = lingkunganDAO.getLingkunganById(warga.getIdLingkungan());
        } else {
            // Jika tidak ada data warga, fallback ke lingkungan dari pengguna
            LingkunganDAO lingkunganDAO = new LingkunganDAO();
            lingkungan = lingkunganDAO.getLingkunganById(pengguna.getIdLingkungan());
        }

        String role = pengguna.getRole();
        switch (role.toLowerCase()) {
            case "warga":
                // Set authentication data
                this.isAuthenticated = true;
                this.authenticatedPengguna = pengguna;
                this.authenticatedWarga = warga;
                this.authenticatedLingkungan = lingkungan;
                
                // Tampilkan pesan sukses
                JOptionPane.showMessageDialog(this, 
                    "Login berhasil! Selamat datang " + pengguna.getNamaPengguna(), 
                    "Login Sukses", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                this.dispose();
                // Buka dashboard warga
                new usersForm.Warga.WargaPage(authenticatedPengguna, authenticatedWarga, authenticatedLingkungan).setVisible(true);
                break;
            case "bendahara":
                // Set authentication data untuk modal dialog
                this.isAuthenticated = true;
                this.authenticatedPengguna = pengguna;
                this.authenticatedWarga = warga;
                this.authenticatedLingkungan = lingkungan;
                
                JOptionPane.showMessageDialog(this, 
                    "Login berhasil! Selamat datang " + pengguna.getNamaPengguna(), 
                    "Login Sukses", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                this.dispose();
                // Buka dashboard bendahara
                new usersForm.Bendahara.BendaharaToolsPage(authenticatedPengguna, authenticatedWarga, authenticatedLingkungan).setVisible(true);
                break;
            case "ketua":
                // Set authentication data untuk modal dialog
                this.isAuthenticated = true;
                this.authenticatedPengguna = pengguna;
                this.authenticatedWarga = warga;
                this.authenticatedLingkungan = lingkungan;
                
                JOptionPane.showMessageDialog(this, 
                    "Login berhasil! Selamat datang " + pengguna.getNamaPengguna(), 
                    "Login Sukses", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                this.dispose();
                // Buka dashboard ketua
                new usersForm.Ketua.KetuaToolsPage(authenticatedPengguna, authenticatedWarga, authenticatedLingkungan).setVisible(true);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Role tidak dikenali!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
                return;
        }
    }
    
    public boolean isAuthenticated() {
        return isAuthenticated;
    }
    
    public Pengguna getAuthenticatedPengguna() {
        return authenticatedPengguna;
    }
    
    public Warga getAuthenticatedWarga() {
        return authenticatedWarga;
    }
    
    public Lingkungan getAuthenticatedLingkungan() {
        return authenticatedLingkungan;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        hideSeekPass = new javax.swing.JToggleButton();
        jLabel4 = new javax.swing.JLabel();
        daftarLingkungan = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 0, 51));

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setForeground(new java.awt.Color(0, 0, 51));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Login Yuk!");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Email");

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Password");

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });

        btnLogin.setBackground(new java.awt.Color(0, 0, 153));
        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        hideSeekPass.setBackground(new java.awt.Color(0, 102, 102));
        hideSeekPass.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        hideSeekPass.setForeground(new java.awt.Color(255, 255, 255));
        hideSeekPass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Hide.png"))); // NOI18N
        hideSeekPass.setText("Show");
        hideSeekPass.setBorder(null);
        hideSeekPass.setBorderPainted(false);
        hideSeekPass.setContentAreaFilled(false);
        hideSeekPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hideSeekPassActionPerformed(evt);
            }
        });

        jLabel4.setText("Belum memiliki akun?");

        daftarLingkungan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        daftarLingkungan.setForeground(new java.awt.Color(0, 0, 153));
        daftarLingkungan.setText("Daftar di Sini");
        daftarLingkungan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                daftarLingkunganMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(daftarLingkungan)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPassword)
                            .addComponent(txtEmail)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(hideSeekPass, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(75, 75, 75))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(64, 64, 64)))))
                .addGap(75, 75, 75))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(53, 53, 53)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(hideSeekPass, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnLogin)
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(daftarLingkungan))
                .addGap(152, 152, 152))
        );

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/sikopetIcon.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Sikopet");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(197, 197, 197)
                        .addComponent(jLabel6)))
                .addGap(82, 82, 82)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(137, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addGap(120, 120, 120))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        txtEmail.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    txtPassword.requestFocus();
                }
            }
        });
    }//GEN-LAST:event_txtEmailActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        authenticate();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void daftarLingkunganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_daftarLingkunganMouseClicked
        try {
            // Nomor WhatsApp yang akan dituju (ganti dengan nomor yang diinginkan)
            String phoneNumber = "6281244910625"; // Format: 628xxx tanpa tanda +
            String message = "Halo, saya adalah seorang ketua RT dan saya ingin mendaftarkan lingkungan saya ke dalam aplikasi anda.";
            
            // Membuat URL WhatsApp dengan pesan
            String whatsappURL = "https://wa.me/" + phoneNumber + "?text=" + 
                               java.net.URLEncoder.encode(message, "UTF-8");
            
            // Membuka browser dengan URL WhatsApp
            java.awt.Desktop.getDesktop().browse(new java.net.URI(whatsappURL));
            
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error membuka WhatsApp", e);
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Gagal membuka WhatsApp. Pastikan browser terinstall.", 
                "Error", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_daftarLingkunganMouseClicked

    private void hideSeekPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hideSeekPassActionPerformed

        if (hideSeekPass.isSelected()) {
            // Tampilkan password
            txtPassword.setEchoChar((char) 0);
            hideSeekPass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/eye.png")));
            hideSeekPass.setText("Hide");
        } else {
            // Sembunyikan password
            txtPassword.setEchoChar('•');
            hideSeekPass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/hide.png")));
            hideSeekPass.setText("Show");
        }
    }//GEN-LAST:event_hideSeekPassActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // Tombol Enter pada field password
        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    authenticate();
                }
            }
        });
    }//GEN-LAST:event_txtPasswordActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> {
            LoginMultiUsers dialog = new LoginMultiUsers();
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel daftarLingkungan;
    private javax.swing.JToggleButton hideSeekPass;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
