-- MySQL dump 10.13  Distrib 8.0.22, for macos10.15 (x86_64)
--
-- Host: 127.0.0.1    Database: LibrarySystem
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Book`
--

DROP TABLE IF EXISTS `Book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Book` (
  `id` int NOT NULL AUTO_INCREMENT,
  `isbn` varchar(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `category` varchar(50) DEFAULT NULL,
  `remainNum` int NOT NULL DEFAULT '1',
  `price` decimal(7,2) DEFAULT '0.00',
  `author` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Book_isbm_uindex` (`isbn`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Book`
--

LOCK TABLES `Book` WRITE;
/*!40000 ALTER TABLE `Book` DISABLE KEYS */;
INSERT INTO `Book` VALUES (1,'9787040396638','Advanced mathematics I','TeachingMaterial',10,47.60,'TongJiUniversity'),(2,'9787040196719','Circuit','TeachingMaterial',8,54.50,'QiuGuanyuan'),(3,'9787302481447','C Programming','TeachingMaterial',12,39.00,'TanHaoqiang'),(4,'9787040479751','Mechanics of materials I','TeachingMaterial',3,48.00,'LiuHongwen'),(5,'9787040370683','Mechanical principle','TeachingMaterial',3,37.00,'SunHeng'),(6,'1145141919810','FUCK your mother','fuck',6,12.50,'SunHeng'),(7,'9787040494839','Outline of modern Chinese history','TeachingMaterial',9,26.00,'ShaJiansun'),(8,'9787030317926','Semiconductor physics','TeachingMaterial',4,43.00,'YuNingmei'),(9,'9787536131767','Health education for youth students','TeachingMaterial',3,15.00,'ZhongShan'),(10,'9787307192881','New edition of mental health education for College Students','TeachingMaterial',3,45.00,'HuangYanping'),(11,'9787562624363','College military theory course','TeachingMaterial',2,39.80,'ZhangJianying'),(12,'9787040523102','College Physical Education and health','TeachingMaterial',2,46.00,'GongJianlin'),(13,'9787511041203','Vocabulary of CET-4','Examination',4,48.00,'YuMinhong'),(14,'9787115521637','C Primer Plus','Programming',10,108.00,'StephenPrata'),(15,'9787115279460','C++ Primer Plus','Programming',10,99.00,'StephenPrata'),(16,'9787302444541','Java, From introduction to mastery','Programming',6,69.60,'TomorrowTech'),(17,'9787115428028','Python Programming, From introduction to mastery','Programming',10,89.00,'EricMatthes'),(18,'9787302506904','C, From introduction to mastery','Programming',6,99.80,'TomorrowTech'),(19,'9787115467294','Le Destin de I\'Univers','Astronomy',1,99.00,'Jean-PierreLuminet'),(20,'9787040298154','Principles and methods of critical thinking','ThinkingScience',1,37.00,'DongYu'),(21,'9787302461128','MATLAB R2016a, From introduction to mastery','MathematicalModeling ',5,89.00,'WenYanxin'),(22,'9787302377894','MATLAB mathematical experiment and modeling','MathematicalModeling ',4,53.00,'ZhangDefeng'),(23,'9787040452112','Entrepreneurship navigation for College Students','TeachingMaterial',2,26.80,'MiYinjun'),(24,'9787040452129','Employment guidance for College Students','TeachingMaterial',2,25.00,'MiYinjun'),(25,'9787570914197','Practical Handbook of College Students\' career development','TeachingMaterial',2,32.00,'TangYaoping'),(26,'9771674678208','Current affairs report for College Students','TeachingMaterial',1,20.00,'PublicityDepartmentOfTheCPCCentralCommittee'),(27,'9787040341362','Algorithm and data structure, C language description','Programming',2,39.00,'ZhangNaixiao'),(28,'9787121239502','Write CPU by yourself','Hardware',1,99.00,'LeiSilei'),(29,'9787115480521','Teach you to design CPU, RISC-V processor','Hardware',1,99.00,'HuZhenbo'),(30,'9787115364692','Design and manufacture CPU and MCU','Hardware',1,89.00,'JiangYonghong'),(31,'9787115429674','Linux command line and shell script programming','OperatingSystem',2,109.00,'RichardBlum'),(32,'9787547816370','The Feynman lectures on physics Volume II','Physics',5,98.00,'R.P.Feynman'),(33,'9787040444933','Fundamentals of digital electronic technology','TeachingMaterial',10,54.00,'TsingHuaUniversity'),(34,'9787115191120','MySQL Crash Course ','Database',4,49.00,'BenForta'),(35,'9787115472588','Linux Basics by NiaoGe','OperatingSystem',4,118.00,'NiaoGe'),(36,'9787115470317','That\'s how Linux should learn','OperatingSystem',3,79.00,'LiuChuan'),(37,'9787115171788','Ruminations on C++','Programming',2,55.00,'AndrewKoenig'),(38,'9787111534259','Computer Science Illuminated','Computer',11,79.00,'NellDale'),(39,'0787302379959','Principle and application of MCS-51 single chip microcomputer','Hardware',3,31.00,'YuHongqi'),(40,'9787115171795','C Traps and Pitfalls','Programming',2,30.00,'AndrewKoenig'),(41,'9787115172013','Pointers on C','Programming',2,65.00,'AndrewKoenig'),(42,'9787115171801','Expert C Programming','Programming',2,45.00,'AndrewKoenig'),(43,'9787030454324','Hello, Amplifier','Hardware',3,68.00,'YangJianguo'),(44,'9787121085314','RF Circuit Design, Theory and Applications, Second Edition','Physics',5,59.00,'ReinholdLudwing'),(45,'9787302142621','Principles of Electric Circuits','TeachingMaterial',12,58.00,'JiangJiguang'),(46,'9787560624624','Fundamentals of RF circuit','TeachingMaterial',11,33.00,'ZhaoJianxun'),(47,'9787302307518','Fundamentals of digital electronic technology','TeachingMaterial',11,28.00,'WangKeyi'),(48,'9787121194276','Signals and Systems','TeachingMaterial',10,69.00,'AlanV.Oppenheim'),(49,'9787121343711','Fundamentals of circuit design engineering calculation','Physics',6,59.00,'WuYeqing'),(50,'9787040425055','Fundamentals of analog electronic technology','TeachingMaterial',11,59.90,'TsingHuaUniversity'),(51,'9787560641652','Electromagnetic field and electromagnetic wave','TeachingMaterial',10,43.00,'WangJiali'),(52,'9787121329227','Practical Electronics for Inventors','Physics',3,128.00,'PaulScherz'),(53,'9787302279983','Learning Guide and Exercises for Principles of Electric Circus','Physics',4,39.00,'ZhuGuiping'),(54,'9787122231109','I\'m a master of electronics','Physics',2,46.00,'MenHong'),(55,'9787040055535','Function of complex variable','Mathematics',5,15.00,'Xi\'anJiaotongUniversity'),(56,'9787040347654','Integral transformation','Mathematics',5,12.00,'Xi\'anJiaotongUniversity'),(57,'9787040186932','Concise calculus','TeachingMaterial',8,37.00,'GongSheng'),(58,'9787040396614','Engineering mathematics, linear algebra','TeachingMaterial',10,19.40,'TongJiUniversity'),(59,'9787040238969','Probability theory and mathematical statistics','TeachingMaterial',10,37.40,'ZheJiangUniversity'),(60,'9787518901432','Brief course of College Physics','Physics',9,43.00,'FanYangcai'),(61,'9787559630612','Quantum Physics','Physics',2,59.80,'CaoTianyuan'),(62,'9787547835975','The art of insight in science and engineering mastering complexity','Physics',2,78.00,'SanjoyMahajan'),(74,'9787566635477','F**K!','Test',4,1999.99,'gregPerlinLi');
/*!40000 ALTER TABLE `Book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CommonStaff`
--

DROP TABLE IF EXISTS `CommonStaff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CommonStaff` (
  `id` int NOT NULL AUTO_INCREMENT,
  `staffName` varchar(50) NOT NULL,
  `uid` int DEFAULT NULL,
  `gender` char(1) NOT NULL,
  `phoneNum` varchar(20) DEFAULT NULL,
  `authority` int NOT NULL DEFAULT '10',
  PRIMARY KEY (`id`),
  UNIQUE KEY `CommonStaff_staffName_uindex` (`staffName`),
  UNIQUE KEY `CommonStaff_uid_uindex` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CommonStaff`
--

LOCK TABLES `CommonStaff` WRITE;
/*!40000 ALTER TABLE `CommonStaff` DISABLE KEYS */;
INSERT INTO `CommonStaff` VALUES (2,'Xiaoming',20001,'M','13223335645',90),(3,'Xiaohong',20002,'F','13345432256',20),(4,'Xiaojun',20003,'M','13566689721',10),(5,'Xiaomi',20004,'F','13114567654',10),(6,'Xiaobai',20005,'F','13744859984',10),(7,'Xiaobing',20006,'M','18433231235',10),(8,'Xiaolin',20007,'M','18234339878',10),(9,'Xiaoxin',20008,'F','19611377651',10),(10,'Xiaowang',20009,'M','18911258876',10),(11,'Xiaoli',20010,'F','13566789972',10),(12,'Xiaoqiang',20011,'M','19788657732',10),(13,'Xiaofei',20012,'M','14655932232',10),(17,'TestStaff',20013,'M','13622887499',10),(18,'LiYilang',20014,'M','13311112321',10);
/*!40000 ALTER TABLE `CommonStaff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Curator`
--

DROP TABLE IF EXISTS `Curator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Curator` (
  `id` int NOT NULL AUTO_INCREMENT,
  `curatorName` varchar(50) NOT NULL,
  `uid` int DEFAULT NULL,
  `gender` char(1) NOT NULL,
  `phoneNum` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `authority` int NOT NULL DEFAULT '100',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Curator_curatorName_uindex` (`curatorName`),
  UNIQUE KEY `Curator_uid_uindex` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Curator`
--

LOCK TABLES `Curator` WRITE;
/*!40000 ALTER TABLE `Curator` DISABLE KEYS */;
INSERT INTO `Curator` VALUES (1,'Lihaolin',10001,'M','18319448827','lihaolin13@outlook.com',100);
/*!40000 ALTER TABLE `Curator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Schedule`
--

DROP TABLE IF EXISTS `Schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Schedule` (
  `id` int NOT NULL AUTO_INCREMENT,
  `day` varchar(10) DEFAULT NULL,
  `firstShift` varchar(50) DEFAULT NULL,
  `secondShift` varchar(50) DEFAULT NULL,
  `thirdShift` varchar(50) DEFAULT NULL,
  `fourthShift` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Schedule`
--

LOCK TABLES `Schedule` WRITE;
/*!40000 ALTER TABLE `Schedule` DISABLE KEYS */;
INSERT INTO `Schedule` VALUES (1,'Mon',NULL,NULL,NULL,NULL),(2,'Tue',NULL,NULL,NULL,NULL),(3,'Wed',NULL,NULL,NULL,NULL),(4,'Thur',NULL,NULL,NULL,NULL),(5,'Fri',NULL,NULL,NULL,NULL),(6,'Sat',NULL,NULL,NULL,NULL),(7,'Sun',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `Schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(50) NOT NULL,
  `uid` int DEFAULT NULL,
  `userName` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL DEFAULT 'E10ADC3949BA59ABBE56E057F20F883E',
  PRIMARY KEY (`id`),
  UNIQUE KEY `User_account_uindex` (`account`),
  UNIQUE KEY `User_userName_uindex` (`userName`),
  UNIQUE KEY `User_uid_uindex` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'XMing',20001,'Xiaoming','E10ADC3949BA59ABBE56E057F20F883E'),(2,'XHong',20002,'Xiaohong','E10ADC3949BA59ABBE56E057F20F883E'),(3,'XJun',20003,'Xiaojun','E10ADC3949BA59ABBE56E057F20F883E'),(4,'XMi',20004,'Xiaomi','E10ADC3949BA59ABBE56E057F20F883E'),(5,'XBai',20005,'Xiaobai','E10ADC3949BA59ABBE56E057F20F883E'),(6,'XBing',20006,'Xiaobing','E10ADC3949BA59ABBE56E057F20F883E'),(7,'XLin',20007,'Xiaolin','E10ADC3949BA59ABBE56E057F20F883E'),(8,'XXin',20008,'Xiaoxin','E10ADC3949BA59ABBE56E057F20F883E'),(9,'XWang',20009,'Xiaowang','E10ADC3949BA59ABBE56E057F20F883E'),(10,'XLi',20010,'Xiaoli','E10ADC3949BA59ABBE56E057F20F883E'),(11,'gregPerlinLi',10001,'Lihaolin','A0CFBC2A027CD5985B41ADF71B6226FF'),(13,'XQiang',20011,'Xiaoqiang','7E3B571D7A1209C12442A09C6D6F008A'),(15,'XFei',20012,'Xiaofei','E10ADC3949BA59ABBE56E057F20F883E'),(18,'TStaff',20013,'TestStaff','C4CA4238A0B923820DCC509A6F75849B'),(19,'LargeKindergarten',20014,'LiYilang','E10ADC3949BA59ABBE56E057F20F883E');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-20 18:06:42
