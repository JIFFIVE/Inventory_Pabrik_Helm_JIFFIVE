-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 30, 2025 at 08:22 AM
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
('KLR001', '01/07/2025', 'AKS001', 'Sticker SNI Label', 'Aksesoris', 600, 2500.00, 50, 'Lembar', 125000.00, 550),
('KLR002', '02/07/2025', 'CAT001', 'Cat Primer Plastik', 'Bahan Kimia', 40, 75000.00, 3, 'Liter', 225000.00, 37),
('KLR003', '03/07/2025', 'KMP001', 'Visor Clear Polycarbonate', 'Komponen', 450, 35000.00, 30, 'Pcs', 1050000.00, 420),
('KLR004', '04/07/2025', 'BBU002', 'EPS Foam Liner', 'Bahan Baku', 150, 15000.00, 5, 'Pcs', 75000.00, 145);

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
('MSK001', '01/06/2025', 'CV Printing Safety', 'AKS001', 'Sticker SNI Label', 'Aksesoris', 0, 2500.00, 500, 'Lembar', 1250000.00, 500),
('MSK002', '02/06/2025', 'CV Foam Indonesia', 'BBU002', 'EPS Foam Liner', 'Bahan Baku', 0, 15000.00, 150, 'Pcs', 2250000.00, 150),
('MSK003', '03/06/2025', 'CV Printing Safety', 'AKS001', 'Sticker SNI Label', 'Aksesoris', 500, 2500.00, 100, 'Lembar', 250000.00, 600),
('MSK005', '05/06/2025', 'PT Optik Safety', 'KMP001', 'Visor Clear Polycarbonate', 'Komponen', 0, 35000.00, 200, 'Pcs', 7000000.00, 200),
('MSK006', '07/06/2025', 'PT Chemical Coat', 'CAT001', 'Cat Primer Plastik', 'Bahan Kimia', 0, 75000.00, 20, 'Liter', 1500000.00, 20),
('MSK007', '06/06/2025', 'PT Airflow Tech', 'KMP003', 'Ventilasi Air Vent', 'Komponen', 0, 12000.00, 120, 'Pcs', 1440000.00, 120),
('MSK008', '08/06/2025', 'CV Pneumatic Indo', 'SPR002', 'Neumatic Cylinder 50mm', 'Spare Part', 0, 450000.00, 20, 'Pcs', 9000000.00, 20);

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
('AKS001', 'Sticker SNI Label', 'Aksesoris', 'Lembar', 550, 2500.00, 'CV Printing Safety', 'Label sertifikasi SNI untuk helm keselamatan'),
('BBU001', 'Polycarbonate Sheet 3mm', 'Bahan Baku', 'Lembar', 0, 85000.00, 'PT Plastik Prima', 'Lembaran polycarbonate untuk cangkang helm utama, tahan benturan tinggi'),
('BBU002', 'EPS Foam Liner', 'Bahan Baku', 'Pcs', 143, 15000.00, 'CV Foam Indonesia', 'Bantalan busa EPS untuk interior helm, densitas 60 kg/m³'),
('BBU003', 'Strap Tali Dagu Nylon', 'Bahan Baku', 'Meter', 0, 8500.00, 'PT Textile Safety', 'Tali dagu berkualitas tinggi dengan buckle plastik, lebar 25mm'),
('CAT001', 'Cat Primer Plastik', 'Bahan Kimia', 'Liter', 31, 75000.00, 'PT Chemical Coat', 'Cat dasar khusus plastik untuk aplikasi helm safety'),
('CAT002', 'Cat Reflektif Silver', 'Bahan Kimia', 'Liter', 0, 125000.00, 'PT Reflective Paint', 'Cat reflektif untuk marking keselamatan pada helm'),
('KMP001', 'Visor Clear Polycarbonate', 'Komponen', 'Pcs', 387, 35000.00, 'PT Optik Safety', 'Visor bening anti-UV untuk helm safety, ketebalan 2mm'),
('KMP002', 'Ratchet Adjustment System', 'Komponen', 'Set', 0, 25000.00, 'CV Mekanik Jaya', 'Sistem penyesuaian ukuran helm dengan ratchet knob'),
('KMP003', 'Ventilasi Air Vent', 'Komponen', 'Pcs', 220, 12000.00, 'PT Airflow Tech', 'Ventilasi udara untuk helm dengan filter debu'),
('PKG001', 'Plastik Wrapping PE', 'Packaging', 'Roll', 0, 45000.00, 'PT Kemasan Industri', 'Plastik pembungkus helm individual, ukuran 30x40cm'),
('SPR001', 'Heating Element 2kW', 'Spare Part', 'Pcs', 11, 350000.00, 'PT Electric Component', 'Elemen pemanas untuk mesin thermoforming helm'),
('SPR002', 'Neumatic Cylinder 50mm', 'Spare Part', 'Pcs', 55, 450000.00, 'CV Pneumatic Indo', 'Silinder pneumatik untuk sistem press molding');

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
('SUP001', 'PT Plastik Prima', 'Jl. Industri Raya No. 45, Kawasan Industri Pulogadung, Jakarta Timur 13260', '021-5551234', 'sales@plastikprima.co.id'),
('SUP002', 'CV Foam Indonesia', 'Jl. Raya Driyorejo Km 8, Gresik, Jawa Timur 61177', '031-7891234', 'info@foamindonesia.com'),
('SUP003', 'PT Textile Safety', 'Jl. Soekarno Hatta No. 689, Bandung, Jawa Barat 40286', '022-6041567', 'order@textilesafety.co.id'),
('SUP004', 'PT Optik Safety', 'Jl. Ahmad Yani No. 123, Semarang, Jawa Tengah 50241', '024-8765432', 'marketing@optiksafety.com'),
('SUP005', 'CV Mekanik Jaya', 'Jl. Veteran No. 78, Malang, Jawa Timur 65145', '0341-555789', 'sales@mekanikjaya.co.id'),
('SUP006', 'PT Airflow Tech', 'Jl. TB Simatupang Kav. 15, Jakarta Selatan 12560', '021-4567891', 'contact@airflowtech.id'),
('SUP007', 'PT Chemical Coat', 'Jl. Rungkut Industri III No. 20, Surabaya, Jawa Timur 60293', '031-3456789', 'support@chemicalcoat.com'),
('SUP008', 'PT Reflective Paint', 'Jl. Gatot Subroto Km 7, Tangerang, Banten 15111', '021-8901234', 'info@reflectivepaint.co.id'),
('SUP009', 'CV Printing Safety', 'Jl. Solo Km 12, Yogyakarta 55184', '0274-567890', 'order@printingsafety.com'),
('SUP010', 'PT Kemasan Industri', 'Jl. Raya Bekasi Km 18, Bekasi, Jawa Barat 17530', '021-7654321', 'sales@kemasanindustri.co.id'),
('SUP011', 'PT Precision Mold', 'Jl. Modern Land Blok C No. 12, Tangerang, Banten 15117', '021-2345678', 'engineering@precisionmold.com'),
('SUP012', 'PT Electric Component', 'Jl. Margomulyo Indah Blok B-15, Surabaya, Jawa Timur 60183', '031-2468135', 'technical@electriccomponent.id'),
('SUP013', 'CV Pneumatic Indo', 'Jl. Leuwi Panjang No. 45, Bandung, Jawa Barat 40164', '022-7531246', 'info@pneumaticindo.com');

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
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`) VALUES
(2, 'JIFFIVE', '767a290a1a4dd6205765f19ace7e79d0027743781520a729d816a47d62442557');

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
