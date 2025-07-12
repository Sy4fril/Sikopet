package model;

import java.sql.Timestamp;

/**
 * Model class untuk entitas Admin
 * Mewakili tabel admin dalam database
 */
public class Admin {
    private int id;
    private String namaAdmin;
    private String masterKey;
    private Timestamp dibuatPada;
    
    // Konstruktor tanpa parameter
    public Admin() {}
    
    // Konstruktor dengan namaAdmin dan masterKey
    public Admin(String namaAdmin, String masterKey) {
        this.namaAdmin = namaAdmin;
        this.masterKey = masterKey;
    }
    
    // Konstruktor dengan semua field
    public Admin(int id, String namaAdmin, String masterKey, Timestamp dibuatPada) {
        this.id = id;
        this.namaAdmin = namaAdmin;
        this.masterKey = masterKey;
        this.dibuatPada = dibuatPada;
    }
    
    // Getter untuk id
    public int getId() {
        return id;
    }
    
    // Setter untuk id
    public void setId(int id) {
        this.id = id;
    }
    
    // Getter untuk namaAdmin
    public String getNamaAdmin() {
        return namaAdmin;
    }
    
    // Setter untuk namaAdmin
    public void setNamaAdmin(String namaAdmin) {
        this.namaAdmin = namaAdmin;
    }
    
    // Getter untuk masterKey
    public String getMasterKey() {
        return masterKey;
    }
    
    // Setter untuk masterKey
    public void setMasterKey(String masterKey) {
        this.masterKey = masterKey;
    }
    
    // Getter untuk dibuatPada
    public Timestamp getDibuatPada() {
        return dibuatPada;
    }
    
    // Setter untuk dibuatPada
    public void setDibuatPada(Timestamp dibuatPada) {
        this.dibuatPada = dibuatPada;
    }
    
    // Mengecek validitas data admin
    public boolean isValid() {
        return namaAdmin != null && !namaAdmin.trim().isEmpty() &&
               masterKey != null && !masterKey.trim().isEmpty() &&
               masterKey.length() >= 6; // Minimal 6 karakter untuk keamanan
    }
    
    // Mengembalikan pesan validasi data admin
    public String getValidationMessage() {
        if (namaAdmin == null || namaAdmin.trim().isEmpty()) {
            return "Nama Admin tidak boleh kosong";
        }
        if (masterKey == null || masterKey.trim().isEmpty()) {
            return "Master Key tidak boleh kosong";
        }
        if (masterKey.length() < 6) {
            return "Master Key minimal 6 karakter";
        }
        return "Valid";
    }
    
    // Mengembalikan representasi string dari objek Admin
    @Override
    public String toString() {
        return namaAdmin;
    }
} 