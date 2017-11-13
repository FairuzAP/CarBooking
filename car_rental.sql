-- --------------------------------------------------------
-- Host:                         localhost
-- Versi server:                 10.1.21-MariaDB - mariadb.org binary distribution
-- OS Server:                    Win32
-- HeidiSQL Versi:               9.2.0.4947
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for car_rental
CREATE DATABASE IF NOT EXISTS `car_rental` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `car_rental`;


-- Dumping structure for table car_rental.kota
CREATE TABLE IF NOT EXISTS `kota` (
  `id_kota` int(11) NOT NULL AUTO_INCREMENT,
  `kota` text,
  PRIMARY KEY (`id_kota`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table car_rental.kota: ~2 rows (approximately)
DELETE FROM `kota`;
/*!40000 ALTER TABLE `kota` DISABLE KEYS */;
INSERT INTO `kota` (`id_kota`, `kota`) VALUES
	(1, 'Bandung'),
	(2, 'Jakarta');
/*!40000 ALTER TABLE `kota` ENABLE KEYS */;


-- Dumping structure for table car_rental.mobil
CREATE TABLE IF NOT EXISTS `mobil` (
  `id_mobil` int(11) NOT NULL AUTO_INCREMENT,
  `merk` text,
  `jenis` text,
  `km` int(11) DEFAULT NULL,
  `biaya_per_hari` text,
  `deskripsi` text,
  `tahun_produksi` text,
  `status` int(11) DEFAULT NULL,
  `id_kota` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_mobil`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Dumping data for table car_rental.mobil: ~0 rows (approximately)
DELETE FROM `mobil`;
/*!40000 ALTER TABLE `mobil` DISABLE KEYS */;
INSERT INTO `mobil` (`id_mobil`, `merk`, `jenis`, `km`, `biaya_per_hari`, `deskripsi`, `tahun_produksi`, `status`, `id_kota`) VALUES
	(1, 'Avanza', 'Sedan', 134, '50000', 'Avanza S Putih', '2016', 0, 1);
/*!40000 ALTER TABLE `mobil` ENABLE KEYS */;


-- Dumping structure for table car_rental.status_mobil
CREATE TABLE IF NOT EXISTS `status_mobil` (
  `id` int(11) DEFAULT NULL,
  `status` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table car_rental.status_mobil: ~3 rows (approximately)
DELETE FROM `status_mobil`;
/*!40000 ALTER TABLE `status_mobil` DISABLE KEYS */;
INSERT INTO `status_mobil` (`id`, `status`) VALUES
	(0, 'Nonaktif'),
	(1, 'Siap Dipinjam'),
	(2, 'Sedang Dipinjam');
/*!40000 ALTER TABLE `status_mobil` ENABLE KEYS */;


-- Dumping structure for table car_rental.status_transaksi
CREATE TABLE IF NOT EXISTS `status_transaksi` (
  `id` int(11) DEFAULT NULL,
  `status` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table car_rental.status_transaksi: ~2 rows (approximately)
DELETE FROM `status_transaksi`;
/*!40000 ALTER TABLE `status_transaksi` DISABLE KEYS */;
INSERT INTO `status_transaksi` (`id`, `status`) VALUES
	(0, 'Cancelled'),
	(1, 'OnGoing'),
	(2, 'Checked');
/*!40000 ALTER TABLE `status_transaksi` ENABLE KEYS */;


-- Dumping structure for table car_rental.transaksi
CREATE TABLE IF NOT EXISTS `transaksi` (
  `id_transaksi` int(11) NOT NULL AUTO_INCREMENT,
  `biaya_total` text,
  `status` int(11) DEFAULT NULL,
  `id_mobil` int(11) DEFAULT NULL,
  `id_kota` int(11) DEFAULT NULL,
  `hp_peminjam` text,
  `email_peminjam` text,
  `nama_peminjam` text,
  `waktu_mulai` date DEFAULT NULL,
  `waktu_selesai` date DEFAULT NULL,
  PRIMARY KEY (`id_transaksi`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table car_rental.transaksi: ~0 rows (approximately)
DELETE FROM `transaksi`;
/*!40000 ALTER TABLE `transaksi` DISABLE KEYS */;
/*!40000 ALTER TABLE `transaksi` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
