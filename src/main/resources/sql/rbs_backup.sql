-- MySQL dump 10.13  Distrib 5.7.11, for osx10.9 (x86_64)
--
-- Host: localhost    Database: rbs
-- ------------------------------------------------------
-- Server version	5.7.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Admin`
--

DROP TABLE IF EXISTS `Admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Admin` (
  `id` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_Admin_id_Faculty_id` FOREIGN KEY (`id`) REFERENCES `Faculty` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Admin`
--

LOCK TABLES `Admin` WRITE;
/*!40000 ALTER TABLE `Admin` DISABLE KEYS */;
INSERT INTO `Admin` VALUES ('10001');
/*!40000 ALTER TABLE `Admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Class`
--

DROP TABLE IF EXISTS `Class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Class` (
  `name` varchar(128) NOT NULL,
  `facultyId` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`name`),
  KEY `index_facultyId` (`facultyId`),
  CONSTRAINT `FK_Class_facultyId_Faculty_id` FOREIGN KEY (`facultyId`) REFERENCES `Faculty` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Class`
--

LOCK TABLES `Class` WRITE;
/*!40000 ALTER TABLE `Class` DISABLE KEYS */;
INSERT INTO `Class` VALUES ('软工121(Java)','10002'),('软工122(Java)','10002');
/*!40000 ALTER TABLE `Class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Faculty`
--

DROP TABLE IF EXISTS `Faculty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Faculty` (
  `id` varchar(20) NOT NULL COMMENT 'Faculty id.',
  `id_digest` char(40) NOT NULL COMMENT 'The sha1 digest to the id.',
  `password` char(40) NOT NULL,
  `name` varchar(50) NOT NULL,
  `designation` varchar(50) DEFAULT NULL,
  `gender` tinyint(1) DEFAULT NULL,
  `office` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_id` (`id`),
  KEY `index_id_digest` (`id_digest`),
  KEY `index_name` (`name`),
  KEY `index_office` (`office`),
  KEY `index_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Faculty`
--

LOCK TABLES `Faculty` WRITE;
/*!40000 ALTER TABLE `Faculty` DISABLE KEYS */;
INSERT INTO `Faculty` VALUES ('10001','6c447a8fe7677ddc4c4cd2efddcfe650e4e6c706','0771235b7952413b4713e65e22e22137ec8cbeef','admin','教师管理员',0,'软件楼209','15556999725'),('10002','6918d3fd8cd96f921bc242f76538d2d6f8078380','34532aede8df428a70c86762ab0635cc0d525b5f','刘晓强','教导主任',1,'软件楼210','15556999725'),('10003','b27b41feecc0a5a45ad1bfa42765474174d5e09e','305c803496ccbc9e5e4d541d69ae335fe5ead12d','黎红','辅导员',0,'软件楼108','15556999725'),('10004','75186a2e1a40cc782d90af3fdd3eb8efbe305478','078998006bb2cfd57efd4d76a7486a06f78956b9','黄安','学生会书记',1,'软件楼315','15556999725');
/*!40000 ALTER TABLE `Faculty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FavoriteRoom`
--

DROP TABLE IF EXISTS `FavoriteRoom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FavoriteRoom` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roomBuilding` varchar(20) DEFAULT NULL,
  `roomNumber` varchar(20) DEFAULT NULL,
  `personType` varchar(20) DEFAULT NULL COMMENT 'The person  type could be faculty or student.',
  `personId` varchar(20) DEFAULT NULL COMMENT 'The person Id could be faculty Id or student Id according to the situation.',
  PRIMARY KEY (`id`),
  KEY `index_personid` (`personId`),
  KEY `index_building_number` (`roomBuilding`,`roomNumber`),
  CONSTRAINT `FK_FavoriteRoom_personId_Faculty_id` FOREIGN KEY (`personId`) REFERENCES `Faculty` (`id`),
  CONSTRAINT `FK_FavoriteRoom_personId_Student_id` FOREIGN KEY (`personId`) REFERENCES `Student` (`id`),
  CONSTRAINT `FK_FavoriteRoom_roomBuilding_roomNumber_Room_building_number` FOREIGN KEY (`roomBuilding`, `roomNumber`) REFERENCES `Room` (`building`, `number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FavoriteRoom`
--

LOCK TABLES `FavoriteRoom` WRITE;
/*!40000 ALTER TABLE `FavoriteRoom` DISABLE KEYS */;
/*!40000 ALTER TABLE `FavoriteRoom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Room`
--

DROP TABLE IF EXISTS `Room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Room` (
  `building` varchar(20) NOT NULL,
  `number` varchar(20) NOT NULL,
  `capacity` smallint(6) DEFAULT NULL,
  `hasMultiMedia` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`building`,`number`),
  KEY `index_capacity` (`capacity`),
  KEY `index_hasMultiMedia` (`hasMultiMedia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Room`
--

LOCK TABLES `Room` WRITE;
/*!40000 ALTER TABLE `Room` DISABLE KEYS */;
INSERT INTO `Room` VALUES ('图书馆','101',50,0),('图书馆','102',42,1),('图书馆','103',36,1),('图书馆','104',36,0),('图书馆','105',40,0),('图书馆','106',38,0),('图书馆','107',34,1),('图书馆','108',48,1),('图书馆','109',42,0),('图书馆','110',50,0),('图书馆','111',42,1),('图书馆','112',48,0),('图书馆','113',46,0),('图书馆','114',36,0),('图书馆','115',32,1),('图书馆','116',50,0),('图书馆','201',40,0),('图书馆','202',24,1),('图书馆','203',36,1),('图书馆','204',32,0),('图书馆','205',42,0),('图书馆','206',42,1),('图书馆','207',34,1),('图书馆','208',38,0),('图书馆','209',36,1),('图书馆','210',32,1),('图书馆','211',42,1),('图书馆','212',26,1),('图书馆','213',50,1),('图书馆','214',36,0),('图书馆','215',22,1),('图书馆','216',38,1),('图书馆','301',32,1),('图书馆','302',50,1),('图书馆','303',46,1),('图书馆','304',32,0),('图书馆','305',38,0),('图书馆','306',48,1),('图书馆','307',40,0),('图书馆','308',32,0),('图书馆','309',24,1),('图书馆','310',48,0),('图书馆','311',48,0),('图书馆','312',38,0),('图书馆','313',24,1),('图书馆','314',48,1),('图书馆','315',46,0),('图书馆','316',26,0),('图书馆','401',42,1),('图书馆','402',28,0),('图书馆','403',24,0),('图书馆','404',32,0),('图书馆','405',26,0),('图书馆','406',42,1),('图书馆','407',46,1),('图书馆','408',38,1),('图书馆','409',50,0),('图书馆','410',34,1),('图书馆','411',48,0),('图书馆','412',34,1),('图书馆','413',28,0),('图书馆','414',28,0),('图书馆','415',44,1),('图书馆','416',50,0),('软件楼','101',50,0),('软件楼','102',48,1),('软件楼','103',36,0),('软件楼','104',24,1),('软件楼','105',22,1),('软件楼','106',50,0),('软件楼','107',34,0),('软件楼','108',36,1),('软件楼','109',38,1),('软件楼','110',46,1),('软件楼','111',42,0),('软件楼','112',36,1),('软件楼','113',46,1),('软件楼','114',24,0),('软件楼','115',42,0),('软件楼','116',50,1),('软件楼','117',36,0),('软件楼','118',32,0),('软件楼','119',46,0),('软件楼','120',26,0),('软件楼','121',32,1),('软件楼','122',44,0),('软件楼','123',28,0),('软件楼','124',42,0),('软件楼','125',50,0),('软件楼','126',24,1),('软件楼','201',30,0),('软件楼','202',30,1),('软件楼','203',22,0),('软件楼','204',42,1),('软件楼','205',48,0),('软件楼','206',34,1),('软件楼','207',36,0),('软件楼','208',28,1),('软件楼','209',24,1),('软件楼','210',22,1),('软件楼','211',22,0),('软件楼','212',30,0),('软件楼','213',40,1),('软件楼','214',44,1),('软件楼','215',24,1),('软件楼','216',44,1),('软件楼','217',34,0),('软件楼','218',28,1),('软件楼','219',50,1),('软件楼','220',46,0),('软件楼','221',40,1),('软件楼','222',46,1),('软件楼','223',36,1),('软件楼','224',50,1),('软件楼','225',38,1),('软件楼','226',34,1),('软件楼','301',30,1),('软件楼','302',38,1),('软件楼','303',34,0),('软件楼','304',44,0),('软件楼','305',24,0),('软件楼','306',24,0),('软件楼','307',46,0),('软件楼','308',26,1),('软件楼','309',26,0),('软件楼','310',34,1),('软件楼','311',28,1),('软件楼','312',50,1),('软件楼','313',22,0),('软件楼','314',22,1),('软件楼','315',22,1),('软件楼','316',48,1),('软件楼','317',46,1),('软件楼','318',24,1),('软件楼','319',38,1),('软件楼','320',44,0),('软件楼','321',24,0),('软件楼','322',46,1),('软件楼','323',28,1),('软件楼','324',28,1),('软件楼','325',26,0),('软件楼','326',38,0),('软件楼','401',50,0),('软件楼','402',46,0),('软件楼','403',46,0),('软件楼','404',46,1),('软件楼','405',48,0),('软件楼','406',34,0),('软件楼','407',22,1),('软件楼','408',26,1),('软件楼','409',26,0),('软件楼','410',46,0),('软件楼','411',48,1),('软件楼','412',34,1),('软件楼','413',46,1),('软件楼','414',46,1),('软件楼','415',24,0),('软件楼','416',32,1),('软件楼','417',34,1),('软件楼','418',44,0),('软件楼','419',40,0),('软件楼','420',44,1),('软件楼','421',38,1),('软件楼','422',20,1),('软件楼','423',36,1),('软件楼','424',48,0),('软件楼','425',30,1),('软件楼','426',26,1),('软件楼','501',40,0),('软件楼','502',46,1),('软件楼','503',44,0),('软件楼','504',48,0),('软件楼','505',32,0),('软件楼','506',26,0),('软件楼','507',46,1),('软件楼','508',20,0),('软件楼','509',50,1),('软件楼','510',44,1),('软件楼','511',28,0),('软件楼','512',24,1),('软件楼','513',30,0),('软件楼','514',44,0),('软件楼','515',50,1),('软件楼','516',42,1),('软件楼','517',48,1),('软件楼','518',48,0),('软件楼','519',44,1),('软件楼','520',50,1),('软件楼','521',26,1),('软件楼','522',44,1),('软件楼','523',26,0),('软件楼','524',50,0),('软件楼','525',32,1),('软件楼','526',36,1);
/*!40000 ALTER TABLE `Room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RoomBooking`
--

DROP TABLE IF EXISTS `RoomBooking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RoomBooking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `groupId` char(36) NOT NULL COMMENT 'If different records have same group id, they belong to same group.',
  `roomBuilding` varchar(20) NOT NULL,
  `roomNumber` varchar(20) NOT NULL,
  `applicantType` varchar(20) NOT NULL COMMENT 'The applicant type could be faculty or student.',
  `applicantId` varchar(20) NOT NULL COMMENT 'The applicant Id could be faculty Id or student Id according to the situation.',
  `fromTime` datetime NOT NULL,
  `toTime` datetime NOT NULL,
  `bookReason` varchar(1024) DEFAULT NULL,
  `progress` smallint(6) DEFAULT NULL,
  `adminId` varchar(20) DEFAULT NULL,
  `facultyId` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_adminId` (`adminId`),
  KEY `index_applicantId` (`applicantId`),
  KEY `index_facultyId` (`facultyId`),
  KEY `index_building_number` (`roomBuilding`,`roomNumber`),
  KEY `index_fromTime` (`fromTime`),
  KEY `index_toTime` (`toTime`),
  KEY `index_progress` (`progress`),
  CONSTRAINT `FK_RoomBooking_adminId_Admin_id` FOREIGN KEY (`adminId`) REFERENCES `Admin` (`id`),
  CONSTRAINT `FK_RoomBooking_applicantId_Faculty_id` FOREIGN KEY (`applicantId`) REFERENCES `Faculty` (`id`),
  CONSTRAINT `FK_RoomBooking_applicantId_Student_id` FOREIGN KEY (`applicantId`) REFERENCES `Student` (`id`),
  CONSTRAINT `FK_RoomBooking_faculty_Faculty_id` FOREIGN KEY (`facultyId`) REFERENCES `Faculty` (`id`),
  CONSTRAINT `FK_RoomBooking_roomBuilding_roomNumber_Room_building_number` FOREIGN KEY (`roomBuilding`, `roomNumber`) REFERENCES `Room` (`building`, `number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RoomBooking`
--

LOCK TABLES `RoomBooking` WRITE;
/*!40000 ALTER TABLE `RoomBooking` DISABLE KEYS */;
/*!40000 ALTER TABLE `RoomBooking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Student`
--

DROP TABLE IF EXISTS `Student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Student` (
  `id` varchar(20) NOT NULL COMMENT 'Student id.',
  `id_digest` char(40) NOT NULL COMMENT 'The sha1 digest to the id.',
  `password` char(40) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `className` varchar(128) DEFAULT NULL,
  `gender` tinyint(1) DEFAULT NULL,
  `dormRoomNumber` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_id` (`id`),
  KEY `index_classname` (`className`),
  KEY `index_id_digest` (`id_digest`),
  CONSTRAINT `FK_Class_className_Class_name` FOREIGN KEY (`className`) REFERENCES `Class` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Student`
--

LOCK TABLES `Student` WRITE;
/*!40000 ALTER TABLE `Student` DISABLE KEYS */;
INSERT INTO `Student` VALUES ('8000112162','6658287f6744f9188e10bb5c59a9a4834b5f4789','bcc30c4344bc17b39caa3e49b033aee04fa3b092','罗川霁','软工121(Java)',1,'6#401','18720918502'),('8000112164','3e062d57f519b38e70522237821a920eac142aeb','b18da297b3bb972bd5543c4def9ed5a42af268c6','周圣盛','软工121(Java)',1,'6#402','15556999725');
/*!40000 ALTER TABLE `Student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `StudentFacultyMapping`
--

DROP TABLE IF EXISTS `StudentFacultyMapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `StudentFacultyMapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `studentId` varchar(20) DEFAULT NULL,
  `facultyId` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_facultyId` (`facultyId`),
  KEY `index_studentId` (`studentId`),
  CONSTRAINT `FK_StudentFacultyMapping_facultyId_Faculty_id` FOREIGN KEY (`facultyId`) REFERENCES `Faculty` (`id`),
  CONSTRAINT `FK_StudentFacultyMapping_studentId_Student_id` FOREIGN KEY (`studentId`) REFERENCES `Student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `StudentFacultyMapping`
--

LOCK TABLES `StudentFacultyMapping` WRITE;
/*!40000 ALTER TABLE `StudentFacultyMapping` DISABLE KEYS */;
/*!40000 ALTER TABLE `StudentFacultyMapping` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-25 21:27:02
