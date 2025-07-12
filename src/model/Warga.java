package model;

import java.sql.Timestamp;

public class Warga {
    private int id;
    private int idPengguna;
    private int idLingkungan;
    private String namaWarga;
    private String nik;
    private String noHp;
    private String email;
    private String alamat;
    private String status;
    private Timestamp dibuatPada;

    // Konstruktor tanpa parameter
    public Warga() {}

    // Konstruktor dengan semua field
    public Warga(int id, int idPengguna, int idLingkungan, String namaWarga, String nik, String noHp, String email, String alamat, String status, Timestamp dibuatPada) {
        this.id = id;
        this.idPengguna = idPengguna;
        this.idLingkungan = idLingkungan;
        this.namaWarga = namaWarga;
        this.nik = nik;
        this.noHp = noHp;
        this.email = email;
        this.alamat = alamat;
        this.status = status;
        this.dibuatPada = dibuatPada;
    }

    // Getter dan setter untuk setiap field
    // Getter untuk id
    public int getId() { return id; }
    // Setter untuk id
    public void setId(int id) { this.id = id; }
    // Getter untuk idPengguna
    public int getIdPengguna() { return idPengguna; }
    // Setter untuk idPengguna
    public void setIdPengguna(int idPengguna) { this.idPengguna = idPengguna; }
    // Getter untuk idLingkungan
    public int getIdLingkungan() { return idLingkungan; }
    // Setter untuk idLingkungan
    public void setIdLingkungan(int idLingkungan) { this.idLingkungan = idLingkungan; }
    // Getter untuk namaWarga
    public String getNamaWarga() { return namaWarga; }
    // Setter untuk namaWarga
    public void setNamaWarga(String namaWarga) { this.namaWarga = namaWarga; }
    // Getter untuk nik
    public String getNik() { return nik; }
    // Setter untuk nik
    public void setNik(String nik) { this.nik = nik; }
    // Getter untuk noHp
    public String getNoHp() { return noHp; }
    // Setter untuk noHp
    public void setNoHp(String noHp) { this.noHp = noHp; }
    // Getter untuk email
    public String getEmail() { return email; }
    // Setter untuk email
    public void setEmail(String email) { this.email = email; }
    // Getter untuk alamat
    public String getAlamat() { return alamat; }
    // Setter untuk alamat
    public void setAlamat(String alamat) { this.alamat = alamat; }
    // Getter untuk status
    public String getStatus() { return status; }
    // Setter untuk status
    public void setStatus(String status) { this.status = status; }
    // Getter untuk dibuatPada
    public Timestamp getDibuatPada() { return dibuatPada; }
    // Setter untuk dibuatPada
    public void setDibuatPada(Timestamp dibuatPada) { this.dibuatPada = dibuatPada; }
} 