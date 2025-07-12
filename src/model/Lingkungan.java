package model;

import java.sql.Timestamp;

public class Lingkungan {
    private int id;
    private String rt;
    private String rw;
    private String namaKetua;
    private String alamat;
    private String namaDaerah;
    private String status;
    private Timestamp dibuatPada;

    // Konstruktor tanpa parameter
    public Lingkungan() {}

    // Konstruktor dengan semua field
    public Lingkungan(int id, String rt, String rw, String namaKetua, String alamat, String namaDaerah, String status, Timestamp dibuatPada) {
        this.id = id;
        this.rt = rt;
        this.rw = rw;
        this.namaKetua = namaKetua;
        this.alamat = alamat;
        this.namaDaerah = namaDaerah;
        this.status = status;
        this.dibuatPada = dibuatPada;
    }

    // Konstruktor untuk input lingkungan baru (status default aktif)
    public Lingkungan(String rt, String rw, String namaKetua, String alamat, String namaDaerah) {
        this.rt = rt;
        this.rw = rw;
        this.namaKetua = namaKetua;
        this.alamat = alamat;
        this.namaDaerah = namaDaerah;
        this.status = "aktif";
    }

    // Getter dan setter untuk setiap field
    // Getter untuk id
    public int getId() { return id; }
    // Setter untuk id
    public void setId(int id) { this.id = id; }
    // Getter untuk rt
    public String getRt() { return rt; }
    // Setter untuk rt
    public void setRt(String rt) { this.rt = rt; }
    // Getter untuk rw
    public String getRw() { return rw; }
    // Setter untuk rw
    public void setRw(String rw) { this.rw = rw; }
    // Getter untuk namaKetua
    public String getNamaKetua() { return namaKetua; }
    // Setter untuk namaKetua
    public void setNamaKetua(String namaKetua) { this.namaKetua = namaKetua; }
    // Getter untuk alamat
    public String getAlamat() { return alamat; }
    // Setter untuk alamat
    public void setAlamat(String alamat) { this.alamat = alamat; }
    // Getter untuk namaDaerah
    public String getNamaDaerah() { return namaDaerah; }
    // Setter untuk namaDaerah
    public void setNamaDaerah(String namaDaerah) { this.namaDaerah = namaDaerah; }
    // Getter untuk status
    public String getStatus() { return status; }
    // Setter untuk status
    public void setStatus(String status) { this.status = status; }
    // Getter untuk dibuatPada
    public Timestamp getDibuatPada() { return dibuatPada; }
    // Setter untuk dibuatPada
    public void setDibuatPada(Timestamp dibuatPada) { this.dibuatPada = dibuatPada; }
} 