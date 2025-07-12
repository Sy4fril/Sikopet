package AdminTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.Koneksi;

/**
 * Custom dialog untuk input admin_name dan master key admin
 */
public class MasterKeyDialog extends JDialog {
    
    private JTextField adminNameField;
    private JPasswordField passwordField;
    private JLabel statusLabel;
    private JButton loginButton;
    private JButton cancelButton;
    private boolean isAuthenticated = false;
    private String adminName = "";
    
    public MasterKeyDialog(Frame parent) {
        super(parent, "Autentikasi Admin", true);
        initComponents();
        setupLayout();
        setupListeners();
        
        // Pastikan dialog selalu di atas
        setAlwaysOnTop(true);
        
        // Set minimum size untuk mencegah dialog terlalu kecil
        setMinimumSize(new Dimension(400, 500));
        
        // Posisikan dialog di tengah layar
        centerDialog();
    }
    
    private void initComponents() {
        setSize(450, 350);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Set icon untuk dialog
        try {
            setIconImage(new ImageIcon(getClass().getResource("/icons/Administrative Tools.png")).getImage());
        } catch (Exception e) {
            // Icon tidak ditemukan, gunakan default
        }
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Panel utama
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        mainPanel.setBackground(new Color(245, 245, 245));
        
        // Header
        JLabel headerLabel = new JLabel("Login Admin");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerLabel.setForeground(new Color(33, 52, 72));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Icon
        JLabel iconLabel = new JLabel();
        try {
            iconLabel.setIcon(new ImageIcon(getClass().getResource("/icons/User_1.png")));
        } catch (Exception e) {
            iconLabel.setText("🔐");
            iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        }
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Admin Name field
        JLabel adminNameLabel = new JLabel("Nama Admin:");
        adminNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        adminNameLabel.setForeground(new Color(33, 52, 72));
        adminNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        adminNameField = new JTextField(20);
        adminNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        adminNameField.setMaximumSize(new Dimension(350, 40));
        adminNameField.setPreferredSize(new Dimension(350, 40));
        adminNameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Master Key field
        JLabel masterKeyLabel = new JLabel("Master Key:");
        masterKeyLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        masterKeyLabel.setForeground(new Color(33, 52, 72));
        masterKeyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setMaximumSize(new Dimension(350, 40));
        passwordField.setPreferredSize(new Dimension(350, 40));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Status label
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(Color.RED);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0, 204, 51));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setPreferredSize(new Dimension(120, 40));
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createRaisedBevelBorder());
        
        cancelButton = new JButton("Batal");
        cancelButton.setBackground(new Color(255, 0, 0));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelButton.setPreferredSize(new Dimension(120, 40));
        cancelButton.setFocusPainted(false);
        cancelButton.setBorder(BorderFactory.createRaisedBevelBorder());
        
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        
        // Tambahkan komponen ke panel utama dengan spacing yang lebih baik
        mainPanel.add(headerLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(iconLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(adminNameLabel);
        mainPanel.add(Box.createVerticalStrut(8));
        mainPanel.add(adminNameField);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(masterKeyLabel);
        mainPanel.add(Box.createVerticalStrut(8));
        mainPanel.add(passwordField);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(statusLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(buttonPanel);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void setupListeners() {
        // Enter key pada password field
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    authenticate();
                }
            }
        });
        
        // Enter key pada admin name field
        adminNameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    passwordField.requestFocus();
                }
            }
        });
        
        // Login button
        loginButton.addActionListener(e -> authenticate());
        
        // Cancel button
        cancelButton.addActionListener(e -> {
            isAuthenticated = false;
            dispose();
        });
        
        // Window listener untuk ESC key
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                adminNameField.requestFocusInWindow();
            }
        });
        
        // Key listener untuk dialog
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    isAuthenticated = false;
                    dispose();
                }
            }
        });
    }
    
    private void authenticate() {
        String adminNameInput = adminNameField.getText();
        String masterKey = new String(passwordField.getPassword());
        
        // Validasi input
        if (adminNameInput.trim().isEmpty()) {
            statusLabel.setText("Nama Admin tidak boleh kosong!");
            statusLabel.setForeground(Color.RED);
            adminNameField.requestFocus();
            return;
        }
        
        if (masterKey.trim().isEmpty()) {
            statusLabel.setText("Master Key tidak boleh kosong!");
            statusLabel.setForeground(Color.RED);
            passwordField.setText("");
            passwordField.requestFocus();
            return;
        }
        
        // Validasi dengan AdminAuth
        if (AdminAuth.validateAdminCredentials(adminNameInput, masterKey)) {
            isAuthenticated = true;
            adminName = adminNameInput;
            statusLabel.setText("Autentikasi berhasil!");
            statusLabel.setForeground(new Color(0, 153, 0));
            
            // Delay sebentar untuk menampilkan pesan sukses
            Timer timer = new Timer(1000, e -> {
                dispose();
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            statusLabel.setText("Nama Admin atau Master Key salah!");
            statusLabel.setForeground(Color.RED);
            adminNameField.setText("");
            passwordField.setText("");
            adminNameField.requestFocus();
        }
    }
    
    public boolean isAuthenticated() {
        return isAuthenticated;
    }
    
    public String getAdminName() {
        return adminName;
    }
    
    public static void main(String[] args) {
        // Test dialog dengan look and feel default (kompatibel)
        SwingUtilities.invokeLater(() -> {
            try {
                MasterKeyDialog dialog = new MasterKeyDialog(null);
                dialog.setVisible(true);
                
                if (dialog.isAuthenticated()) {
                    JOptionPane.showMessageDialog(null, 
                        "Selamat datang, " + dialog.getAdminName() + "!", 
                        "Login Berhasil", 
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    System.out.println("Dialog ditutup tanpa autentikasi");
                }
            } catch (Exception e) {
                System.err.println("Error saat menjalankan dialog: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
    
    /**
     * Memposisikan dialog di tengah layar
     */
    private void centerDialog() {
        // Dapatkan ukuran layar
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        // Dapatkan ukuran dialog
        Dimension dialogSize = getSize();
        
        // Hitung posisi tengah
        int x = (screenSize.width - dialogSize.width) / 2;
        int y = (screenSize.height - dialogSize.height) / 2;
        
        // Set posisi dialog
        setLocation(x, y);
    }
} 