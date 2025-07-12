package model;

import java.sql.Timestamp;

public class LogAktivitas {
    private int id;
    private Integer idPengguna;
    private String aktivitas;
    private Timestamp waktu;

    // Konstruktor tanpa parameter
    public LogAktivitas() {}

    // Konstruktor dengan semua field
    public LogAktivitas(int id, Integer idPengguna, String aktivitas, Timestamp waktu) {
        this.id = id;
        this.idPengguna = idPengguna;
        this.aktivitas = aktivitas;
        this.waktu = waktu;
    }

    // Getter untuk id
    public int getId() {
        return id;
    }

    // Setter untuk id
    public void setId(int id) {
        this.id = id;
    }

    // Getter untuk idPengguna
    public Integer getIdPengguna() {
        return idPengguna;
    }

    // Setter untuk idPengguna
    public void setIdPengguna(Integer idPengguna) {
        this.idPengguna = idPengguna;
    }

    // Getter untuk aktivitas
    public String getAktivitas() {
        return aktivitas;
    }

    // Setter untuk aktivitas
    public void setAktivitas(String aktivitas) {
        this.aktivitas = aktivitas;
    }

    // Getter untuk waktu
    public Timestamp getWaktu() {
        return waktu;
    }

    // Setter untuk waktu
    public void setWaktu(Timestamp waktu) {
        this.waktu = waktu;
    }
} 