/*
SQLyog Community v12.5.0 (64 bit)
MySQL - 10.1.31-MariaDB : Database - battleship
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`battleship` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `battleship`;

/*Table structure for table `match` */

DROP TABLE IF EXISTS `match`;

CREATE TABLE `match` (
  `matchId` int(11) NOT NULL,
  `winnerId` int(11) NOT NULL,
  `looserId` int(11) DEFAULT NULL,
  PRIMARY KEY (`matchId`),
  KEY `winnerId` (`winnerId`),
  KEY `looserId` (`looserId`),
  CONSTRAINT `match_ibfk_1` FOREIGN KEY (`winnerId`) REFERENCES `user` (`userId`),
  CONSTRAINT `match_ibfk_2` FOREIGN KEY (`looserId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `userId` int(11) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
