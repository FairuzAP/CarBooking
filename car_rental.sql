/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 100116
Source Host           : localhost:3306
Source Database       : car_rental

Target Server Type    : MYSQL
Target Server Version : 100116
File Encoding         : 65001

Date: 2017-11-15 07:01:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for kota
-- ----------------------------
DROP TABLE IF EXISTS `kota`;
CREATE TABLE `kota` (
  `id_kota` int(11) NOT NULL AUTO_INCREMENT,
  `kota` text,
  PRIMARY KEY (`id_kota`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of kota
-- ----------------------------
INSERT INTO `kota` VALUES ('1', 'Bandung');
INSERT INTO `kota` VALUES ('2', 'Jakarta');

-- ----------------------------
-- Table structure for mobil
-- ----------------------------
DROP TABLE IF EXISTS `mobil`;
CREATE TABLE `mobil` (
  `id_mobil` int(11) NOT NULL AUTO_INCREMENT,
  `merk` text,
  `jenis` text,
  `km` int(11) DEFAULT NULL,
  `biaya_per_hari` text,
  `deskripsi` text,
  `tahun_produksi` text,
  `status` int(11) DEFAULT NULL,
  `id_kota` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_mobil`),
  KEY `Kota Asal` (`id_kota`),
  CONSTRAINT `Kota Asal` FOREIGN KEY (`id_kota`) REFERENCES `kota` (`id_kota`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of mobil
-- ----------------------------
INSERT INTO `mobil` VALUES ('1', 'Avanza', 'Sedan', '134', '50000', 'Avanza S Putih', '2016', '1', '1');

-- ----------------------------
-- Table structure for status_mobil
-- ----------------------------
DROP TABLE IF EXISTS `status_mobil`;
CREATE TABLE `status_mobil` (
  `id` int(11) DEFAULT NULL,
  `status` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of status_mobil
-- ----------------------------
INSERT INTO `status_mobil` VALUES ('0', 'Nonaktif');
INSERT INTO `status_mobil` VALUES ('1', 'Siap Dipinjam');
INSERT INTO `status_mobil` VALUES ('2', 'Sedang Dipinjam');

-- ----------------------------
-- Table structure for status_transaksi
-- ----------------------------
DROP TABLE IF EXISTS `status_transaksi`;
CREATE TABLE `status_transaksi` (
  `id` int(11) DEFAULT NULL,
  `status` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of status_transaksi
-- ----------------------------
INSERT INTO `status_transaksi` VALUES ('0', 'Submitted');
INSERT INTO `status_transaksi` VALUES ('1', 'Paid');
INSERT INTO `status_transaksi` VALUES ('2', 'Ongoing');
INSERT INTO `status_transaksi` VALUES ('3', 'Closed');
INSERT INTO `status_transaksi` VALUES ('4', 'Canceled');

-- ----------------------------
-- Table structure for transaksi
-- ----------------------------
DROP TABLE IF EXISTS `transaksi`;
CREATE TABLE `transaksi` (
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of transaksi
-- ----------------------------
INSERT INTO `transaksi` VALUES ('1', '10000', '4', '1', '1', '01234567', 'a@a.a.com', 'aaa', '2017-11-17', '2017-11-24');
SET FOREIGN_KEY_CHECKS=1;
