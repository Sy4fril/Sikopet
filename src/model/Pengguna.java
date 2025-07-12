package model;

import java.sql.Timestamp;

public class Pengguna {
    private int id;
    private String namaPengguna;
    private String username;
    private String password;
    private String role;
    private String email;
    private String status;
    private int idLingkungan;
    private Timestamp dibuatPada;

    // Konstruktor tanpa parameter
    public Pengguna() {}

    // Konstruktor dengan semua field
    public Pengguna(int id, String namaPengguna, String username, String password, String role, String email, String status, int idLingkungan, Timestamp dibuatPada) {
        this.id = id;
        this.namaPengguna = namaPengguna;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.status = status;
        this.idLingkungan = idLingkungan;
        this.dibuatPada = dibuatPada;
    }

    // Konstruktor untuk input pengguna baru (status default aktif)
    public Pengguna(String namaPengguna, String username, String password, String role, String email, int idLingkungan) {
        this.namaPengguna = namaPengguna;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.status = "aktif";
        this.idLingkungan = idLingkungan;
    }

    // Getter dan setter untuk setiap field
    // Getter untuk id
    public int getId() { return id; }
    // Setter untuk id
    public void setId(int id) { this.id = id; }
    // Getter untuk namaPengguna
    public String getNamaPengguna() { return namaPengguna; }
    // Setter untuk namaPengguna
    public void setNamaPengguna(String namaPengguna) { this.namaPengguna = namaPengguna; }
    // Getter untuk username
    public String getUsername() { return username; }
    // Setter untuk username
    public void setUsername(String username) { this.username = username; }
    // Getter untuk password
    public String getPassword() { return password; }
    // Setter untuk password
    public void setPassword(String password) { this.password = password; }
    // Getter untuk role
    public String getRole() { return role; }
    // Setter untuk role
    public void setRole(String role) { this.role = role; }
    // Getter untuk email
    public String getEmail() { return email; }
    // Setter untuk email
    public void setEmail(String email) { this.email = email; }
    // Getter untuk status
    public String getStatus() { return status; }
    // Setter untuk status
    public void setStatus(String status) { this.status = status; }
    // Getter untuk idLingkungan
    public int getIdLingkungan() { return idLingkungan; }
    // Setter untuk idLingkungan
    public void setIdLingkungan(int idLingkungan) { this.idLingkungan = idLingkungan; }
    // Getter untuk dibuatPada
    public Timestamp getDibuatPada() { return dibuatPada; }
    // Setter untuk dibuatPada
    public void setDibuatPada(Timestamp dibuatPada) { this.dibuatPada = dibuatPada; }
} 