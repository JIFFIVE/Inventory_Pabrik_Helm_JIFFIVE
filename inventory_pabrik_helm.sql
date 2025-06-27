-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 23, 2025 at 09:10 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `inventory_pabrik_helm`
--

-- --------------------------------------------------------

--
-- Table structure for table `barang_keluar`
--

CREATE TABLE `barang_keluar` (
  `kode_transaksi` varchar(10) NOT NULL,
  `tanggal` varchar(10) DEFAULT NULL,
  `kode_barang` varchar(10) DEFAULT NULL,
  `nama_barang` varchar(50) DEFAULT NULL,
  `kategori` varchar(30) DEFAULT NULL,
  `stok` int(5) DEFAULT NULL,
  `harga` decimal(12,2) DEFAULT NULL,
  `total_keluar` int(5) DEFAULT NULL,
  `satuan` varchar(20) DEFAULT NULL,
  `total_harga` decimal(12,2) DEFAULT NULL,
  `total_stok` int(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `barang_keluar`
--

INSERT INTO `barang_keluar` (`kode_transaksi`, `tanggal`, `kode_barang`, `nama_barang`, `kategori`, `stok`, `harga`, `total_keluar`, `satuan`, `total_harga`, `total_stok`) VALUES
('KLR001', '06-24-2025', '001', 'Padel', 'Cat', 5, 15000.00, 3, 'Box', 45000.00, 2),
('KLR002', '06-24-2025', '002', 'Pikri', 'Batok', 10, 10000.00, 5, 'pcs', 50000.00, 5),
('KLR003', '06-24-2025', '003', 'Petir', 'Baut', 15, 5000.00, 10, 'pcs', 50000.00, 5);

-- --------------------------------------------------------

--
-- Table structure for table `barang_masuk`
--

CREATE TABLE `barang_masuk` (
  `kode_transaksi` varchar(10) NOT NULL,
  `tanggal` varchar(10) DEFAULT NULL,
  `nama_supplier` varchar(50) DEFAULT NULL,
  `kode_barang` varchar(10) DEFAULT NULL,
  `nama_barang` varchar(50) DEFAULT NULL,
  `kategori` varchar(30) DEFAULT NULL,
  `stok` int(5) DEFAULT NULL,
  `harga` decimal(12,2) DEFAULT NULL,
  `total_masuk` int(5) DEFAULT NULL,
  `satuan` varchar(20) DEFAULT NULL,
  `total_harga` decimal(12,2) DEFAULT NULL,
  `total_stok` int(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `barang_masuk`
--

INSERT INTO `barang_masuk` (`kode_transaksi`, `tanggal`, `nama_supplier`, `kode_barang`, `nama_barang`, `kategori`, `stok`, `harga`, `total_masuk`, `satuan`, `total_harga`, `total_stok`) VALUES
('MSK001', '06-23-2025', 'Naira', '001', 'Padel', 'Cat', 0, 15000.00, 5, 'Box', 75000.00, 5),
('MSK002', '06-23-2025', 'Naira', '002', 'Pikri', 'Batok', 0, 10000.00, 10, 'pcs', 100000.00, 10),
('MSK003', '06-23-2025', 'Naira', '003', 'Petir', 'Baut', 0, 5000.00, 15, 'pcs', 75000.00, 15);

-- --------------------------------------------------------

--
-- Table structure for table `data_barang`
--

CREATE TABLE `data_barang` (
  `kode_barang` varchar(10) NOT NULL,
  `nama_barang` varchar(50) DEFAULT NULL,
  `kategori` varchar(30) DEFAULT NULL,
  `satuan` varchar(20) DEFAULT NULL,
  `stok` int(11) DEFAULT 0,
  `harga` decimal(12,2) DEFAULT NULL,
  `nama_supplier` varchar(50) DEFAULT NULL,
  `deskripsi` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `data_barang`
--

INSERT INTO `data_barang` (`kode_barang`, `nama_barang`, `kategori`, `satuan`, `stok`, `harga`, `nama_supplier`, `deskripsi`) VALUES
('001', 'Padel', 'Cat', 'Box', 2, 15000.00, 'Naira', 'Cat Glitter Asoy'),
('002', 'Pikri', 'Batok', 'pcs', 5, 10000.00, 'Naira', 'Batok Arai'),
('003', 'Petir', 'Baut', 'pcs', 5, 5000.00, 'Naira', 'Baut neraka'),
('004', 'Irma', 'Perekat', 'Box', 0, 20000.00, 'Janu', 'Perekat ampuh super lengket');

-- --------------------------------------------------------

--
-- Table structure for table `data_supplier`
--

CREATE TABLE `data_supplier` (
  `kode_supplier` varchar(10) NOT NULL,
  `nama_supplier` varchar(50) DEFAULT NULL,
  `alamat` varchar(100) DEFAULT NULL,
  `telepon` varchar(15) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `data_supplier`
--

INSERT INTO `data_supplier` (`kode_supplier`, `nama_supplier`, `alamat`, `telepon`, `email`) VALUES
('S001', 'Janu', 'Tangerang Sebelah CGK', '123578', 'janu@gmail.com'),
('S002', 'Naira', 'Kebayoran', '081514151620', 'naira@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(3000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `barang_keluar`
--
ALTER TABLE `barang_keluar`
  ADD PRIMARY KEY (`kode_transaksi`);

--
-- Indexes for table `barang_masuk`
--
ALTER TABLE `barang_masuk`
  ADD PRIMARY KEY (`kode_transaksi`);

--
-- Indexes for table `data_barang`
--
ALTER TABLE `data_barang`
  ADD PRIMARY KEY (`kode_barang`);

--
-- Indexes for table `data_supplier`
--
ALTER TABLE `data_supplier`
  ADD PRIMARY KEY (`kode_supplier`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
