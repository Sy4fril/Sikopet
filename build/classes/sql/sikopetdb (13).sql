-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 11 Jul 2025 pada 18.23
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sikopetdb`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `nama_admin` varchar(100) NOT NULL,
  `master_key` varchar(100) NOT NULL,
  `dibuat_pada` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `admin`
--

INSERT INTO `admin` (`id`, `nama_admin`, `master_key`, `dibuat_pada`) VALUES
(1, 'Riel', '123', '2025-07-06 12:56:11');

-- --------------------------------------------------------

--
-- Struktur dari tabel `lingkungan`
--

CREATE TABLE `lingkungan` (
  `id` int(11) NOT NULL,
  `rt` varchar(5) NOT NULL,
  `rw` varchar(5) NOT NULL,
  `nama_ketua` varchar(100) DEFAULT NULL,
  `alamat` text DEFAULT NULL,
  `nama_daerah` text NOT NULL,
  `status` enum('aktif','nonaktif') DEFAULT 'aktif',
  `dibuat_pada` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `lingkungan`
--

INSERT INTO `lingkungan` (`id`, `rt`, `rw`, `nama_ketua`, `alamat`, `nama_daerah`, `status`, `dibuat_pada`) VALUES
(1, '01', '02', 'Pak Slamet', 'Jl. Dahlia No. 5', 'Citra Graha', 'aktif', '2025-06-26 12:08:17'),
(2, '03', '04', 'Bu Ani', 'Jl. Melati No. 9', 'Taman Indah', 'aktif', '2025-06-26 12:08:17');

-- --------------------------------------------------------

--
-- Struktur dari tabel `log_aktivitas`
--

CREATE TABLE `log_aktivitas` (
  `id` int(11) NOT NULL,
  `id_pengguna` int(11) DEFAULT NULL,
  `aktivitas` text DEFAULT NULL,
  `waktu` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `log_aktivitas`
--

INSERT INTO `log_aktivitas` (`id`, `id_pengguna`, `aktivitas`, `waktu`) VALUES
(1, 2, 'Menyetujui penarikan dana warga', '2025-06-26 12:08:17'),
(2, 1, 'Menambahkan transaksi pemasukan', '2025-06-26 12:08:17'),
(3, NULL, 'Menambah user baru: bjiku (Biji) dengan role bendahara', '2025-06-26 14:17:34'),
(4, NULL, 'Menambah lingkungan: Benteng, RT 001/RW 002, Ketua: Baso', '2025-06-26 14:18:54'),
(5, NULL, 'Menghapus lingkungan: Benteng, RT 001/RW 002', '2025-06-26 14:22:21'),
(6, NULL, 'Menghapus lingkungan: Benteng, RT 001/RW 002', '2025-06-26 14:25:54'),
(7, NULL, 'Menghapus lingkungan: Benteng, RT 001/RW 002', '2025-06-26 14:26:48'),
(8, NULL, 'Mengedit user: bendahara01 (Bendahara RT01) dengan role bendahara', '2025-06-26 14:36:13'),
(9, NULL, 'Mengedit user: bjiku (Biji) dengan role bendahara', '2025-06-26 14:36:31'),
(10, NULL, 'Mengubah status user: sari456 (Warga Sari) menjadi nonaktif', '2025-06-27 06:48:14'),
(11, NULL, 'Mengubah status user: sari456 (Warga Sari) menjadi aktif', '2025-06-27 06:48:40'),
(12, NULL, 'Menambah lingkungan: Benteng, RT 001/RW 002, Ketua: biji', '2025-06-27 07:13:56'),
(13, NULL, 'Mengedit user: cuy (riel) dengan role bendahara', '2025-06-27 07:32:24'),
(14, NULL, 'Mengedit user: cuy (riel) dengan role bendahara', '2025-06-27 07:33:33'),
(15, NULL, 'Menghapus user: cuy (riel)', '2025-06-27 07:34:29'),
(16, NULL, 'Menambah user baru: bijiku (kotol) dengan role bendahara', '2025-06-27 07:35:09'),
(17, 5, 'Menambahkan pendapatan: iuran sebesar Rp 600,000', '2025-06-29 14:13:19'),
(18, 7, 'Menambahkan pendapatan: DOnasi sebesar Rp 1,000,000', '2025-06-29 14:38:17'),
(19, 5, 'Menambahkan pengeluaran: Bayar kebersihan sebesar Rp 40,000', '2025-06-29 14:57:39'),
(20, 5, 'Menambahkan pendapatan: Donasi sebesar Rp1.000.000.000,00', '2025-06-30 14:20:46'),
(21, 5, 'Mengubah data pendapatan ID: 6', '2025-06-30 14:21:17'),
(22, 5, 'Menambahkan pengeluaran: Bayar kebersihan sebesar Rp500.000,00', '2025-06-30 14:43:47'),
(23, 5, 'Melakukan verifikasi: disetujui penarikan (ID: 4) sebesar Rp50.000,00', '2025-06-30 15:06:40'),
(24, 5, 'Melakukan verifikasi: ditolak penarikan (ID: 6) sebesar Rp300.000,00', '2025-06-30 15:08:00'),
(25, 5, 'Menambahkan pengeluaran: asdas sebesar Rp121.212,00', '2025-06-30 16:23:11'),
(26, 5, 'Menambahkan pendapatan: dasd sebesar Rp1.231.123,00', '2025-06-30 16:24:33'),
(27, 5, 'Mengajukan pengeluaran: testing sebesar Rp1.000.000,00 (menunggu persetujuan ketua)', '2025-06-30 16:30:53'),
(28, 5, 'Mengajukan pengeluaran: Testing server sebesar Rp1.000.000,00 (menunggu persetujuan ketua)', '2025-06-30 16:33:00'),
(29, 5, 'Mengajukan pengeluaran: Test ujisn sebesar Rp100.000,00 (menunggu persetujuan ketua)', '2025-06-30 16:35:11'),
(30, NULL, 'Menambah user baru: besseji (Besse) dengan role ketua', '2025-07-01 15:17:01'),
(31, NULL, 'Menambah user baru: AjiJIe (Aji) dengan role bendahara', '2025-07-01 15:43:58'),
(32, NULL, 'Menambah user baru: sgs (RI) dengan role ketua', '2025-07-01 15:49:38'),
(33, NULL, 'Mengedit user: bijiku (Aco) dengan role bendahara', '2025-07-04 07:00:44'),
(34, NULL, 'Mengedit user: AcoJi (Aco) dengan role bendahara', '2025-07-04 07:01:00'),
(35, NULL, 'Mengedit user: AmbooJi (Ambo) dengan role bendahara', '2025-07-04 07:01:36'),
(36, 9, 'Mengajukan pengeluaran: uang sampah sebesar Rp30.000,00 (menunggu persetujuan ketua)', '2025-07-06 16:22:11'),
(37, NULL, 'Mengedit user: AcoJi (Aco) dengan role bendahara', '2025-07-08 07:04:46'),
(38, 5, 'Menambahkan pendapatan: testing sebesar Rp10.000.000,00', '2025-07-11 07:13:06'),
(39, NULL, 'Menghapus lingkungan: Benteng, RT 001/RW 002', '2025-07-11 16:15:49');

-- --------------------------------------------------------

--
-- Struktur dari tabel `notifikasi`
--

CREATE TABLE `notifikasi` (
  `id` int(11) NOT NULL,
  `id_warga` int(11) DEFAULT NULL,
  `judul` varchar(255) NOT NULL,
  `pesan` text DEFAULT NULL,
  `waktu` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `notifikasi`
--

INSERT INTO `notifikasi` (`id`, `id_warga`, `judul`, `pesan`, `waktu`) VALUES
(1, 3, '', 'Tabungan Anda sedang diproses', '2025-06-26 12:08:17'),
(2, 4, '', 'Penarikan Anda telah disetujui', '2025-06-26 12:08:17'),
(3, 5, '', 'Penarikan tabungan Anda sejumlah Rp50.000,00 telah disetujui oleh bendahara.', '2025-06-30 15:06:40'),
(4, 4, '', 'Penarikan tabungan Anda sejumlah Rp300.000,00 ditolak. Alasan: gamau', '2025-06-30 15:08:00'),
(5, 3, 'Bayar iuran', 'bayar iurannya kang totalnya Rp.60.000,00', '2025-07-06 16:40:18'),
(6, 2, 'Pesan dari Ketua', 'Testing', '2025-07-06 16:58:17'),
(7, 3, 'Pesan dari Ketua', 'Testing', '2025-07-06 16:58:17'),
(8, 8, 'Pesan dari Ketua', 'Testing', '2025-07-06 16:58:17'),
(9, 9, 'Pesan dari Ketua', 'Testing', '2025-07-06 16:58:17'),
(10, 2, 'Pesan dari Ketua', 'Halo warga', '2025-07-06 17:08:51'),
(11, 3, 'Pesan dari Ketua', 'Halo warga', '2025-07-06 17:08:51'),
(12, 8, 'Pesan dari Ketua', 'Halo warga', '2025-07-06 17:08:51'),
(13, 9, 'Pesan dari Ketua', 'Halo warga', '2025-07-06 17:08:51'),
(19, 3, 'Broadcast Admin', 'Hola', '2025-07-08 07:40:21'),
(20, 4, 'Broadcast Admin', 'Hola', '2025-07-08 07:40:21'),
(21, 5, 'Broadcast Admin', 'Hola', '2025-07-08 07:40:21'),
(22, 7, 'Broadcast Admin', 'Hola', '2025-07-08 07:40:21'),
(23, 1, 'Broadcast Admin', 'Hola', '2025-07-08 07:40:21'),
(24, 8, 'Broadcast Admin', 'Hola', '2025-07-08 07:40:21'),
(25, 9, 'Broadcast Admin', 'Hola', '2025-07-08 07:40:21'),
(26, 10, 'Broadcast Admin', 'Hola', '2025-07-08 07:40:21');

-- --------------------------------------------------------

--
-- Struktur dari tabel `pengguna`
--

CREATE TABLE `pengguna` (
  `id` int(11) NOT NULL,
  `nama_pengguna` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('ketua','bendahara','warga') NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `status` enum('aktif','nonaktif') DEFAULT 'aktif',
  `id_lingkungan` int(11) DEFAULT NULL,
  `dibuat_pada` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `pengguna`
--

INSERT INTO `pengguna` (`id`, `nama_pengguna`, `username`, `password`, `role`, `email`, `status`, `id_lingkungan`, `dibuat_pada`) VALUES
(1, 'Ketua RT01', 'ketua01', 'password1', 'ketua', 'ketua01@example.com', 'aktif', 1, '2025-06-26 12:08:17'),
(2, 'Bendahara RT01', 'bendahara01', 'password2', 'bendahara', 'bendahara01@example.com', 'aktif', 1, '2025-06-26 12:08:17'),
(3, 'Warga Budi', 'budi123', 'password3', 'warga', 'budi@example.com', 'aktif', 1, '2025-06-26 12:08:17'),
(4, 'Warga Sari', 'sari456', 'password4', 'warga', 'sari@example.com', 'aktif', 2, '2025-06-26 12:08:17'),
(5, 'Ambo', 'AmbooJi', '321', 'bendahara', 'Ambo@gmail.com', 'aktif', 1, '2025-06-26 14:17:32'),
(7, 'Aco', 'AcoJi', '321', 'bendahara', 'Aco@gmail.com', 'aktif', 1, '2025-06-27 07:35:08'),
(8, 'Besse', 'besseji', '321', 'ketua', 'besse@gmail.com', 'aktif', NULL, '2025-07-01 15:17:00'),
(9, 'Aji', 'AjiJIe', '321', 'bendahara', 'Aji@gmail.com', 'aktif', 2, '2025-07-01 15:43:57'),
(10, 'RI', 'sgs', '321', 'ketua', 'Ri', 'aktif', 2, '2025-07-01 15:49:36');

-- --------------------------------------------------------

--
-- Struktur dari tabel `saldo_tabungan`
--

CREATE TABLE `saldo_tabungan` (
  `id` int(11) NOT NULL,
  `id_warga` int(11) NOT NULL,
  `saldo` decimal(12,2) NOT NULL,
  `last_update` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `saldo_tabungan`
--

INSERT INTO `saldo_tabungan` (`id`, `id_warga`, `saldo`, `last_update`) VALUES
(1, 1, 250000.00, '2025-06-29 23:45:30'),
(2, 2, 480185096.00, '2025-07-11 23:54:39'),
(3, 3, 0.00, '2025-06-29 23:45:30'),
(4, 5, 0.00, '2025-06-29 23:45:30');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tabungan`
--

CREATE TABLE `tabungan` (
  `id` int(11) NOT NULL,
  `id_warga` int(11) NOT NULL,
  `jenis` enum('setoran','penarikan') NOT NULL,
  `jumlah` decimal(12,2) NOT NULL,
  `tanggal` datetime DEFAULT current_timestamp(),
  `metode` enum('tunai','qris','transfer') DEFAULT 'tunai',
  `keterangan` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tabungan`
--

INSERT INTO `tabungan` (`id`, `id_warga`, `jenis`, `jumlah`, `tanggal`, `metode`, `keterangan`) VALUES
(1, 1, 'setoran', 150000.00, '2025-06-26 20:08:17', 'tunai', 'Setoran awal'),
(2, 2, 'penarikan', 50000.00, '2025-06-26 20:08:17', 'transfer', 'Penarikan darurat'),
(3, 3, 'penarikan', 50000.00, '2025-06-29 23:23:30', 'tunai', 'test'),
(4, 3, 'penarikan', 50000.00, '2025-06-29 23:23:50', 'transfer', 'test | Transfer ke bank BCA, No. Rek: 9839823749823'),
(5, 2, 'setoran', 1000000.00, '2025-06-29 23:46:13', 'tunai', 'yo bro'),
(6, 2, 'penarikan', 300000.00, '2025-06-30 23:07:37', 'transfer', 'asdjasndjas | Transfer ke bank BCA, No. Rek: 4564564564'),
(7, 2, 'setoran', 1000000.00, '2025-06-30 23:51:34', 'tunai', 'nabung'),
(12, 2, 'setoran', 454543534.00, '2025-07-05 22:10:43', 'qris', 'Setoran tabungan pribadi via QRIS'),
(13, 2, 'setoran', 1212121.00, '2025-07-05 22:18:10', 'qris', 'Setoran tabungan pribadi via QRIS'),
(14, 2, 'setoran', 31231231.00, '2025-07-05 22:20:38', 'qris', 'Setoran tabungan pribadi via QRIS'),
(15, 2, 'penarikan', 4234234.00, '2025-07-05 22:34:18', 'tunai', 'Penarikan tabungan'),
(16, 2, 'penarikan', 4234234.00, '2025-07-05 22:34:39', 'transfer', 'Transfer ke bank BNI, No. Rek: 3424234234242'),
(17, 2, 'penarikan', 233333.00, '2025-07-11 22:17:32', 'tunai', 'Penarikan tabungan'),
(18, 2, 'setoran', 11.00, '2025-07-11 22:32:03', 'tunai', ''),
(19, 2, 'setoran', 11.00, '2025-07-11 23:10:05', 'tunai', 'Nabung dikit'),
(20, 2, 'penarikan', 11.00, '2025-07-11 23:10:13', 'tunai', ''),
(21, 2, 'setoran', 200000.00, '2025-07-11 23:53:26', 'qris', 'Setoran tabungan pribadi via QRIS'),
(22, 2, 'penarikan', 100000.00, '2025-07-11 23:54:39', 'tunai', 'Penarikan tabungan');

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi`
--

CREATE TABLE `transaksi` (
  `id` int(11) NOT NULL,
  `id_lingkungan` int(11) NOT NULL,
  `tipe` enum('pemasukan','pengeluaran') NOT NULL,
  `jenis` varchar(100) DEFAULT NULL,
  `jumlah` int(11) NOT NULL,
  `tanggal` datetime DEFAULT current_timestamp(),
  `sumber` varchar(100) DEFAULT NULL,
  `keterangan` text DEFAULT NULL,
  `id_dibuat_oleh` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `transaksi`
--

INSERT INTO `transaksi` (`id`, `id_lingkungan`, `tipe`, `jenis`, `jumlah`, `tanggal`, `sumber`, `keterangan`, `id_dibuat_oleh`) VALUES
(1, 1, 'pemasukan', 'Iuran Rutin', 500000, '2025-06-26 20:08:17', 'RT Warga', 'Iuran bulanan', 2),
(2, 2, 'pengeluaran', 'kebersihann', 200000, '2025-06-26 20:08:17', 'Kas RT', 'Bayar tukang sapu', 2),
(3, 2, 'pemasukan', 'iuran', 600000, '2025-06-29 22:13:19', 'pak slamet', 'Pendapatan: iuran dari pak slamet', 5),
(4, 1, 'pemasukan', 'DOnasi', 1000000, '2025-06-29 22:38:17', 'pak slamet', 'Pendapatan: DOnasi dari pak slamet', 7),
(5, 2, 'pengeluaran', 'Bayar kebersihan', 40000, '2025-06-29 22:57:39', 'iuran rt', 'Pengeluaran: Bayar kebersihan untuk iuran rt', 5),
(6, 2, 'pemasukan', 'Donasi', 1000000000, '2025-06-30 22:20:46', 'Pak haji yusuf jr', 'Bismillah', 5),
(7, 2, 'pengeluaran', 'Bayar kebersihan', 500000, '2025-06-30 22:43:47', 'Iuran rt', 'testing', 5),
(8, 2, 'pengeluaran', 'asdas', 121212, '2025-07-01 00:23:11', 'asdasd', 'asda', 5),
(9, 2, 'pemasukan', 'dasd', 1231123, '2025-07-01 00:24:33', 'afasd', 'svsdv', 5),
(10, 2, 'pengeluaran', 'testing', 1000000, '2025-07-01 00:30:53', 'iuran', 'pinjam bos', 5),
(11, 2, 'pengeluaran', 'Testing server', 1000000, '2025-07-01 00:33:00', 'iuran', 'testing', 5),
(12, 2, 'pengeluaran', 'Test ujisn', 100000, '2025-07-01 00:35:11', 'iuran', 'sdlkfnsljdf', 5),
(14, 2, 'pengeluaran', 'uang sampah', 30000, '2025-07-07 00:22:11', 'uang kas', 'oya', 9),
(15, 2, 'pemasukan', 'testing', 10000000, '2025-07-11 15:13:06', 'atmint', 'iya', 5);

-- --------------------------------------------------------

--
-- Struktur dari tabel `verifikasi_transaksi`
--

CREATE TABLE `verifikasi_transaksi` (
  `id` int(11) NOT NULL,
  `id_transaksi` int(11) NOT NULL,
  `status` enum('disetujui','ditolak','pending') NOT NULL DEFAULT 'pending',
  `alasan` text DEFAULT NULL,
  `waktu` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `verifikasi_transaksi`
--

INSERT INTO `verifikasi_transaksi` (`id`, `id_transaksi`, `status`, `alasan`, `waktu`) VALUES
(1, 2, 'disetujui', 'Dana tersedia', '2025-06-26 12:08:17'),
(2, 4, 'disetujui', 'Disetujui oleh bendahara', '2025-06-30 15:06:40'),
(3, 6, 'ditolak', 'gamau', '2025-06-30 15:08:00'),
(4, 10, 'ditolak', 'gamau', '2025-06-30 16:30:53'),
(5, 11, 'ditolak', 'ga', '2025-06-30 16:33:00'),
(6, 12, 'ditolak', 'ga', '2025-06-30 16:35:11'),
(7, 14, 'disetujui', 'oke bos', '2025-07-06 16:22:11');

-- --------------------------------------------------------

--
-- Struktur dari tabel `warga`
--

CREATE TABLE `warga` (
  `id` int(11) NOT NULL,
  `id_pengguna` int(11) DEFAULT NULL,
  `id_lingkungan` int(11) NOT NULL,
  `nama_warga` varchar(100) NOT NULL,
  `nik` varchar(20) NOT NULL,
  `no_hp` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `alamat` text DEFAULT NULL,
  `status` enum('aktif','nonaktif') DEFAULT 'aktif',
  `dibuat_pada` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `warga`
--

INSERT INTO `warga` (`id`, `id_pengguna`, `id_lingkungan`, `nama_warga`, `nik`, `no_hp`, `email`, `alamat`, `status`, `dibuat_pada`) VALUES
(1, 3, 1, 'Budi Santoso', '7371001001001001', '081234567890', 'budi@example.com', 'Jl. Dahlia No. 6', 'aktif', '2025-06-26 12:08:17'),
(2, 4, 2, 'Sari Dewi', '7371002002002002', '082345678901', 'sari@example.com', 'Jl. Melati No. 10', 'aktif', '2025-06-26 12:08:17'),
(3, 5, 2, 'Ambo', '123', '123456789', 'Ambo@gmail.com', '', 'aktif', '2025-06-26 14:17:32'),
(5, 7, 1, 'Aco', '1234', '4321', 'Aco@gmail.com', '', 'aktif', '2025-06-27 07:35:08'),
(6, 1, 1, 'Pak Slamet', '13982039283232', '08512345678', 'ketua01@example.com', 'testing', 'aktif', '2025-06-30 12:20:50'),
(8, 9, 2, 'Aji', '453453453453453453', '45345345345', 'Aji@gmail.com', '', 'aktif', '2025-07-01 15:43:57'),
(9, 10, 2, 'RI', '123456', '324234', 'Ri', '', 'aktif', '2025-07-01 15:49:36');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `master_key` (`master_key`);

--
-- Indeks untuk tabel `lingkungan`
--
ALTER TABLE `lingkungan`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `log_aktivitas`
--
ALTER TABLE `log_aktivitas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_pengguna` (`id_pengguna`);

--
-- Indeks untuk tabel `notifikasi`
--
ALTER TABLE `notifikasi`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `pengguna`
--
ALTER TABLE `pengguna`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `id_lingkungan` (`id_lingkungan`);

--
-- Indeks untuk tabel `saldo_tabungan`
--
ALTER TABLE `saldo_tabungan`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_saldo_warga` (`id_warga`);

--
-- Indeks untuk tabel `tabungan`
--
ALTER TABLE `tabungan`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_warga` (`id_warga`);

--
-- Indeks untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_lingkungan` (`id_lingkungan`),
  ADD KEY `id_dibuat_oleh` (`id_dibuat_oleh`);

--
-- Indeks untuk tabel `verifikasi_transaksi`
--
ALTER TABLE `verifikasi_transaksi`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_transaksi` (`id_transaksi`) USING BTREE;

--
-- Indeks untuk tabel `warga`
--
ALTER TABLE `warga`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nik` (`nik`),
  ADD KEY `id_lingkungan` (`id_lingkungan`),
  ADD KEY `id_pengguna` (`id_pengguna`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT untuk tabel `lingkungan`
--
ALTER TABLE `lingkungan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT untuk tabel `log_aktivitas`
--
ALTER TABLE `log_aktivitas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT untuk tabel `notifikasi`
--
ALTER TABLE `notifikasi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT untuk tabel `pengguna`
--
ALTER TABLE `pengguna`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT untuk tabel `saldo_tabungan`
--
ALTER TABLE `saldo_tabungan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT untuk tabel `tabungan`
--
ALTER TABLE `tabungan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT untuk tabel `verifikasi_transaksi`
--
ALTER TABLE `verifikasi_transaksi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT untuk tabel `warga`
--
ALTER TABLE `warga`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `log_aktivitas`
--
ALTER TABLE `log_aktivitas`
  ADD CONSTRAINT `log_aktivitas_ibfk_1` FOREIGN KEY (`id_pengguna`) REFERENCES `pengguna` (`id`) ON DELETE SET NULL;

--
-- Ketidakleluasaan untuk tabel `notifikasi`
--
ALTER TABLE `notifikasi`
  ADD CONSTRAINT `notifikasi_ibfk_1` FOREIGN KEY (`id_warga`) REFERENCES `pengguna` (`id`) ON DELETE CASCADE;

--
-- Ketidakleluasaan untuk tabel `pengguna`
--
ALTER TABLE `pengguna`
  ADD CONSTRAINT `pengguna_ibfk_1` FOREIGN KEY (`id_lingkungan`) REFERENCES `lingkungan` (`id`) ON DELETE SET NULL;

--
-- Ketidakleluasaan untuk tabel `saldo_tabungan`
--
ALTER TABLE `saldo_tabungan`
  ADD CONSTRAINT `fk_saldo_warga` FOREIGN KEY (`id_warga`) REFERENCES `warga` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `tabungan`
--
ALTER TABLE `tabungan`
  ADD CONSTRAINT `tabungan_ibfk_1` FOREIGN KEY (`id_warga`) REFERENCES `warga` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `transaksi_ibfk_1` FOREIGN KEY (`id_lingkungan`) REFERENCES `lingkungan` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transaksi_ibfk_2` FOREIGN KEY (`id_dibuat_oleh`) REFERENCES `pengguna` (`id`);

--
-- Ketidakleluasaan untuk tabel `verifikasi_transaksi`
--
ALTER TABLE `verifikasi_transaksi`
  ADD CONSTRAINT `verifikasi_transaksi_ibfk_3` FOREIGN KEY (`id_transaksi`) REFERENCES `transaksi` (`id`);

--
-- Ketidakleluasaan untuk tabel `warga`
--
ALTER TABLE `warga`
  ADD CONSTRAINT `warga_ibfk_1` FOREIGN KEY (`id_lingkungan`) REFERENCES `lingkungan` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `warga_ibfk_2` FOREIGN KEY (`id_pengguna`) REFERENCES `pengguna` (`id`) ON DELETE SET NULL;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
