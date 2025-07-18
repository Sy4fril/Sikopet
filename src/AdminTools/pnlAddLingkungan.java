/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package AdminTools;

import DataAccessObject.LingkunganDAO;
import model.Lingkungan;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.Component;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.AbstractCellEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import DataAccessObject.LogAktivitasDAO;
import model.LogAktivitas;

/**
 *
 * @author HYPE AMD
 */
public class pnlAddLingkungan extends javax.swing.JPanel {

    private int selectedId = -1;

    /**
     * Creates new form pnlAddLingkungan
     */
    public pnlAddLingkungan() {
        initComponents();
        loadTableLingkungan();
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
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
        txt_namaDaerah = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_Alamat = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtRT = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtRW = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtNamaKetua = new javax.swing.JTextField();
        btnAddLingkungan = new javax.swing.JButton();
        btnEditLingkungan = new javax.swing.JButton();
        btnDeleteLingkungan = new javax.swing.JButton();
        tbl_dataLingkungan = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnRefreshTable = new javax.swing.JButton();
        lblTotalLingkungan = new javax.swing.JLabel();
        lblLingkunganAktif = new javax.swing.JLabel();
        lblLingkunganNonAktif = new javax.swing.JLabel();

        setBackground(new java.awt.Color(245, 245, 245));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("MANAJEMEN LINGKUNGAN");

        jLabel3.setText("Nama Daerah");

        jLabel4.setText("Alamat");

        jLabel5.setText("RT");

        jLabel6.setText("RW");

        jLabel7.setText("Nama Ketua");

        btnAddLingkungan.setBackground(new java.awt.Color(51, 255, 51));
        btnAddLingkungan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAddLingkungan.setText("Add");
        btnAddLingkungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddLingkunganActionPerformed(evt);
            }
        });

        btnEditLingkungan.setBackground(new java.awt.Color(255, 153, 0));
        btnEditLingkungan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEditLingkungan.setText("Edit");
        btnEditLingkungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditLingkunganActionPerformed(evt);
            }
        });

        btnDeleteLingkungan.setBackground(new java.awt.Color(255, 51, 51));
        btnDeleteLingkungan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDeleteLingkungan.setText("Delete");
        btnDeleteLingkungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteLingkunganActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_dataLingkungan.setViewportView(jTable1);

        btnRefreshTable.setBackground(new java.awt.Color(0, 153, 153));
        btnRefreshTable.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRefreshTable.setText("Refresh");
        btnRefreshTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshTableActionPerformed(evt);
            }
        });

        lblTotalLingkungan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTotalLingkungan.setText("Total Lingkungan :");

        lblLingkunganAktif.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblLingkunganAktif.setText("Lingkungan Aktif :");

        lblLingkunganNonAktif.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblLingkunganNonAktif.setText("Lingkungan Nonaktif:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tbl_dataLingkungan)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(txt_namaDaerah)
                                    .addComponent(jLabel4)
                                    .addComponent(txt_Alamat))
                                .addGap(55, 55, 55)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtRW, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtRT, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addGap(50, 50, 50)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(txtNamaKetua, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnAddLingkungan, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditLingkungan, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDeleteLingkungan, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addComponent(btnRefreshTable))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTotalLingkungan)
                            .addComponent(lblLingkunganAktif)
                            .addComponent(lblLingkunganNonAktif))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtRT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNamaKetua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRW, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_namaDaerah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_Alamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAddLingkungan, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEditLingkungan, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRefreshTable, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnDeleteLingkungan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(tbl_dataLingkungan, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTotalLingkungan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblLingkunganAktif)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblLingkunganNonAktif)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    
    private void loadTableLingkungan() {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Nama Daerah", "Alamat", "RT", "RW", "Nama Ketua", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Kolom status (6) bisa diedit (untuk toggle), lainnya tidak
                return column == 6;
            }
        };
        LingkunganDAO dao = new LingkunganDAO();
        List<Lingkungan> list = dao.getAllLingkungan(true); // true: ambil semua status
        for (Lingkungan l : list) {
            model.addRow(new Object[]{l.getId(), l.getNamaDaerah(), l.getAlamat(), l.getRt(), l.getRw(), l.getNamaKetua(), l.getStatus()});
        }
        jTable1.setModel(model);
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        // Set custom renderer/editor untuk kolom status
        jTable1.getColumnModel().getColumn(6).setCellRenderer(new StatusButtonRenderer());
        jTable1.getColumnModel().getColumn(6).setCellEditor(new StatusButtonEditor());
        updateLingkunganStatsLabels();
    }

    private void updateLingkunganStatsLabels() {
        DataAccessObject.LingkunganDAO dao = new DataAccessObject.LingkunganDAO();
        int total = dao.getAllLingkungan(true).size();
        int aktif = 0;
        int nonaktif = 0;
        for (model.Lingkungan l : dao.getAllLingkungan(true)) {
            if ("aktif".equalsIgnoreCase(l.getStatus())) aktif++;
            else if ("nonaktif".equalsIgnoreCase(l.getStatus())) nonaktif++;
        }
        lblTotalLingkungan.setText("Total Lingkungan : " + total);
        lblLingkunganAktif.setText("Lingkungan Aktif : " + aktif);
        lblLingkunganNonAktif.setText("Lingkungan Nonaktif: " + nonaktif);
    }

    private void clearForm() {
        txt_namaDaerah.setText("");
        txt_Alamat.setText("");
        txtRT.setText("");
        txtRW.setText("");
        txtNamaKetua.setText("");
        selectedId = -1;
        jTable1.clearSelection();
        btnAddLingkungan.setEnabled(true);
    }

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        int row = jTable1.getSelectedRow();
        if (row != -1) {
            selectedId = Integer.parseInt(jTable1.getValueAt(row, 0).toString());
            txt_namaDaerah.setText(jTable1.getValueAt(row, 1).toString());
            txt_Alamat.setText(jTable1.getValueAt(row, 2) == null ? "" : jTable1.getValueAt(row, 2).toString());
            txtRT.setText(jTable1.getValueAt(row, 3).toString());
            txtRW.setText(jTable1.getValueAt(row, 4).toString());
            txtNamaKetua.setText(jTable1.getValueAt(row, 5) == null ? "" : jTable1.getValueAt(row, 5).toString());
            btnAddLingkungan.setEnabled(false);
        }
    }

    private void btnAddLingkunganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddLingkunganActionPerformed
        String namaDaerah = txt_namaDaerah.getText();
        String alamat = txt_Alamat.getText();
        String rt = txtRT.getText();
        String rw = txtRW.getText();
        String namaKetua = txtNamaKetua.getText();
        if (namaDaerah.isEmpty() || rt.isEmpty() || rw.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama Daerah, RT, dan RW wajib diisi.");
            return;
        }
        Lingkungan l = new Lingkungan(rt, rw, namaKetua, alamat, namaDaerah);
        LingkunganDAO dao = new LingkunganDAO();
        if (dao.insertLingkungan(l)) {
            JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan.");
            // Log tambah lingkungan
            LogAktivitasDAO logDao = new LogAktivitasDAO();
            String logDesc = "Menambah lingkungan: " + namaDaerah + ", RT " + rt + "/RW " + rw + ", Ketua: " + namaKetua;
            LogAktivitas log = new LogAktivitas(0, null, logDesc, new java.sql.Timestamp(System.currentTimeMillis()));
            logDao.insertLogAktivitas(log);
            loadTableLingkungan();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menambah data.");
        }
    }//GEN-LAST:event_btnAddLingkunganActionPerformed

    private void btnEditLingkunganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditLingkunganActionPerformed
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan diedit.");
            return;
        }
        String namaDaerah = txt_namaDaerah.getText();
        String alamat = txt_Alamat.getText();
        String rt = txtRT.getText();
        String rw = txtRW.getText();
        String namaKetua = txtNamaKetua.getText();
        if (namaDaerah.isEmpty() || rt.isEmpty() || rw.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama Daerah, RT, dan RW wajib diisi.");
            return;
        }
        Lingkungan l = new Lingkungan(selectedId, rt, rw, namaKetua, alamat, namaDaerah, "aktif", null);
        LingkunganDAO dao = new LingkunganDAO();
        if (dao.updateLingkungan(l)) {
            JOptionPane.showMessageDialog(this, "Data berhasil diupdate.");
            // Log edit lingkungan
            LogAktivitasDAO logDao = new LogAktivitasDAO();
            String logDesc = "Mengedit lingkungan: " + namaDaerah + ", RT " + rt + "/RW " + rw + ", Ketua: " + namaKetua;
            LogAktivitas log = new LogAktivitas(0, null, logDesc, new java.sql.Timestamp(System.currentTimeMillis()));
            logDao.insertLogAktivitas(log);
            loadTableLingkungan();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal mengupdate data.");
        }
    }//GEN-LAST:event_btnEditLingkunganActionPerformed

    private void btnDeleteLingkunganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteLingkunganActionPerformed
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            LingkunganDAO dao = new LingkunganDAO();
            if (dao.deleteLingkungan(selectedId)) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
                // Log hapus lingkungan
                LogAktivitasDAO logDao = new LogAktivitasDAO();
                String logDesc = "Menghapus lingkungan: " + txt_namaDaerah.getText() + ", RT " + txtRT.getText() + "/RW " + txtRW.getText();
                LogAktivitas log = new LogAktivitas(0, null, logDesc, new java.sql.Timestamp(System.currentTimeMillis()));
                logDao.insertLogAktivitas(log);
                loadTableLingkungan();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data.");
            }
        }
    }//GEN-LAST:event_btnDeleteLingkunganActionPerformed

    private void btnRefreshTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshTableActionPerformed
        loadTableLingkungan();
        clearForm();
    }//GEN-LAST:event_btnRefreshTableActionPerformed

    // Custom renderer untuk tombol status
    class StatusButtonRenderer extends JButton implements TableCellRenderer {
        public StatusButtonRenderer() {
            setOpaque(true);
            // Set font to bold and black
            setFont(getFont().deriveFont(java.awt.Font.BOLD));
            setForeground(Color.BLACK);
        }
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            String status = value == null ? "" : value.toString();
            setText(status.equalsIgnoreCase("aktif") ? "Aktif" : "Nonaktif");
            setBackground(status.equalsIgnoreCase("aktif") ? new Color(76, 175, 80) : new Color(244, 67, 54));
            setFont(getFont().deriveFont(java.awt.Font.BOLD));
            setForeground(Color.BLACK);
            // Agar button tidak memenuhi kolom, set margin dan preferred size
            setMargin(new java.awt.Insets(2, 8, 2, 8));
            setHorizontalAlignment(CENTER);
            return this;
        }
    }

    // Custom editor untuk tombol status
    class StatusButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private JButton button = new JButton();
        private String currentStatus;
        private int currentRow;
        public StatusButtonEditor() {
            button.setOpaque(true);
            button.setFont(button.getFont().deriveFont(java.awt.Font.BOLD));
            button.setForeground(Color.BLACK);
            button.setMargin(new java.awt.Insets(2, 8, 2, 8));
            button.setHorizontalAlignment(JButton.CENTER);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    toggleStatus(currentRow);
                }
            });
        }
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            currentStatus = value == null ? "" : value.toString();
            currentRow = row;
            button.setText(currentStatus.equalsIgnoreCase("aktif") ? "Aktif" : "Nonaktif");
            button.setBackground(currentStatus.equalsIgnoreCase("aktif") ? new Color(76, 175, 80) : new Color(244, 67, 54));
            button.setFont(button.getFont().deriveFont(java.awt.Font.BOLD));
            button.setForeground(Color.BLACK);
            button.setMargin(new java.awt.Insets(2, 8, 2, 8));
            button.setHorizontalAlignment(JButton.CENTER);
            return button;
        }
        public Object getCellEditorValue() {
            return currentStatus;
        }
    }

    // Fungsi untuk toggle status dengan konfirmasi
    private void toggleStatus(int row) {
        int id = Integer.parseInt(jTable1.getValueAt(row, 0).toString());
        String namaDaerah = jTable1.getValueAt(row, 1).toString();
        String rt = jTable1.getValueAt(row, 3).toString();
        String rw = jTable1.getValueAt(row, 4).toString();
        String statusLama = jTable1.getValueAt(row, 6).toString();
        String statusBaru = statusLama.equalsIgnoreCase("aktif") ? "nonaktif" : "aktif";
        int confirm = JOptionPane.showConfirmDialog(this, "Ubah status lingkungan '" + namaDaerah + "' menjadi '" + statusBaru + "'?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            LingkunganDAO dao = new LingkunganDAO();
            if (dao.updateStatusLingkungan(id, statusBaru)) {
                JOptionPane.showMessageDialog(this, "Status berhasil diubah.");
                // Log ubah status lingkungan
                LogAktivitasDAO logDao = new LogAktivitasDAO();
                String logDesc = "Mengubah status lingkungan: " + namaDaerah + ", RT " + rt + "/RW " + rw + " menjadi " + statusBaru;
                LogAktivitas log = new LogAktivitas(0, null, logDesc, new java.sql.Timestamp(System.currentTimeMillis()));
                logDao.insertLogAktivitas(log);
                loadTableLingkungan();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengubah status.");
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddLingkungan;
    private javax.swing.JButton btnDeleteLingkungan;
    private javax.swing.JButton btnEditLingkungan;
    private javax.swing.JButton btnRefreshTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblLingkunganAktif;
    private javax.swing.JLabel lblLingkunganNonAktif;
    private javax.swing.JLabel lblTotalLingkungan;
    private javax.swing.JScrollPane tbl_dataLingkungan;
    private javax.swing.JTextField txtNamaKetua;
    private javax.swing.JTextField txtRT;
    private javax.swing.JTextField txtRW;
    private javax.swing.JTextField txt_Alamat;
    private javax.swing.JTextField txt_namaDaerah;
    // End of variables declaration//GEN-END:variables
}
