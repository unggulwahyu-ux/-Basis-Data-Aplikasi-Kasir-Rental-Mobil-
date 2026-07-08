-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 08, 2026 at 11:20 AM
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
-- Database: `rental_mobil_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `mobil`
--

CREATE TABLE `mobil` (
  `no_plat` varchar(20) NOT NULL,
  `merk` varchar(50) NOT NULL,
  `jenis` varchar(50) NOT NULL,
  `tahun` varchar(4) NOT NULL,
  `harga_sewa` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `mobil`
--

INSERT INTO `mobil` (`no_plat`, `merk`, `jenis`, `tahun`, `harga_sewa`) VALUES
('AB 2024 OP', 'Mitsubishi Xpander Ultimate', 'MPV', '2022', 450000),
('B 1234 ABC', 'Toyota Avanza Veloz', 'MPV', '2022', 350000),
('B 5678 DEF', 'Honda Brio RS', 'City Car', '2021', 300000),
('B 9012 KLN', 'Mitsubishi Pajero Sport', 'SUV', '2022', 850000),
('D 1122 MBR', 'Hyundai Stargazer Prime', 'MPV', '2023', 450000),
('D 9911 XYZ', 'Toyota Innova Reborn', 'MPV', '2023', 600000),
('DK 7777 GG', 'Toyota Fortuner GR Sport', 'SUV', '2023', 850000),
('H 8000 CH', 'Suzuki Ertiga Hybrid', 'MPV', '2023', 400000),
('L 3344 SS', 'Honda HR-V Prestige', 'SUV', '2022', 550000),
('N 4567 AA', 'Daihatsu Sigra R', 'LCGC / MPV', '2020', 250000);

-- --------------------------------------------------------

--
-- Table structure for table `pelanggan`
--

CREATE TABLE `pelanggan` (
  `id_pelanggan` varchar(10) NOT NULL,
  `nama_pelanggan` varchar(50) NOT NULL,
  `no_ktp` varchar(20) NOT NULL,
  `no_hp` varchar(15) NOT NULL,
  `alamat` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pelanggan`
--

INSERT INTO `pelanggan` (`id_pelanggan`, `nama_pelanggan`, `no_ktp`, `no_hp`, `alamat`) VALUES
('PLG0001', 'Ahmad Subarjo', '3171012345670001', '081234567890', 'Jl. Merdeka No. 45, Jakarta Pusat'),
('PLG0002', 'Siti Aminah', '3273029876540003', '081398765432', 'Jl. Dago No. 102, Bandung'),
('PLG0003', 'Budi Santoso', '3578031122330002', '085611223344', 'Jl. Kaliurang KM 5, Yogyakarta'),
('PLG0004', 'Dewi Lestari', '5171044455660001', '087844556677', 'Jl. Sunset Road No. 88, Kuta, Bali'),
('PLG0005', 'Rian Hidayat', '6471055566770004', '082155566778', 'Jl. Sudirman No. 12, Balikpapan'),
('PLG0006', 'Diana Putri', '7371066677880002', '089666778899', 'Jl. AP Pettarani No. 34, Makassar'),
('PLG0007', 'Eko Prasetyo', '1271077788990001', '081977788990', 'Jl. Padang Bulan No. 5, Medan'),
('PLG0008', 'Fitri Handayani', '1671088899000003', '085288899001', 'Jl. Palembang Betung KM 14, Palembang'),
('PLG0009', 'Gilang Permana', '3515099900110005', '081199001122', 'Jl. Manyar Kertoarjo No. 21, Surabaya'),
('PLG0010', 'Mega Wijaya', '3275101122330001', '081310112233', 'Jl. KH Noer Ali No. 45, Bekasi');

-- --------------------------------------------------------

--
-- Table structure for table `sewa`
--

CREATE TABLE `sewa` (
  `id_sewa` varchar(20) NOT NULL,
  `id_pelanggan` varchar(20) DEFAULT NULL,
  `no_plat` varchar(20) DEFAULT NULL,
  `id_supir` varchar(20) DEFAULT NULL,
  `tgl_sewa` date DEFAULT NULL,
  `lama_sewa` int(11) DEFAULT NULL,
  `total_biaya` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sewa`
--

INSERT INTO `sewa` (`id_sewa`, `id_pelanggan`, `no_plat`, `id_supir`, `tgl_sewa`, `lama_sewa`, `total_biaya`) VALUES
('TRX0001', 'PLG0001', 'B 1234 ABC', 'SPR0001', '2026-06-20', 3, 1500000),
('TRX0002', 'PLG0002', 'B 5678 DEF', NULL, '2026-06-21', 2, 600000),
('TRX0003', 'PLG0003', 'D 9911 XYZ', 'SPR0002', '2026-06-22', 5, 3750000),
('TRX0004', 'PLG0004', 'AB 2024 OP', NULL, '2026-06-23', 1, 450000),
('TRX0005', 'PLG0005', 'L 3344 SS', 'SPR0003', '2026-06-24', 2, 1450000);

-- --------------------------------------------------------

--
-- Table structure for table `supir`
--

CREATE TABLE `supir` (
  `id_supir` varchar(20) NOT NULL,
  `nama_supir` varchar(100) DEFAULT NULL,
  `no_hp` varchar(20) DEFAULT NULL,
  `tarif_supir` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `supir`
--

INSERT INTO `supir` (`id_supir`, `nama_supir`, `no_hp`, `tarif_supir`) VALUES
('SPR0001', 'Joko Susanto', '081211112222', 150000),
('SPR0002', 'Bambang Pamungkas', '081322223333', 150000),
('SPR0003', 'Hendra Wijaya', '085633334444', 175000),
('SPR0004', 'Agus Setiawan', '087844445555', 150000),
('SPR0005', 'Mulyono', '082155556666', 200000),
('SPR0006', 'Dedi Kurniawan', '089666667777', 150000),
('SPR0007', 'Slamet Riyadi', '081977778888', 175000),
('SPR0008', 'Andi Wijaya', '085288889999', 150000),
('SPR0009', 'Budiman', '081199990000', 200000),
('SPR0010', 'Roni Paslah', '081310102020', 150000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `mobil`
--
ALTER TABLE `mobil`
  ADD PRIMARY KEY (`no_plat`);

--
-- Indexes for table `sewa`
--
ALTER TABLE `sewa`
  ADD PRIMARY KEY (`id_sewa`),
  ADD KEY `id_pelanggan` (`id_pelanggan`),
  ADD KEY `id_pelanggan_2` (`id_pelanggan`),
  ADD KEY `id_pelanggan_3` (`id_pelanggan`),
  ADD KEY `no_plat` (`no_plat`),
  ADD KEY `id_supir` (`id_supir`);

--
-- Indexes for table `supir`
--
ALTER TABLE `supir`
  ADD PRIMARY KEY (`id_supir`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
