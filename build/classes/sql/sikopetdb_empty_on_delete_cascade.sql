-- SQL Dump: sikopetdb (semua ON DELETE CASCADE, tanpa data)
CREATE DATABASE IF NOT EXISTS sikopetdb;
USE sikopetdb;

-- Tabel admin
CREATE TABLE admin (
  id INT(11) NOT NULL AUTO_INCREMENT,
  nama_admin VARCHAR(100) NOT NULL,
  master_key VARCHAR(100) NOT NULL,
  dibuat_pada TIMESTAMP NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (id),
  UNIQUE KEY master_key (master_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabel lingkungan
CREATE TABLE lingkungan (
  id INT(11) NOT NULL AUTO_INCREMENT,
  rt VARCHAR(5) NOT NULL,
  rw VARCHAR(5) NOT NULL,
  nama_ketua VARCHAR(100) DEFAULT NULL,
  alamat TEXT DEFAULT NULL,
  nama_daerah TEXT NOT NULL,
  status ENUM('aktif','nonaktif') DEFAULT 'aktif',
  dibuat_pada TIMESTAMP NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabel pengguna
CREATE TABLE pengguna (
  id INT(11) NOT NULL AUTO_INCREMENT,
  nama_pengguna VARCHAR(100) NOT NULL,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(255) NOT NULL,
  role ENUM('ketua','bendahara','warga') NOT NULL,
  email VARCHAR(100) DEFAULT NULL,
  status ENUM('aktif','nonaktif') DEFAULT 'aktif',
  id_lingkungan INT(11) DEFAULT NULL,
  dibuat_pada TIMESTAMP NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (id),
  UNIQUE KEY username (username),
  KEY id_lingkungan (id_lingkungan),
  CONSTRAINT pengguna_ibfk_1 FOREIGN KEY (id_lingkungan) REFERENCES lingkungan(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabel warga
CREATE TABLE warga (
  id INT(11) NOT NULL AUTO_INCREMENT,
  id_pengguna INT(11) DEFAULT NULL,
  id_lingkungan INT(11) NOT NULL,
  nama_warga VARCHAR(100) NOT NULL,
  nik VARCHAR(20) NOT NULL,
  no_hp VARCHAR(20) DEFAULT NULL,
  email VARCHAR(100) DEFAULT NULL,
  alamat TEXT DEFAULT NULL,
  status ENUM('aktif','nonaktif') DEFAULT 'aktif',
  dibuat_pada TIMESTAMP NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (id),
  UNIQUE KEY nik (nik),
  KEY id_lingkungan (id_lingkungan),
  KEY id_pengguna (id_pengguna),
  CONSTRAINT warga_ibfk_1 FOREIGN KEY (id_lingkungan) REFERENCES lingkungan(id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT warga_ibfk_2 FOREIGN KEY (id_pengguna) REFERENCES pengguna(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabel log_aktivitas
CREATE TABLE log_aktivitas (
  id INT(11) NOT NULL AUTO_INCREMENT,
  id_pengguna INT(11) DEFAULT NULL,
  aktivitas TEXT DEFAULT NULL,
  waktu TIMESTAMP NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (id),
  KEY id_pengguna (id_pengguna),
  CONSTRAINT log_aktivitas_ibfk_1 FOREIGN KEY (id_pengguna) REFERENCES pengguna(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabel notifikasi
CREATE TABLE notifikasi (
  id INT(11) NOT NULL AUTO_INCREMENT,
  id_warga INT(11) DEFAULT NULL,
  judul VARCHAR(255) NOT NULL,
  pesan TEXT DEFAULT NULL,
  waktu TIMESTAMP NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (id),
  CONSTRAINT notifikasi_ibfk_1 FOREIGN KEY (id_warga) REFERENCES pengguna(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabel saldo_tabungan
CREATE TABLE saldo_tabungan (
  id INT(11) NOT NULL AUTO_INCREMENT,
  id_warga INT(11) NOT NULL,
  saldo DECIMAL(12,2) NOT NULL,
  last_update DATETIME NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (id),
  KEY fk_saldo_warga (id_warga),
  CONSTRAINT fk_saldo_warga FOREIGN KEY (id_warga) REFERENCES warga(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabel tabungan
CREATE TABLE tabungan (
  id INT(11) NOT NULL AUTO_INCREMENT,
  id_warga INT(11) NOT NULL,
  jenis ENUM('setoran','penarikan') NOT NULL,
  jumlah DECIMAL(12,2) NOT NULL,
  tanggal DATETIME DEFAULT current_timestamp(),
  metode ENUM('tunai','qris','transfer') DEFAULT 'tunai',
  keterangan TEXT DEFAULT NULL,
  PRIMARY KEY (id),
  KEY id_warga (id_warga),
  CONSTRAINT tabungan_ibfk_1 FOREIGN KEY (id_warga) REFERENCES warga(id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabel transaksi
CREATE TABLE transaksi (
  id INT(11) NOT NULL AUTO_INCREMENT,
  id_lingkungan INT(11) NOT NULL,
  tipe ENUM('pemasukan','pengeluaran') NOT NULL,
  jenis VARCHAR(100) DEFAULT NULL,
  jumlah INT(11) NOT NULL,
  tanggal DATETIME DEFAULT current_timestamp(),
  sumber VARCHAR(100) DEFAULT NULL,
  keterangan TEXT DEFAULT NULL,
  id_dibuat_oleh INT(11) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY id_lingkungan (id_lingkungan),
  KEY id_dibuat_oleh (id_dibuat_oleh),
  CONSTRAINT transaksi_ibfk_1 FOREIGN KEY (id_lingkungan) REFERENCES lingkungan(id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT transaksi_ibfk_2 FOREIGN KEY (id_dibuat_oleh) REFERENCES pengguna(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabel verifikasi_transaksi
CREATE TABLE verifikasi_transaksi (
  id INT(11) NOT NULL AUTO_INCREMENT,
  id_transaksi INT(11) NOT NULL,
  status ENUM('disetujui','ditolak','pending') NOT NULL DEFAULT 'pending',
  alasan TEXT DEFAULT NULL,
  waktu TIMESTAMP NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (id),
  KEY id_transaksi (id_transaksi) USING BTREE,
  CONSTRAINT verifikasi_transaksi_ibfk_3 FOREIGN KEY (id_transaksi) REFERENCES transaksi(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4; 