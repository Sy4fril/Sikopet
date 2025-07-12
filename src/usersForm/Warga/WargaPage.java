/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package usersForm.Warga;

import model.Pengguna;
import model.Warga;
import model.Lingkungan;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import DataAccessObject.WargaDAO;
import DataAccessObject.LingkunganDAO;
import usersForm.LoginMultiUsers;

/**
 *
 * @author HYPE AMD
 */
public class WargaPage extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(WargaPage.class.getName());
    
    private Penarikan penarikan;
    private Setoran setoran;
    private TabunganWarga tabungan;
    private notif notif;
    
    private Pengguna pengguna;
    private Warga warga;
    private Lingkungan lingkungan;
    private JPanel activeSidebarPanel;

    /**
     * Creates new form WargaPage
     */
    public WargaPage() {
        // Autentikasi terlebih dahulu sebelum inisialisasi UI
        if (!performAuthentication()) {
            // Jika autentikasi gagal, tutup aplikasi
            System.exit(0);
        }
        
        // Validasi role - hanya warga yang boleh mengakses halaman ini
        if (pengguna != null && !"warga".equalsIgnoreCase(pengguna.getRole())) {
            JOptionPane.showMessageDialog(null, 
                "Anda tidak memiliki akses ke halaman ini. Role Anda: " + pengguna.getRole(), 
                "Akses Ditolak", 
                JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        
        // Jika autentikasi berhasil, inisialisasi UI
        initComponents();
        setLocationRelativeTo(null);
        activeSidebarPanel = pnlTabungan;
        updateSidebarHighlight(pnlTabungan);
        tampilkanTanggalDanWaktu();
        
        // Inisialisasi UI dengan data user yang sudah login
        initializeUserData();
    }

    // Konstruktor untuk login multi user (dipanggil dari LoginMultiUsers)
    public WargaPage(Pengguna pengguna, Warga warga, Lingkungan lingkungan) {
        initComponents();
        setLocationRelativeTo(null);
        activeSidebarPanel = pnlTabungan;
        updateSidebarHighlight(pnlTabungan);
        tampilkanTanggalDanWaktu();
        
        this.pengguna = pengguna;
        this.warga = warga;
        this.lingkungan = lingkungan;
        
        // Inisialisasi UI dengan data user
        initializeUserData();
    }
    
    /**
     * Melakukan autentikasi user sebelum menampilkan UI
     * @return true jika autentikasi berhasil, false jika gagal
     */
    private boolean performAuthentication() {
        // Buat dialog login yang modal
        LoginMultiUsers dialog = new LoginMultiUsers(this);
        dialog.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        
        // Tambahkan window listener untuk menangani penutupan window
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                // Jika user menutup window tanpa login, exit aplikasi
                int choice = JOptionPane.showConfirmDialog(dialog, 
                    "Apakah Anda yakin ingin keluar dari aplikasi?", 
                    "Konfirmasi Keluar", 
                    JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        
        // Tampilkan dialog dan tunggu sampai ditutup
        dialog.setVisible(true);
        
        // Tunggu sampai dialog ditutup
        while (dialog.isVisible()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        // Cek hasil autentikasi
        if (!dialog.isAuthenticated()) {
            // Jika autentikasi gagal, tutup aplikasi
            JOptionPane.showMessageDialog(null, 
                "Autentikasi gagal atau dibatalkan. Aplikasi akan ditutup.", 
                "Autentikasi Gagal", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Ambil data user yang sudah login
        this.pengguna = dialog.getAuthenticatedPengguna();
        this.warga = dialog.getAuthenticatedWarga();
        this.lingkungan = dialog.getAuthenticatedLingkungan();
        
        return true;
    }
    
    private void initializeUserData() {
        if (pengguna != null) {
            lblNamaUser.setText(pengguna.getNamaPengguna());
            System.out.println("[DEBUG] Pengguna ID: " + pengguna.getId());
            
            if (warga != null) {
                System.out.println("[DEBUG] Warga: " + warga.getNamaWarga() + ", id_lingkungan=" + warga.getIdLingkungan());
                if (lingkungan != null) {
                    lblLingkungan.setText(lingkungan.getNamaDaerah());
                    lblRTRW.setText("RT " + lingkungan.getRt() + "/RW " + lingkungan.getRw());
                } else {
                    lblLingkungan.setText("-");
                    lblRTRW.setText("-");
                }
            } else {
                // fallback: gunakan id_lingkungan dari pengguna
                if (lingkungan != null) {
                    lblLingkungan.setText(lingkungan.getNamaDaerah());
                    lblRTRW.setText("RT " + lingkungan.getRt() + "/RW " + lingkungan.getRw());
                } else {
                    lblLingkungan.setText("-");
                    lblRTRW.setText("-");
                }
            }
        }
        
        setTitle("Dashboard Warga - " + (pengguna != null ? pengguna.getNamaPengguna() + " (" + pengguna.getRole() + ")" : "Login"));
        
        // Inisialisasi panel
        inisiasiPanel();
    }

    private void inisiasiPanel(){
        System.out.println("[DEBUG] inisiasiPanel() dipanggil, pengguna=" + (pengguna != null ? pengguna.getId() : "null"));
        penarikan = new Penarikan();
        setoran = new Setoran();
        tabungan = new TabunganWarga();
        notif = new notif();
        
        // SET DATA KE PANEL SETORAN, TABUNGAN, PENARIKAN dengan debug
        if (warga != null) {
            System.out.println("[DEBUG] Data warga: id=" + warga.getId() + ", nama=" + warga.getNamaWarga() + ", id_lingkungan=" + warga.getIdLingkungan());
            setoran.setIdWarga(warga.getId());
            setoran.setNamaWarga(warga.getNamaWarga());
            setoran.setIdLingkungan(warga.getIdLingkungan());
            tabungan.setIdWarga(warga.getId());
            penarikan.setIdWarga(warga.getId());
            notif.loadNotifikasi(warga.getId());
        } else if (pengguna != null) {
            // Fallback: jika tidak ada data warga, gunakan data pengguna
            System.out.println("[DEBUG] Data warga null, menggunakan data pengguna sebagai fallback");
        } else {
            System.out.println("[ERROR] Data pengguna dan warga null, tidak bisa set data ke panel!");
        }

        // menambahkan panel ke panel
        actionPanel.add(penarikan);
        actionPanel.add(setoran);
        actionPanel.add(tabungan);
        actionPanel.add(notif);

        penarikan.setVisible(false);
        setoran.setVisible(false);
        tabungan.setVisible(true);
        notif.setVisible(false);
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
    
    private void updateSidebarHighlight(JPanel activePanel) {
        // Reset all panel backgrounds
        pnlPenarikan.setBackground(new java.awt.Color(33,52,72));
        pnlSetoran.setBackground(new java.awt.Color(33,52,72));
        pnlTabungan.setBackground(new java.awt.Color(33,52,72));
        
        // Highlight active panel
        if (activePanel != null) {
            activePanel.setBackground(new java.awt.Color(11,60,105));
            activeSidebarPanel = activePanel;
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_sideBar = new javax.swing.JPanel();
        pnlProfileUser = new javax.swing.JPanel();
        profile = new javax.swing.JLabel();
        lblNamaUser = new javax.swing.JLabel();
        iconNotif = new javax.swing.JLabel();
        pnlTabungan = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pnlSetoran = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        pnlPenarikan = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblLingkungan = new javax.swing.JLabel();
        lblRTRW = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblWaktu = new javax.swing.JLabel();
        lblTanggal = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        pnl_content = new javax.swing.JPanel();
        actionPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));

        pnl_sideBar.setBackground(new java.awt.Color(33, 52, 72));
        pnl_sideBar.setPreferredSize(new java.awt.Dimension(250, 342));

        pnlProfileUser.setBackground(new java.awt.Color(11, 60, 105));

        profile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Profile_1.png"))); // NOI18N

        lblNamaUser.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNamaUser.setForeground(new java.awt.Color(255, 255, 255));
        lblNamaUser.setText("Nama");

        iconNotif.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Notification_1.png"))); // NOI18N
        iconNotif.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconNotifMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlProfileUserLayout = new javax.swing.GroupLayout(pnlProfileUser);
        pnlProfileUser.setLayout(pnlProfileUserLayout);
        pnlProfileUserLayout.setHorizontalGroup(
            pnlProfileUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProfileUserLayout.createSequentialGroup()
                .addGroup(pnlProfileUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlProfileUserLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(iconNotif))
                    .addGroup(pnlProfileUserLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(profile, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblNamaUser)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(12, 12, 12))
        );
        pnlProfileUserLayout.setVerticalGroup(
            pnlProfileUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProfileUserLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iconNotif)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlProfileUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProfileUserLayout.createSequentialGroup()
                        .addComponent(profile)
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProfileUserLayout.createSequentialGroup()
                        .addComponent(lblNamaUser)
                        .addGap(30, 30, 30))))
        );

        pnlTabungan.setBackground(new java.awt.Color(33, 52, 72));
        pnlTabungan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlTabunganMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlTabunganMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlTabunganMouseExited(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Lihat Tabungan");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Dollar Bag.png"))); // NOI18N

        javax.swing.GroupLayout pnlTabunganLayout = new javax.swing.GroupLayout(pnlTabungan);
        pnlTabungan.setLayout(pnlTabunganLayout);
        pnlTabunganLayout.setHorizontalGroup(
            pnlTabunganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTabunganLayout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(81, 81, 81))
        );
        pnlTabunganLayout.setVerticalGroup(
            pnlTabunganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTabunganLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTabunganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlSetoran.setBackground(new java.awt.Color(33, 52, 72));
        pnlSetoran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlSetoranMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlSetoranMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlSetoranMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlSetoranMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pnlSetoranMouseReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Setoran");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Transaction.png"))); // NOI18N

        javax.swing.GroupLayout pnlSetoranLayout = new javax.swing.GroupLayout(pnlSetoran);
        pnlSetoran.setLayout(pnlSetoranLayout);
        pnlSetoranLayout.setHorizontalGroup(
            pnlSetoranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSetoranLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlSetoranLayout.setVerticalGroup(
            pnlSetoranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSetoranLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSetoranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlPenarikan.setBackground(new java.awt.Color(33, 52, 72));
        pnlPenarikan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlPenarikanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlPenarikanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlPenarikanMouseExited(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Penarikan");

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Get Cash.png"))); // NOI18N

        javax.swing.GroupLayout pnlPenarikanLayout = new javax.swing.GroupLayout(pnlPenarikan);
        pnlPenarikan.setLayout(pnlPenarikanLayout);
        pnlPenarikanLayout.setHorizontalGroup(
            pnlPenarikanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPenarikanLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlPenarikanLayout.setVerticalGroup(
            pnlPenarikanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPenarikanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPenarikanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        lblLingkungan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblLingkungan.setText("Lingkungan");

        lblRTRW.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblRTRW.setText("RT/RW");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLingkungan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblRTRW, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 63, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblLingkungan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRTRW)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel12.setForeground(new java.awt.Color(255, 0, 0));
        jLabel12.setText("Made with 💓 by");

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Kelompok 1");

        jPanel2.setBackground(new java.awt.Color(33, 52, 72));

        lblWaktu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblWaktu.setForeground(new java.awt.Color(255, 255, 255));
        lblWaktu.setText("Waktu");

        lblTanggal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTanggal.setForeground(new java.awt.Color(255, 255, 255));
        lblTanggal.setText("Tanggal");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblWaktu, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(69, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jSeparator1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblWaktu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTanggal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnl_sideBarLayout = new javax.swing.GroupLayout(pnl_sideBar);
        pnl_sideBar.setLayout(pnl_sideBarLayout);
        pnl_sideBarLayout.setHorizontalGroup(
            pnl_sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_sideBarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(pnlPenarikan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlSetoran, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlProfileUser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlTabungan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnl_sideBarLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_sideBarLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_sideBarLayout.setVerticalGroup(
            pnl_sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_sideBarLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(pnlProfileUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlTabungan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlSetoran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlPenarikan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnl_sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_sideBarLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(pnl_sideBarLayout.createSequentialGroup()
                        .addGap(213, 213, 213)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)))
                .addGroup(pnl_sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(pnl_sideBar, java.awt.BorderLayout.LINE_START);

        pnl_content.setLayout(new java.awt.BorderLayout());

        actionPanel.setLayout(new java.awt.CardLayout());
        pnl_content.add(actionPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnl_content, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pnlTabunganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTabunganMouseClicked
        penarikan.setVisible(false);
        setoran.setVisible(false);
        tabungan.setVisible(true);
        notif.setVisible(false);
        updateSidebarHighlight(pnlTabungan);
    }//GEN-LAST:event_pnlTabunganMouseClicked

    private void pnlTabunganMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTabunganMouseEntered
        // Hanya ubah warna jika bukan panel yang aktif
        if (activeSidebarPanel != pnlTabungan) {
            pnlTabungan.setBackground(new java.awt.Color(11,60,105));
        }
    }//GEN-LAST:event_pnlTabunganMouseEntered

    private void pnlTabunganMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTabunganMouseExited
        // Hanya ubah warna jika bukan panel yang aktif
        if (activeSidebarPanel != pnlTabungan) {
            pnlTabungan.setBackground(new java.awt.Color(33,52,72));
        }
    }//GEN-LAST:event_pnlTabunganMouseExited

    private void pnlSetoranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSetoranMouseClicked
        penarikan.setVisible(false);
        setoran.setVisible(true);
        tabungan.setVisible(false);
        notif.setVisible(false);
        updateSidebarHighlight(pnlSetoran);
    }//GEN-LAST:event_pnlSetoranMouseClicked

    private void pnlSetoranMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSetoranMouseEntered
        // Hanya ubah warna jika bukan panel yang aktif
        if (activeSidebarPanel != pnlSetoran) {
            pnlSetoran.setBackground(new java.awt.Color(11,60,105));
        }
    }//GEN-LAST:event_pnlSetoranMouseEntered

    private void pnlSetoranMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSetoranMouseExited
        // Hanya ubah warna jika bukan panel yang aktif
        if (activeSidebarPanel != pnlSetoran) {
            pnlSetoran.setBackground(new java.awt.Color(33,52,72));
        }
    }//GEN-LAST:event_pnlSetoranMouseExited

    private void pnlSetoranMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSetoranMousePressed

    }//GEN-LAST:event_pnlSetoranMousePressed

    private void pnlSetoranMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSetoranMouseReleased

    }//GEN-LAST:event_pnlSetoranMouseReleased

    private void pnlPenarikanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlPenarikanMouseClicked
        penarikan.setVisible(true);
        setoran.setVisible(false);
        tabungan.setVisible(false);
        notif.setVisible(false);
        updateSidebarHighlight(pnlPenarikan);
    }//GEN-LAST:event_pnlPenarikanMouseClicked

    private void pnlPenarikanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlPenarikanMouseEntered
        // Hanya ubah warna jika bukan panel yang aktif
        if (activeSidebarPanel != pnlPenarikan) {
            pnlPenarikan.setBackground(new java.awt.Color(11,60,105));
        }
    }//GEN-LAST:event_pnlPenarikanMouseEntered

    private void pnlPenarikanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlPenarikanMouseExited
        // Hanya ubah warna jika bukan panel yang aktif
        if (activeSidebarPanel != pnlPenarikan) {
            pnlPenarikan.setBackground(new java.awt.Color(33,52,72));
        }
    }//GEN-LAST:event_pnlPenarikanMouseExited

    private void iconNotifMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconNotifMouseClicked
        penarikan.setVisible(false);
        setoran.setVisible(false);
        tabungan.setVisible(false);
        notif.setVisible(true);
        if (warga != null) {
            notif.loadNotifikasi(warga.getId());
        }
        // ... kode lain untuk menampilkan panel notif jika pakai CardLayout/setVisible
    }//GEN-LAST:event_iconNotifMouseClicked

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
            try {
                System.out.println("[INFO] Memulai aplikasi WargaPage...");
                WargaPage wargaPage = new WargaPage();
                wargaPage.setVisible(false);
                System.out.println("[INFO] Aplikasi WargaPage berhasil dimulai");
            } catch (Exception e) {
                logger.log(java.util.logging.Level.SEVERE, "Error starting WargaPage", e);
                JOptionPane.showMessageDialog(null, 
                    "Terjadi kesalahan saat memulai aplikasi: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionPanel;
    private javax.swing.JLabel iconNotif;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblLingkungan;
    private javax.swing.JLabel lblNamaUser;
    private javax.swing.JLabel lblRTRW;
    private javax.swing.JLabel lblTanggal;
    private javax.swing.JLabel lblWaktu;
    private javax.swing.JPanel pnlPenarikan;
    private javax.swing.JPanel pnlProfileUser;
    private javax.swing.JPanel pnlSetoran;
    private javax.swing.JPanel pnlTabungan;
    private javax.swing.JPanel pnl_content;
    private javax.swing.JPanel pnl_sideBar;
    private javax.swing.JLabel profile;
    // End of variables declaration//GEN-END:variables

    public model.Warga getWarga() {
        return warga;
    }
}
