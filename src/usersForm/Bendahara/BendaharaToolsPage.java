/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package usersForm.Bendahara;

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
public class BendaharaToolsPage extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(BendaharaToolsPage.class.getName());

    private panelDashboardBendahara dashboardBendahara;
    private panelPendapatanBendahara profit;
    private panelPengeluaranBendahara loss;
    private panelTabunganWarga nabung;
    
    // Variabel untuk melacak panel yang aktif
    private JPanel activeSidebarPanel;
    
    private Pengguna pengguna;
    private Warga warga;
    private Lingkungan lingkungan;
    
    public BendaharaToolsPage() {
        // Autentikasi terlebih dahulu sebelum inisialisasi UI
        if (!performAuthentication()) {
            // Jika autentikasi gagal, tutup aplikasi
            System.exit(0);
        }
        
        // Jika autentikasi berhasil, inisialisasi UI
        initComponents();
        setTitle("Bendahara Tools Page");
        setLocationRelativeTo(null);
        tampilkanTanggalDanWaktu();
        inisiasiPanel();
        
        try {
            setIconImage(new ImageIcon(getClass().getResource("/icons/Dollar Bag.png")).getImage());
        } catch (Exception e) {
            // Icon tidak ditemukan, gunakan default
        }
        
        // Set panel dashboard sebagai panel aktif default
        activeSidebarPanel = pnlDashboard;
        updateSidebarHighlight(pnlDashboard);
        
        // Shortcut rahasia Ctrl+Shift+A untuk membuka AdminToolsPage
        setFocusable(true);
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == java.awt.event.KeyEvent.VK_A) {
                    new AdminTools.AdminToolsPage().setVisible(true);
                }
            }
        });
        
        // Inisialisasi UI dengan data user yang sudah login
        initializeUserData();
    }
    
    public BendaharaToolsPage(Pengguna pengguna) {
        // Inisialisasi UI tanpa autentikasi karena data pengguna sudah disediakan
        initComponents();
        setTitle("Bendahara Tools Page");
        setLocationRelativeTo(null);
        tampilkanTanggalDanWaktu();
        inisiasiPanel();
        
        try {
            setIconImage(new ImageIcon(getClass().getResource("/icons/Dollar Bag.png")).getImage());
        } catch (Exception e) {
            // Icon tidak ditemukan, gunakan default
        }
        
        // Set panel dashboard sebagai panel aktif default
        activeSidebarPanel = pnlDashboard;
        updateSidebarHighlight(pnlDashboard);
        
        // Shortcut rahasia Ctrl+Shift+A untuk membuka AdminToolsPage
        setFocusable(true);
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == java.awt.event.KeyEvent.VK_A) {
                    new AdminTools.AdminToolsPage().setVisible(true);
                }
            }
        });
        
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
        
        // Create a final copy for lambda expressions
        final int finalIdLingkungan = idLingkunganToSet;
        
        // Set jumlah warga di dashboard jika id lingkungan valid
        if (dashboardBendahara != null && idLingkunganToSet > 0) {
            dashboardBendahara.setLingkungan(idLingkunganToSet);
            dashboardBendahara.setDataKeuangan(idLingkunganToSet);
        }
        
        // Configure panel pengeluaran
        if (loss != null && pengguna != null) {
            loss.setIdLingkungan(this.lingkungan != null ? this.lingkungan.getId() : this.pengguna.getIdLingkungan());
            loss.setIdPengguna(this.pengguna.getId());
            loss.setDashboardRefreshCallback(() -> {
                if (dashboardBendahara != null) {
                    int idLingkungan = (this.lingkungan != null) ? this.lingkungan.getId() : this.pengguna.getIdLingkungan();
                    dashboardBendahara.setDataKeuangan(idLingkungan);
                }
            });
        }
        
        
        // Set idLingkungan dan idPengguna untuk panel tabungan setelah field warga/pengguna sudah terisi
        if (nabung != null) {
            if (this.warga != null) {
                nabung.setIdLingkungan(this.warga.getIdLingkungan());
            } else if (this.pengguna != null) {
                nabung.setIdLingkungan(this.pengguna.getIdLingkungan());
            }
            if (this.pengguna != null) {
                nabung.setIdPengguna(this.pengguna.getId());
            }
        }
    }
    
    // Overload jika ingin menerima Warga dan Lingkungan juga
    public BendaharaToolsPage(Pengguna pengguna, Warga warga, Lingkungan lingkungan) {
        // Inisialisasi UI tanpa autentikasi karena data pengguna sudah disediakan
        initComponents();
        setTitle("Bendahara Tools Page");
        setLocationRelativeTo(null);
        tampilkanTanggalDanWaktu();
        inisiasiPanel();
        
        try {
            setIconImage(new ImageIcon(getClass().getResource("/icons/Dollar Bag.png")).getImage());
        } catch (Exception e) {
            // Icon tidak ditemukan, gunakan default
        }
        
        // Set panel dashboard sebagai panel aktif default
        activeSidebarPanel = pnlDashboard;
        updateSidebarHighlight(pnlDashboard);
        
        // Shortcut rahasia Ctrl+Shift+A untuk membuka AdminToolsPage
        setFocusable(true);
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == java.awt.event.KeyEvent.VK_A) {
                    new AdminTools.AdminToolsPage().setVisible(true);
                }
            }
        });
        
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
            if (dashboardBendahara != null) {
                dashboardBendahara.setLingkungan(this.lingkungan.getId());
            }
            if (profit != null) {
                profit.setIdLingkungan(this.lingkungan.getId());
                profit.setIdPengguna(this.pengguna.getId());
                profit.setDashboardRefreshCallback(() -> {
                    if (dashboardBendahara != null) {
                        dashboardBendahara.setDataKeuangan(this.lingkungan.getId());
                    }
                });
            }
            if (loss != null) {
                loss.setIdLingkungan(this.lingkungan.getId());
                loss.setIdPengguna(this.pengguna.getId());
                loss.setDashboardRefreshCallback(() -> {
                    if (dashboardBendahara != null) {
                        dashboardBendahara.setDataKeuangan(this.lingkungan.getId());
                    }
                });
            }
        } else if (dashboardBendahara != null && pengguna != null) {
            dashboardBendahara.setLingkungan(pengguna.getIdLingkungan());
            if (profit != null) {
                profit.setIdLingkungan(pengguna.getIdLingkungan());
                profit.setIdPengguna(pengguna.getId());
                profit.setDashboardRefreshCallback(() -> {
                    if (dashboardBendahara != null) {
                        dashboardBendahara.setDataKeuangan(pengguna.getIdLingkungan());
                    }
                });
            }
            if (loss != null) {
                loss.setIdLingkungan(pengguna.getIdLingkungan());
                loss.setIdPengguna(pengguna.getId());
                loss.setDashboardRefreshCallback(() -> {
                    if (dashboardBendahara != null) {
                        dashboardBendahara.setDataKeuangan(pengguna.getIdLingkungan());
                    }
                });
            }
        }
    }
    
    private void inisiasiPanel(){
        dashboardBendahara = new panelDashboardBendahara();
        profit = new panelPendapatanBendahara();
        loss = new panelPengeluaranBendahara();
        nabung = new panelTabunganWarga();

        // menambahkan panel ke panel
        actionPanel.add(dashboardBendahara, "dashboard");
        actionPanel.add(profit, "pendapatan");
        actionPanel.add(loss, "pengeluaran");
        actionPanel.add(nabung, "tabungan");

        dashboardBendahara.setVisible(true);
        profit.setVisible(false);
        loss.setVisible(false);
        nabung.setVisible(false);
    }
    
    private void showDashboard() {
        // Hide all panels
        profit.setVisible(false);
        loss.setVisible(false);
        nabung.setVisible(false);
        
        // Show dashboard panel
        dashboardBendahara.setVisible(true);
        
        // Update sidebar highlighting
        updateSidebarHighlight(pnlDashboard);
    }
    
    private void updateSidebarHighlight(JPanel activePanel) {
        // Reset all panel backgrounds
        pnlDashboard.setBackground(new java.awt.Color(33,52,72));
        pnlPendapatan.setBackground(new java.awt.Color(33,52,72));
        pnlPengeluaran.setBackground(new java.awt.Color(33,52,72));
        pnlTabunganWarga.setBackground(new java.awt.Color(33,52,72));
//        pnlDaftarTransaksi.setBackground(new java.awt.Color(33,52,72));
        
        // Highlight active panel
        if (activePanel != null) {
            activePanel.setBackground(new java.awt.Color(11,60,105));
            activeSidebarPanel = activePanel;
        }
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
        pnlProfileUser = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblNamaUser = new javax.swing.JLabel();
        lblRoleUser = new javax.swing.JLabel();
        pnlLogout = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        pnlDashboard = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pnlPendapatan = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        pnlPengeluaran = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        pnlTabunganWarga = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
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

        pnlPendapatan.setBackground(new java.awt.Color(33, 52, 72));
        pnlPendapatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlPendapatanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlPendapatanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlPendapatanMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlPendapatanMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                pnlPendapatanMouseReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Pendapatan");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Profit.png"))); // NOI18N

        javax.swing.GroupLayout pnlPendapatanLayout = new javax.swing.GroupLayout(pnlPendapatan);
        pnlPendapatan.setLayout(pnlPendapatanLayout);
        pnlPendapatanLayout.setHorizontalGroup(
            pnlPendapatanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPendapatanLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlPendapatanLayout.setVerticalGroup(
            pnlPendapatanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPendapatanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPendapatanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlPengeluaran.setBackground(new java.awt.Color(33, 52, 72));
        pnlPengeluaran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlPengeluaranMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlPengeluaranMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlPengeluaranMouseExited(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Pengeluaran");

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Financial Decline.png"))); // NOI18N

        javax.swing.GroupLayout pnlPengeluaranLayout = new javax.swing.GroupLayout(pnlPengeluaran);
        pnlPengeluaran.setLayout(pnlPengeluaranLayout);
        pnlPengeluaranLayout.setHorizontalGroup(
            pnlPengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPengeluaranLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlPengeluaranLayout.setVerticalGroup(
            pnlPengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPengeluaranLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlTabunganWarga.setBackground(new java.awt.Color(33, 52, 72));
        pnlTabunganWarga.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlTabunganWargaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlTabunganWargaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlTabunganWargaMouseExited(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Tabungan Warga");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Get Cash.png"))); // NOI18N

        javax.swing.GroupLayout pnlTabunganWargaLayout = new javax.swing.GroupLayout(pnlTabunganWarga);
        pnlTabunganWarga.setLayout(pnlTabunganWargaLayout);
        pnlTabunganWargaLayout.setHorizontalGroup(
            pnlTabunganWargaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTabunganWargaLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlTabunganWargaLayout.setVerticalGroup(
            pnlTabunganWargaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTabunganWargaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTabunganWargaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8))
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
                        .addComponent(pnlTabunganWarga, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlPengeluaran, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlPendapatan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(pnlPendapatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlTabunganWarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnl_sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_sideBarLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(pnl_sideBarLayout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)))
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
        showDashboard();
    }//GEN-LAST:event_pnlDashboardMouseClicked

    private void pnlPendapatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlPendapatanMouseClicked
        // Sembunyikan semua panel lain
        dashboardBendahara.setVisible(false);
        loss.setVisible(false);
        nabung.setVisible(false);

        // Tampilkan panel pendapatan
        profit.setVisible(true);

        // Muat ulang data tabel pendapatan
        profit.refreshTabelPendapatan();

        updateSidebarHighlight(pnlPendapatan);
    }//GEN-LAST:event_pnlPendapatanMouseClicked

    private void pnlPengeluaranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlPengeluaranMouseClicked
        dashboardBendahara.setVisible(false);
        profit.setVisible(false);
        loss.setVisible(true);
        nabung.setVisible(false);

        // Memuat ulang data di tabel pengeluaran saat panel ditampilkan
        //loss.refreshTabelPengeluaran();

        updateSidebarHighlight(pnlPengeluaran);
    }//GEN-LAST:event_pnlPengeluaranMouseClicked

    private void pnlTabunganWargaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTabunganWargaMouseClicked
        dashboardBendahara.setVisible(false);
        profit.setVisible(false);
        loss.setVisible(false);
        nabung.setVisible(true);
        updateSidebarHighlight(pnlTabunganWarga);
    }//GEN-LAST:event_pnlTabunganWargaMouseClicked

    private void pnlDashboardMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDashboardMouseExited
        if (activeSidebarPanel != pnlDashboard) {
            pnlDashboard.setBackground(new java.awt.Color(33,52,72));
        }
    }//GEN-LAST:event_pnlDashboardMouseExited

    private void pnlDashboardMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDashboardMouseEntered
        if (activeSidebarPanel != pnlDashboard) {
            pnlDashboard.setBackground(new java.awt.Color(11,60,105));
        }
    }//GEN-LAST:event_pnlDashboardMouseEntered

    private void pnlPendapatanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlPendapatanMouseEntered
        if (activeSidebarPanel != pnlPendapatan) {
            pnlPendapatan.setBackground(new java.awt.Color(11,60,105));
        }
    }//GEN-LAST:event_pnlPendapatanMouseEntered

    private void pnlPendapatanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlPendapatanMouseExited
        if (activeSidebarPanel != pnlPendapatan) {
            pnlPendapatan.setBackground(new java.awt.Color(33,52,72));
        }
    }//GEN-LAST:event_pnlPendapatanMouseExited

    private void pnlPendapatanMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlPendapatanMousePressed
        
    }//GEN-LAST:event_pnlPendapatanMousePressed

    private void pnlPendapatanMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlPendapatanMouseReleased
        
    }//GEN-LAST:event_pnlPendapatanMouseReleased

    private void pnlPengeluaranMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlPengeluaranMouseEntered
        if (activeSidebarPanel != pnlPengeluaran) {
            pnlPengeluaran.setBackground(new java.awt.Color(11,60,105));
        }
    }//GEN-LAST:event_pnlPengeluaranMouseEntered

    private void pnlPengeluaranMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlPengeluaranMouseExited
        if (activeSidebarPanel != pnlPengeluaran) {
            pnlPengeluaran.setBackground(new java.awt.Color(33,52,72));
        }
    }//GEN-LAST:event_pnlPengeluaranMouseExited

    private void pnlTabunganWargaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTabunganWargaMouseEntered
        if (activeSidebarPanel != pnlTabunganWarga) {
            pnlTabunganWarga.setBackground(new java.awt.Color(11,60,105));
        }
    }//GEN-LAST:event_pnlTabunganWargaMouseEntered

    private void pnlTabunganWargaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTabunganWargaMouseExited
        if (activeSidebarPanel != pnlTabunganWarga) {
            pnlTabunganWarga.setBackground(new java.awt.Color(33,52,72));
        }
    }//GEN-LAST:event_pnlTabunganWargaMouseExited

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
        
        setTitle("Dashboard Bendahara - " + (pengguna != null ? pengguna.getNamaPengguna() + " (" + pengguna.getRole() + ")" : "Login"));
        
        // Set data ke panel setelah panel diinisialisasi
        int idLingkunganToSet = -1;
        if (warga != null) {
            idLingkunganToSet = warga.getIdLingkungan();
        } else if (pengguna != null) {
            idLingkunganToSet = pengguna.getIdLingkungan();
        }
        
        // Set jumlah warga di dashboard jika id lingkungan valid
        if (dashboardBendahara != null && idLingkunganToSet > 0) {
            dashboardBendahara.setLingkungan(idLingkunganToSet);
            dashboardBendahara.setDataKeuangan(idLingkunganToSet);
        }
        
        // Configure panel pengeluaran
        if (loss != null && pengguna != null) {
            loss.setIdLingkungan(this.lingkungan != null ? this.lingkungan.getId() : this.pengguna.getIdLingkungan());
            loss.setIdPengguna(this.pengguna.getId());
            loss.setDashboardRefreshCallback(() -> {
                if (dashboardBendahara != null) {
                    int idLingkungan = (this.lingkungan != null) ? this.lingkungan.getId() : this.pengguna.getIdLingkungan();
                    dashboardBendahara.setDataKeuangan(idLingkungan);
                }
            });
        }
        
        // Set idLingkungan dan idPengguna untuk panel tabungan
        if (nabung != null) {
            if (this.warga != null) {
                nabung.setIdLingkungan(this.warga.getIdLingkungan());
            } else if (this.pengguna != null) {
                nabung.setIdLingkungan(this.pengguna.getIdLingkungan());
            }
            if (this.pengguna != null) {
                nabung.setIdPengguna(this.pengguna.getId());
            }
        }
    }

    // Getter untuk id lingkungan aktif (untuk panelTabunganWarga)
    public int getIdLingkunganAktif() {
        if (this.lingkungan != null) {
            return this.lingkungan.getId();
        } else if (this.pengguna != null) {
            return this.pengguna.getIdLingkungan();
        }
        return -1;
    }

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
                System.out.println("[INFO] Memulai aplikasi BendaharaToolsPage...");
                BendaharaToolsPage bendaharaPage = new BendaharaToolsPage();
                bendaharaPage.setVisible(false);
                System.out.println("[INFO] Aplikasi BendaharaToolsPage berhasil dimulai");
            } catch (Exception e) {
                logger.log(java.util.logging.Level.SEVERE, "Error starting BendaharaToolsPage", e);
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
    private javax.swing.JPanel pnlDashboard;
    private javax.swing.JPanel pnlLogout;
    private javax.swing.JPanel pnlPendapatan;
    private javax.swing.JPanel pnlPengeluaran;
    private javax.swing.JPanel pnlProfileUser;
    private javax.swing.JPanel pnlTabunganWarga;
    private javax.swing.JPanel pnl_content;
    private javax.swing.JPanel pnl_sideBar;
    // End of variables declaration//GEN-END:variables
}
