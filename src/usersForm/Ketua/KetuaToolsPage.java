/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package usersForm.Ketua;

import model.Pengguna;
import model.Warga;
import model.Lingkungan;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.Pengguna;
import DataAccessObject.WargaDAO;
import model.Warga;
import DataAccessObject.LingkunganDAO;
import model.Lingkungan;
import javax.swing.JOptionPane;

/**
 *
 * @author HYPE AMD
 */
public class KetuaToolsPage extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(KetuaToolsPage.class.getName());
    
    private panelDashboardKetua DashboardKetua;
    private panelBuatLaporan BuatLaporan;
    private panelDaftarPengajuan DaftarPengajuan;
    private panelTinjauTabungan TinjauTabungan;
    private panelKomunikasi Komunikasi;
    
    private Pengguna pengguna;
    private Warga warga;
    private Lingkungan lingkungan;
    private JPanel activeSidebarPanel;

    /**
     * Creates new form KetuaToolsPage
     */
    public KetuaToolsPage() {
        // Autentikasi terlebih dahulu sebelum inisialisasi UI
        if (!performAuthentication()) {
            // Jika autentikasi gagal, tutup aplikasi
            System.exit(0);
        }
        
        // Jika autentikasi berhasil, inisialisasi UI
        initComponents();
        setLocationRelativeTo(null);
        // Set panel dashboard sebagai panel aktif default
        activeSidebarPanel = pnlDashboard;
        updateSidebarHighlight(pnlDashboard);
        tampilkanTanggalDanWaktu();
        inisiasiPanel();
        
        // Inisialisasi UI dengan data user yang sudah login
        initializeUserData();
    }

    // Konstruktor untuk login multi user
    public KetuaToolsPage(Pengguna pengguna) {
        // Inisialisasi UI tanpa autentikasi karena data pengguna sudah disediakan
        initComponents();
        setLocationRelativeTo(null);
        // Set panel dashboard sebagai panel aktif default
        activeSidebarPanel = pnlDashboard;
        updateSidebarHighlight(pnlDashboard);
        tampilkanTanggalDanWaktu();
        inisiasiPanel();
        
        // Set data pengguna yang sudah disediakan
        this.pengguna = pengguna;
        int idLingkunganToSet = -1;
        if (pengguna != null) {
            lblNamaUser.setText(pengguna.getNamaPengguna());
            lblRoleUser.setText(pengguna.getRole());
            System.out.println("[DEBUG] Pengguna ID: " + pengguna.getId());
            WargaDAO wargaDAO = new WargaDAO();
            this.warga = wargaDAO.getWargaByPenggunaId(pengguna.getId());
            System.out.println("[DEBUG] Warga: " + (this.warga != null ? this.warga.getNamaWarga() + ", id_lingkungan=" + this.warga.getIdLingkungan() : "null"));
            if (this.warga != null) {
                int idLingkungan = this.warga.getIdLingkungan();
                idLingkunganToSet = idLingkungan;
                LingkunganDAO lingkunganDAO = new LingkunganDAO();
                this.lingkungan = lingkunganDAO.getLingkunganById(idLingkungan);
                System.out.println("[DEBUG] Lingkungan: " + (this.lingkungan != null ? this.lingkungan.getNamaDaerah() + ", RT=" + this.lingkungan.getRt() + ", RW=" + this.lingkungan.getRw() : "null"));
                if (this.lingkungan != null) {
                    lblLingkungan.setText(this.lingkungan.getNamaDaerah());
                    lblRTRW.setText("RT " + this.lingkungan.getRt() + "/RW " + this.lingkungan.getRw());
                } else {
                    lblLingkungan.setText("-");
                    lblRTRW.setText("-");
                }
            } else {
                // fallback: gunakan id_lingkungan dari pengguna
                idLingkunganToSet = pengguna.getIdLingkungan();
                lblLingkungan.setText("-");
                lblRTRW.setText("-");
            }
        }
        setTitle("Dashboard Ketua - " + pengguna.getNamaPengguna() + " (" + pengguna.getRole() + ")");
        // Panggil setter dashboard dan daftar pengajuan setelah panel diinisialisasi dan field sudah terisi
        if (DashboardKetua != null && idLingkunganToSet > 0) {
            DashboardKetua.setLingkunganDanPengguna(idLingkunganToSet, pengguna.getId());
        }
        if (DaftarPengajuan != null && idLingkunganToSet > 0) {
            DaftarPengajuan.setLingkunganDanPengguna(idLingkunganToSet, pengguna.getId());
        }
        if (TinjauTabungan != null && idLingkunganToSet > 0) {
            TinjauTabungan.setIdLingkungan(idLingkunganToSet);
        }
        if (BuatLaporan != null && idLingkunganToSet > 0) {
            BuatLaporan.setIdLingkungan(idLingkunganToSet);
        }
    }

    // Overload jika ingin menerima Warga dan Lingkungan juga
    public KetuaToolsPage(Pengguna pengguna, Warga warga, Lingkungan lingkungan) {
        // Inisialisasi UI tanpa autentikasi karena data pengguna sudah disediakan
        initComponents();
        setLocationRelativeTo(null);
        // Set panel dashboard sebagai panel aktif default
        activeSidebarPanel = pnlDashboard;
        updateSidebarHighlight(pnlDashboard);
        tampilkanTanggalDanWaktu();
        inisiasiPanel();
        
        // Set data yang sudah disediakan
        this.pengguna = pengguna;
        this.warga = warga;
        this.lingkungan = lingkungan;
        
        if (pengguna != null) {
            lblNamaUser.setText(pengguna.getNamaPengguna());
            lblRoleUser.setText(pengguna.getRole());
        }
        
        if (this.lingkungan != null) {
            lblLingkungan.setText(this.lingkungan.getNamaDaerah());
            lblRTRW.setText("RT " + this.lingkungan.getRt() + "/RW " + this.lingkungan.getRw());
        }
        
        setTitle("Dashboard Ketua - " + (pengguna != null ? pengguna.getNamaPengguna() + " (" + pengguna.getRole() + ")" : "Login"));
        
        // Set data ke panel setelah panel diinisialisasi
        int idLingkunganToSet = -1;
        if (warga != null) {
            idLingkunganToSet = warga.getIdLingkungan();
        } else if (pengguna != null) {
            idLingkunganToSet = pengguna.getIdLingkungan();
        }
        
        if (DashboardKetua != null && idLingkunganToSet > 0) {
            DashboardKetua.setLingkunganDanPengguna(idLingkunganToSet, pengguna.getId());
        }
        if (DaftarPengajuan != null && idLingkunganToSet > 0) {
            DaftarPengajuan.setLingkunganDanPengguna(idLingkunganToSet, pengguna.getId());
        }
        if (TinjauTabungan != null && idLingkunganToSet > 0) {
            TinjauTabungan.setIdLingkungan(idLingkunganToSet);
        }
        if (BuatLaporan != null && idLingkunganToSet > 0) {
            BuatLaporan.setIdLingkungan(idLingkunganToSet);
        }
    }

    private void inisiasiPanel(){
        System.out.println("[DEBUG] inisiasiPanel() dipanggil, pengguna=" + (pengguna != null ? pengguna.getId() : "null"));
        DashboardKetua = new panelDashboardKetua();
        BuatLaporan = new panelBuatLaporan();
        DaftarPengajuan = new panelDaftarPengajuan();
        TinjauTabungan = new panelTinjauTabungan();
        Komunikasi = new panelKomunikasi();

        // menambahkan panel ke panel
        actionPanel.add(DashboardKetua);
        actionPanel.add(BuatLaporan);
        actionPanel.add(DaftarPengajuan);
        actionPanel.add(TinjauTabungan);
        actionPanel.add(Komunikasi);

        DashboardKetua.setVisible(true);
        BuatLaporan.setVisible(false);
        DaftarPengajuan.setVisible(false);
        TinjauTabungan.setVisible(false);
        Komunikasi.setVisible(false);
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
        pnlDashboard.setBackground(new java.awt.Color(33,52,72));
        pnlDaftarPengajuan.setBackground(new java.awt.Color(33,52,72));
        pnlTinjauTabungan.setBackground(new java.awt.Color(33,52,72));
        pnlBuatLaporan.setBackground(new java.awt.Color(33,52,72));
        pnlKomunikasi.setBackground(new java.awt.Color(33,52,72));
        
        // Highlight active panel
        if (activePanel != null) {
            activePanel.setBackground(new java.awt.Color(11,60,105));
            activeSidebarPanel = activePanel;
        }
    }
    
    /**
     * Melakukan autentikasi user sebelum menampilkan UI
     * @return true jika autentikasi berhasil, false jika gagal
     */
    private boolean performAuthentication() {
        // Buat dialog login yang modal
        usersForm.LoginMultiUsers dialog = new usersForm.LoginMultiUsers(this);
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
            lblRoleUser.setText(pengguna.getRole());
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
        
        setTitle("Dashboard Ketua - " + (pengguna != null ? pengguna.getNamaPengguna() + " (" + pengguna.getRole() + ")" : "Login"));
        
        // Set data ke panel setelah panel diinisialisasi
        int idLingkunganToSet = -1;
        if (warga != null) {
            idLingkunganToSet = warga.getIdLingkungan();
        } else if (pengguna != null) {
            idLingkunganToSet = pengguna.getIdLingkungan();
        }
        
        if (DashboardKetua != null && idLingkunganToSet > 0) {
            DashboardKetua.setLingkunganDanPengguna(idLingkunganToSet, pengguna.getId());
        }
        if (DaftarPengajuan != null && idLingkunganToSet > 0) {
            DaftarPengajuan.setLingkunganDanPengguna(idLingkunganToSet, pengguna.getId());
        }
        if (TinjauTabungan != null && idLingkunganToSet > 0) {
            TinjauTabungan.setIdLingkungan(idLingkunganToSet);
        }
        if (BuatLaporan != null && idLingkunganToSet > 0) {
            BuatLaporan.setIdLingkungan(idLingkunganToSet);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_sideBar = new javax.swing.JPanel();
        pnlProfileUser = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblNamaUser = new javax.swing.JLabel();
        lblRoleUser = new javax.swing.JLabel();
        pnlLogout = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        pnlDashboard = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pnlDaftarPengajuan = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        pnlTinjauTabungan = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        pnlBuatLaporan = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        pnlKomunikasi = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
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

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Profile_1.png"))); // NOI18N

        lblNamaUser.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNamaUser.setForeground(new java.awt.Color(255, 255, 255));
        lblNamaUser.setText("Nama");

        lblRoleUser.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblRoleUser.setForeground(new java.awt.Color(153, 153, 153));
        lblRoleUser.setText("Role");

        pnlLogout.setBackground(new java.awt.Color(204, 0, 0));
        pnlLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlLogoutMouseClicked(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel14.setText("LOGOUT");

        javax.swing.GroupLayout pnlLogoutLayout = new javax.swing.GroupLayout(pnlLogout);
        pnlLogout.setLayout(pnlLogoutLayout);
        pnlLogoutLayout.setHorizontalGroup(
            pnlLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLogoutLayout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addGap(26, 26, 26))
        );
        pnlLogoutLayout.setVerticalGroup(
            pnlLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLogoutLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlProfileUserLayout = new javax.swing.GroupLayout(pnlProfileUser);
        pnlProfileUser.setLayout(pnlProfileUserLayout);
        pnlProfileUserLayout.setHorizontalGroup(
            pnlProfileUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProfileUserLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlProfileUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNamaUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlProfileUserLayout.createSequentialGroup()
                        .addGroup(pnlProfileUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlLogout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblRoleUser, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        pnlProfileUserLayout.setVerticalGroup(
            pnlProfileUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProfileUserLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlProfileUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addGroup(pnlProfileUserLayout.createSequentialGroup()
                        .addComponent(lblNamaUser)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblRoleUser)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlLogout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pnlDashboard.setBackground(new java.awt.Color(33, 52, 72));
        pnlDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlDashboardMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlDashboardMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlDashboardMouseExited(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Dashboard");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Menu.png"))); // NOI18N

        javax.swing.GroupLayout pnlDashboardLayout = new javax.swing.GroupLayout(pnlDashboard);
        pnlDashboard.setLayout(pnlDashboardLayout);
        pnlDashboardLayout.setHorizontalGroup(
            pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDashboardLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(105, 105, 105))
        );
        pnlDashboardLayout.setVerticalGroup(
            pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlDaftarPengajuan.setBackground(new java.awt.Color(33, 52, 72));
        pnlDaftarPengajuan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlDaftarPengajuanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlDaftarPengajuanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlDaftarPengajuanMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlDaftarPengajuanMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pnlDaftarPengajuanMouseReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Daftar Pengajuan");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Transaction.png"))); // NOI18N

        javax.swing.GroupLayout pnlDaftarPengajuanLayout = new javax.swing.GroupLayout(pnlDaftarPengajuan);
        pnlDaftarPengajuan.setLayout(pnlDaftarPengajuanLayout);
        pnlDaftarPengajuanLayout.setHorizontalGroup(
            pnlDaftarPengajuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDaftarPengajuanLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlDaftarPengajuanLayout.setVerticalGroup(
            pnlDaftarPengajuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDaftarPengajuanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDaftarPengajuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlTinjauTabungan.setBackground(new java.awt.Color(33, 52, 72));
        pnlTinjauTabungan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlTinjauTabunganMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlTinjauTabunganMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlTinjauTabunganMouseExited(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Tinjau Tabungan");

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Get Cash.png"))); // NOI18N

        javax.swing.GroupLayout pnlTinjauTabunganLayout = new javax.swing.GroupLayout(pnlTinjauTabungan);
        pnlTinjauTabungan.setLayout(pnlTinjauTabunganLayout);
        pnlTinjauTabunganLayout.setHorizontalGroup(
            pnlTinjauTabunganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTinjauTabunganLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlTinjauTabunganLayout.setVerticalGroup(
            pnlTinjauTabunganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTinjauTabunganLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTinjauTabunganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlBuatLaporan.setBackground(new java.awt.Color(33, 52, 72));
        pnlBuatLaporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlBuatLaporanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlBuatLaporanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlBuatLaporanMouseExited(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Buat Laporan");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Edit Graph Report.png"))); // NOI18N

        javax.swing.GroupLayout pnlBuatLaporanLayout = new javax.swing.GroupLayout(pnlBuatLaporan);
        pnlBuatLaporan.setLayout(pnlBuatLaporanLayout);
        pnlBuatLaporanLayout.setHorizontalGroup(
            pnlBuatLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBuatLaporanLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlBuatLaporanLayout.setVerticalGroup(
            pnlBuatLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuatLaporanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBuatLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlKomunikasi.setBackground(new java.awt.Color(33, 52, 72));
        pnlKomunikasi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlKomunikasiMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlKomunikasiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlKomunikasiMouseExited(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Komunikasi");

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/People Working Together.png"))); // NOI18N

        javax.swing.GroupLayout pnlKomunikasiLayout = new javax.swing.GroupLayout(pnlKomunikasi);
        pnlKomunikasi.setLayout(pnlKomunikasiLayout);
        pnlKomunikasiLayout.setHorizontalGroup(
            pnlKomunikasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlKomunikasiLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlKomunikasiLayout.setVerticalGroup(
            pnlKomunikasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKomunikasiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlKomunikasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10))
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
                        .addComponent(pnlKomunikasi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlBuatLaporan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlTinjauTabungan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlDaftarPengajuan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlProfileUser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlDashboard, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGap(18, 18, 18)
                .addComponent(pnlDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlDaftarPengajuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlTinjauTabungan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlBuatLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlKomunikasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnl_sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_sideBarLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(pnl_sideBarLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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

    private void pnlLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLogoutMouseClicked
        // Tampilkan konfirmasi logout
        int confirm = JOptionPane.showConfirmDialog(this,
            "Apakah Anda yakin ingin keluar?",
            "Konfirmasi Logout",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Buka halaman login
            usersForm.LoginMultiUsers loginPage = new usersForm.LoginMultiUsers();
            loginPage.setVisible(true);

            // Tutup halaman bendahara
            this.dispose();
        }
    }//GEN-LAST:event_pnlLogoutMouseClicked

    private void pnlDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDashboardMouseClicked
        DashboardKetua.setVisible(true);
        BuatLaporan.setVisible(false);
        DaftarPengajuan.setVisible(false);
        TinjauTabungan.setVisible(false);
        Komunikasi.setVisible(false);
        updateSidebarHighlight(pnlDashboard);
    }//GEN-LAST:event_pnlDashboardMouseClicked

    private void pnlDashboardMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDashboardMouseEntered
        // Hanya ubah warna jika bukan panel yang aktif
        if (activeSidebarPanel != pnlDashboard) {
            pnlDashboard.setBackground(new java.awt.Color(11,60,105));
        }
    }//GEN-LAST:event_pnlDashboardMouseEntered

    private void pnlDashboardMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDashboardMouseExited
        // Hanya ubah warna jika bukan panel yang aktif
        if (activeSidebarPanel != pnlDashboard) {
            pnlDashboard.setBackground(new java.awt.Color(33,52,72));
        }
    }//GEN-LAST:event_pnlDashboardMouseExited

    private void pnlDaftarPengajuanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDaftarPengajuanMouseClicked
        DashboardKetua.setVisible(false);
        BuatLaporan.setVisible(false);
        DaftarPengajuan.setVisible(true);
        TinjauTabungan.setVisible(false);
        Komunikasi.setVisible(false);
        updateSidebarHighlight(pnlDaftarPengajuan);
    }//GEN-LAST:event_pnlDaftarPengajuanMouseClicked

    private void pnlDaftarPengajuanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDaftarPengajuanMouseEntered
        // Hanya ubah warna jika bukan panel yang aktif
        if (activeSidebarPanel != pnlDaftarPengajuan) {
            pnlDaftarPengajuan.setBackground(new java.awt.Color(11,60,105));
        }
    }//GEN-LAST:event_pnlDaftarPengajuanMouseEntered

    private void pnlDaftarPengajuanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDaftarPengajuanMouseExited
        // Hanya ubah warna jika bukan panel yang aktif
        if (activeSidebarPanel != pnlDaftarPengajuan) {
            pnlDaftarPengajuan.setBackground(new java.awt.Color(33,52,72));
        }
    }//GEN-LAST:event_pnlDaftarPengajuanMouseExited

    private void pnlDaftarPengajuanMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDaftarPengajuanMousePressed

    }//GEN-LAST:event_pnlDaftarPengajuanMousePressed

    private void pnlDaftarPengajuanMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDaftarPengajuanMouseReleased

    }//GEN-LAST:event_pnlDaftarPengajuanMouseReleased

    private void pnlTinjauTabunganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTinjauTabunganMouseClicked
        DashboardKetua.setVisible(false);
        BuatLaporan.setVisible(false);
        DaftarPengajuan.setVisible(false);
        TinjauTabungan.setVisible(true);
        Komunikasi.setVisible(false);
        updateSidebarHighlight(pnlTinjauTabungan);
    }//GEN-LAST:event_pnlTinjauTabunganMouseClicked

    private void pnlTinjauTabunganMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTinjauTabunganMouseEntered
        // Hanya ubah warna jika bukan panel yang aktif
        if (activeSidebarPanel != pnlTinjauTabungan) {
            pnlTinjauTabungan.setBackground(new java.awt.Color(11,60,105));
        }
    }//GEN-LAST:event_pnlTinjauTabunganMouseEntered

    private void pnlTinjauTabunganMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTinjauTabunganMouseExited
        // Hanya ubah warna jika bukan panel yang aktif
        if (activeSidebarPanel != pnlTinjauTabungan) {
            pnlTinjauTabungan.setBackground(new java.awt.Color(33,52,72));
        }
    }//GEN-LAST:event_pnlTinjauTabunganMouseExited

    private void pnlBuatLaporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBuatLaporanMouseClicked
        DashboardKetua.setVisible(false);
        BuatLaporan.setVisible(true);
        DaftarPengajuan.setVisible(false);
        TinjauTabungan.setVisible(false);
        Komunikasi.setVisible(false);
        updateSidebarHighlight(pnlBuatLaporan);
    }//GEN-LAST:event_pnlBuatLaporanMouseClicked

    private void pnlBuatLaporanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBuatLaporanMouseEntered
        // Hanya ubah warna jika bukan panel yang aktif
        if (activeSidebarPanel != pnlBuatLaporan) {
            pnlBuatLaporan.setBackground(new java.awt.Color(11,60,105));
        }
    }//GEN-LAST:event_pnlBuatLaporanMouseEntered

    private void pnlBuatLaporanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlBuatLaporanMouseExited
        // Hanya ubah warna jika bukan panel yang aktif
        if (activeSidebarPanel != pnlBuatLaporan) {
            pnlBuatLaporan.setBackground(new java.awt.Color(33,52,72));
        }
    }//GEN-LAST:event_pnlBuatLaporanMouseExited

    private void pnlKomunikasiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlKomunikasiMouseClicked
        DashboardKetua.setVisible(false);
        BuatLaporan.setVisible(false);
        DaftarPengajuan.setVisible(false);
        TinjauTabungan.setVisible(false);
        Komunikasi.setVisible(true);
        updateSidebarHighlight(pnlKomunikasi);
    }//GEN-LAST:event_pnlKomunikasiMouseClicked

    private void pnlKomunikasiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlKomunikasiMouseEntered
        // Hanya ubah warna jika bukan panel yang aktif
        if (activeSidebarPanel != pnlKomunikasi) {
            pnlKomunikasi.setBackground(new java.awt.Color(11,60,105));
        }
    }//GEN-LAST:event_pnlKomunikasiMouseEntered

    private void pnlKomunikasiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlKomunikasiMouseExited
        // Hanya ubah warna jika bukan panel yang aktif
        if (activeSidebarPanel != pnlKomunikasi) {
            pnlKomunikasi.setBackground(new java.awt.Color(33,52,72));
        }
    }//GEN-LAST:event_pnlKomunikasiMouseExited

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
                System.out.println("[INFO] Memulai aplikasi KetuaToolsPage...");
                KetuaToolsPage ketuaPage = new KetuaToolsPage();
                ketuaPage.setVisible(false);
                System.out.println("[INFO] Aplikasi KetuaToolsPage berhasil dimulai");
            } catch (Exception e) {
                logger.log(java.util.logging.Level.SEVERE, "Error starting KetuaToolsPage", e);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblLingkungan;
    private javax.swing.JLabel lblNamaUser;
    private javax.swing.JLabel lblRTRW;
    private javax.swing.JLabel lblRoleUser;
    private javax.swing.JLabel lblTanggal;
    private javax.swing.JLabel lblWaktu;
    private javax.swing.JPanel pnlBuatLaporan;
    private javax.swing.JPanel pnlDaftarPengajuan;
    private javax.swing.JPanel pnlDashboard;
    private javax.swing.JPanel pnlKomunikasi;
    private javax.swing.JPanel pnlLogout;
    private javax.swing.JPanel pnlProfileUser;
    private javax.swing.JPanel pnlTinjauTabungan;
    private javax.swing.JPanel pnl_content;
    private javax.swing.JPanel pnl_sideBar;
    // End of variables declaration//GEN-END:variables

    public model.Lingkungan getLingkungan() {
        return lingkungan;
    }
    public model.Pengguna getPengguna() {
        return pengguna;
    }
}
