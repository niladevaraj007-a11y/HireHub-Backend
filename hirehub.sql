-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: hirehubdb
-- ------------------------------------------------------
-- Server version	8.0.46

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
-- Table structure for table `applications`
--

DROP TABLE IF EXISTS `applications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `applications` (
  `application_id` int NOT NULL AUTO_INCREMENT,
  `job_id` int NOT NULL,
  `seeker_id` int NOT NULL,
  `resume_id` int DEFAULT NULL,
  `status` enum('APPLIED','SHORTLISTED','INTERVIEW','SELECTED','REJECTED') DEFAULT 'APPLIED',
  `applied_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`application_id`),
  UNIQUE KEY `job_id` (`job_id`,`seeker_id`),
  KEY `seeker_id` (`seeker_id`),
  KEY `resume_id` (`resume_id`),
  CONSTRAINT `applications_ibfk_1` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`job_id`) ON DELETE CASCADE,
  CONSTRAINT `applications_ibfk_2` FOREIGN KEY (`seeker_id`) REFERENCES `job_seekers` (`seeker_id`) ON DELETE CASCADE,
  CONSTRAINT `applications_ibfk_3` FOREIGN KEY (`resume_id`) REFERENCES `resumes` (`resume_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `applications`
--

LOCK TABLES `applications` WRITE;
/*!40000 ALTER TABLE `applications` DISABLE KEYS */;
INSERT INTO `applications` VALUES (8,2,2,2,'REJECTED','2026-08-16 07:58:52'),(11,6,3,4,'SELECTED','2026-08-18 05:26:49'),(12,6,2,2,'SELECTED','2026-08-18 09:22:06'),(13,6,1,NULL,'REJECTED','2026-08-18 09:33:37'),(15,2,6,8,'REJECTED','2026-09-06 04:26:31'),(16,6,6,8,'SELECTED','2026-09-06 04:30:20'),(17,2,5,9,'SELECTED','2026-09-07 05:14:45'),(18,6,5,9,'SELECTED','2026-09-07 05:20:03'),(19,2,7,10,'SELECTED','2026-09-07 09:28:37'),(20,6,8,12,'SELECTED','2026-09-08 04:08:32'),(21,6,7,10,'SELECTED','2026-09-09 06:13:56'),(22,6,10,15,'REJECTED','2026-09-10 06:13:28'),(23,2,11,16,'SELECTED','2026-09-10 07:06:41'),(24,2,12,17,'SELECTED','2026-09-10 09:17:08'),(25,2,13,18,'SELECTED','2026-09-11 04:00:02'),(26,6,14,19,'REJECTED','2026-09-11 04:26:49'),(27,9,14,19,'SELECTED','2026-09-11 04:52:38');
/*!40000 ALTER TABLE `applications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `candidate_skills`
--

DROP TABLE IF EXISTS `candidate_skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidate_skills` (
  `seeker_id` int NOT NULL,
  `skill_id` int NOT NULL,
  `skill_level` enum('BEGINNER','INTERMEDIATE','ADVANCED') DEFAULT NULL,
  PRIMARY KEY (`seeker_id`,`skill_id`),
  KEY `skill_id` (`skill_id`),
  CONSTRAINT `candidate_skills_ibfk_1` FOREIGN KEY (`seeker_id`) REFERENCES `job_seekers` (`seeker_id`) ON DELETE CASCADE,
  CONSTRAINT `candidate_skills_ibfk_2` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`skill_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidate_skills`
--

LOCK TABLES `candidate_skills` WRITE;
/*!40000 ALTER TABLE `candidate_skills` DISABLE KEYS */;
INSERT INTO `candidate_skills` VALUES (1,1,'ADVANCED'),(1,2,'INTERMEDIATE'),(1,12,'INTERMEDIATE'),(1,14,'INTERMEDIATE'),(2,1,'ADVANCED'),(2,2,'INTERMEDIATE'),(2,4,'INTERMEDIATE'),(2,12,'INTERMEDIATE'),(2,14,'INTERMEDIATE');
/*!40000 ALTER TABLE `candidate_skills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `companies`
--

DROP TABLE IF EXISTS `companies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `companies` (
  `company_id` int NOT NULL AUTO_INCREMENT,
  `recruiter_id` int NOT NULL,
  `company_name` varchar(150) NOT NULL,
  `description` text,
  `website` varchar(255) DEFAULT NULL,
  `location` varchar(150) DEFAULT NULL,
  `company_size` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`company_id`),
  KEY `recruiter_id` (`recruiter_id`),
  CONSTRAINT `companies_ibfk_1` FOREIGN KEY (`recruiter_id`) REFERENCES `recruiters` (`recruiter_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `companies`
--

LOCK TABLES `companies` WRITE;
/*!40000 ALTER TABLE `companies` DISABLE KEYS */;
INSERT INTO `companies` VALUES (1,1,'TechNova Solutions','Software development and technology services company.','https://technova.example.com','Bangalore','51-200');
/*!40000 ALTER TABLE `companies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interviews`
--

DROP TABLE IF EXISTS `interviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interviews` (
  `interview_id` int NOT NULL AUTO_INCREMENT,
  `application_id` int NOT NULL,
  `interview_date` datetime NOT NULL,
  `interview_type` enum('ONLINE','OFFLINE','PHONE') DEFAULT 'ONLINE',
  `meeting_link` varchar(500) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `notes` text,
  `status` enum('SCHEDULED','COMPLETED','CANCELLED') DEFAULT 'SCHEDULED',
  PRIMARY KEY (`interview_id`),
  KEY `application_id` (`application_id`),
  CONSTRAINT `interviews_ibfk_1` FOREIGN KEY (`application_id`) REFERENCES `applications` (`application_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interviews`
--

LOCK TABLES `interviews` WRITE;
/*!40000 ALTER TABLE `interviews` DISABLE KEYS */;
INSERT INTO `interviews` VALUES (1,8,'2026-08-20 10:00:00','ONLINE','https://meet.google.com/test-hirehub',NULL,'Technical interview','COMPLETED'),(2,8,'2026-08-21 14:00:00','OFFLINE',NULL,'Bangalore Office','Face-to-face technical round','CANCELLED'),(3,8,'2026-08-25 11:00:00','ONLINE','https://meet.google.com/hirehub-demo',NULL,'Final technical round','CANCELLED'),(4,8,'2026-08-26 15:00:00','ONLINE','https://meet.google.com/hirehub-final',NULL,'Final HR round','COMPLETED'),(6,8,'2026-08-28 11:00:00','ONLINE','https://meet.google.com/hirehub-new',NULL,'Additional technical interview','CANCELLED'),(7,8,'2026-08-30 11:00:00','ONLINE','https://meet.google.com/hirehub-test6',NULL,'Technical round 2','COMPLETED'),(8,8,'2026-09-01 10:00:00','ONLINE','https://meet.google.com/hirehub-final-test',NULL,'Final interview test','COMPLETED'),(9,11,'2026-09-12 15:00:00','ONLINE','https://meet.google.com/new-hirehub-link',NULL,'Technical interview for Java Backend Developer Intern','COMPLETED'),(10,13,'2026-09-20 10:00:00','ONLINE','https://meet.google.com/hirehub-interview-13',NULL,'Technical interview for Java Backend Developer','COMPLETED'),(11,15,'2026-09-22 09:50:00','ONLINE','https://google meet-link/',NULL,'Be ready','COMPLETED'),(12,15,'2026-09-24 09:52:00','ONLINE','https://meet.google.com/test',NULL,'Technical interview - Java Full Stack Developer','COMPLETED'),(13,15,'2026-09-16 09:55:00','ONLINE','https://google meet-link/',NULL,'Technical interview - Java Full Stack Developer','COMPLETED'),(14,17,'2026-09-07 11:47:00','ONLINE','https://meet.google.com/test',NULL,'technical drive','COMPLETED'),(15,18,'2026-09-13 13:51:00','OFFLINE',NULL,'btm','Be ready','COMPLETED'),(16,16,'2026-09-16 11:02:00','ONLINE','https://meet.google.com/test',NULL,'Technical drive','COMPLETED'),(17,19,'2026-09-09 17:08:00','ONLINE','https://meet.google.com/abc-defg-hij',NULL,'Technical interview - Java and Spring Boot','COMPLETED'),(18,19,'2026-09-23 15:14:00','ONLINE','https://meet.google.com/test',NULL,NULL,'COMPLETED'),(19,20,'2026-09-10 10:00:00','ONLINE','https://meet.google.com/test-hirehub',NULL,'Technical interview','COMPLETED'),(20,23,'2026-09-11 10:30:00','ONLINE','https://meet.google.com/test',NULL,NULL,'COMPLETED'),(21,24,'2026-09-11 11:00:00','ONLINE','https://meet.google.com/test',NULL,NULL,'COMPLETED'),(22,25,'2026-09-21 13:31:00','ONLINE','https://meet/link',NULL,NULL,'COMPLETED'),(23,26,'2026-09-18 14:57:00','ONLINE','https://meet/link',NULL,NULL,'COMPLETED'),(24,27,'2026-09-12 11:00:00','ONLINE','https://meet.google.com/hirehub-test',NULL,'Technical interview for Backend Developer position','COMPLETED'),(25,21,'2026-09-24 14:31:00','ONLINE','https://meet.google.com/test',NULL,NULL,'COMPLETED');
/*!40000 ALTER TABLE `interviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_seekers`
--

DROP TABLE IF EXISTS `job_seekers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_seekers` (
  `seeker_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `headline` varchar(200) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  `experience_years` decimal(3,1) DEFAULT '0.0',
  `education` varchar(200) DEFAULT NULL,
  `about` text,
  PRIMARY KEY (`seeker_id`),
  UNIQUE KEY `user_id` (`user_id`),
  CONSTRAINT `job_seekers_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_seekers`
--

LOCK TABLES `job_seekers` WRITE;
/*!40000 ALTER TABLE `job_seekers` DISABLE KEYS */;
INSERT INTO `job_seekers` VALUES (1,2,'Java Full Stack Developer','Bangalore',0.0,'B.E Computer Science and Engineering','Fresher interested in Java backend and full-stack development.'),(2,3,'Java Backend Developer','Chennai',0.0,'B.E Computer Science and Engineering','Fresher skilled in Java, Spring Boot, MySQL and REST API development.'),(3,12,'Java Developer','Bangalore',0.0,'B.E Computer Science and Engineering','Fresher interested in Java backend development.'),(4,14,'Fresher','Not specified',0.0,'Not specified','Fresher looking for job opportunities.'),(5,15,'Fresher','Not specified',0.0,'Not specified','Fresher looking for job opportunities.'),(6,16,'Fresher','Not specified',0.0,'Not specified','Fresher looking for job opportunities.'),(7,6,'Fresher','Not specified',0.0,'Not specified','Fresher looking for job opportunities.'),(8,22,'Fresher','Bangalore',0.0,'Not specified','Frontend and backend testing.'),(9,21,'Fresher','Not specified',0.0,'Not specified','Fresher looking for job opportunities.'),(10,23,'Fresher','Not specified',0.0,'Not specified','Fresher looking for job opportunities.'),(11,25,'Fresher','Not specified',0.0,'Not specified','Fresher looking for job opportunities.'),(12,26,'Fresher','Not specified',0.0,'Not specified','Fresher looking for job opportunities.'),(13,27,'Fresher','Not specified',0.0,'Not specified','Fresher looking for job opportunities.'),(14,28,'Fresher','Not specified',0.0,'Not specified','Fresher looking for job opportunities.');
/*!40000 ALTER TABLE `job_seekers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_skills`
--

DROP TABLE IF EXISTS `job_skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_skills` (
  `job_id` int NOT NULL,
  `skill_id` int NOT NULL,
  `importance` enum('REQUIRED','PREFERRED') DEFAULT 'REQUIRED',
  PRIMARY KEY (`job_id`,`skill_id`),
  KEY `skill_id` (`skill_id`),
  CONSTRAINT `job_skills_ibfk_1` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`job_id`) ON DELETE CASCADE,
  CONSTRAINT `job_skills_ibfk_2` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`skill_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_skills`
--

LOCK TABLES `job_skills` WRITE;
/*!40000 ALTER TABLE `job_skills` DISABLE KEYS */;
INSERT INTO `job_skills` VALUES (2,1,'REQUIRED'),(2,2,'REQUIRED'),(2,14,'REQUIRED');
/*!40000 ALTER TABLE `job_skills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jobs`
--

DROP TABLE IF EXISTS `jobs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jobs` (
  `job_id` int NOT NULL AUTO_INCREMENT,
  `company_id` int NOT NULL,
  `title` varchar(150) NOT NULL,
  `description` text NOT NULL,
  `required_skills` text,
  `location` varchar(100) DEFAULT NULL,
  `job_type` enum('FULL_TIME','PART_TIME','INTERNSHIP','CONTRACT') DEFAULT 'FULL_TIME',
  `experience_required` decimal(3,1) DEFAULT '0.0',
  `min_salary` decimal(10,2) DEFAULT NULL,
  `max_salary` decimal(10,2) DEFAULT NULL,
  `vacancies` int DEFAULT '1',
  `status` enum('OPEN','CLOSED') DEFAULT 'OPEN',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`job_id`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `jobs_ibfk_1` FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jobs`
--

LOCK TABLES `jobs` WRITE;
/*!40000 ALTER TABLE `jobs` DISABLE KEYS */;
INSERT INTO `jobs` VALUES (2,1,'Junior Backend Developer','Looking for a Java backend developer to build REST APIs and backend services.','Java, REST API, JDBC, SQL, Git','Bangalore','FULL_TIME',1.0,350000.00,600000.00,3,'CLOSED','2026-09-06 05:10:25'),(6,1,'Java Backend Developer Intern','Updated internship for Java backend development','Java, SQL, JDBC, Spring Boot, Git','Bangalore','INTERNSHIP',0.0,180000.00,300000.00,3,'OPEN','2026-09-06 05:10:25'),(9,1,'Backend Developer','A Backend Developer is responsible for building and maintaining the server-side part of an application. They develop APIs, manage databases, implement business logic, and make sure the application works securely and efficiently.','Core Java Spring Boot REST API SQL/MySQL JDBC/JPA/Hibernate Git/GitHub Postman Basic knowledge of HTML, CSS, and JavaScript','Bangalore','FULL_TIME',NULL,NULL,NULL,1,'OPEN','2026-09-11 04:05:02');
/*!40000 ALTER TABLE `jobs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `notification_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `message` varchar(500) NOT NULL,
  `is_read` tinyint(1) DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`notification_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES (2,3,'Your application has been shortlisted',0,NULL),(3,3,'Your application has been rejected',0,NULL),(4,3,'Your application has been shortlisted',0,'2026-08-17 13:44:10'),(5,3,'Your interview has been scheduled for 2026-08-25T11:00',0,'2026-08-17 13:51:36'),(6,3,'Your interview scheduled for 2026-08-25T11:00 has been cancelled',0,'2026-08-17 13:52:28'),(7,3,'Your interview has been scheduled for 2026-08-26T15:00',0,'2026-08-17 13:53:11'),(8,3,'Your interview scheduled for 2026-08-26T15:00 has been completed',1,'2026-08-17 13:53:21'),(9,3,'Your interview has been scheduled for 2026-08-28T11:00',0,'2026-08-18 04:49:48'),(10,3,'Your interview has been scheduled for 2026-08-30T11:00',0,'2026-08-18 04:52:57'),(11,3,'Your interview scheduled for 2026-08-28T11:00 has been cancelled',0,'2026-08-18 04:53:16'),(12,3,'Your interview has been scheduled for 2026-09-01T10:00',0,'2026-08-18 04:54:27'),(13,3,'Your interview scheduled for 2026-08-30T11:00 has been completed',0,'2026-08-18 04:54:35'),(14,3,'Your application has been rejected',1,'2026-08-18 05:12:19'),(15,12,'Your application has been shortlisted',1,'2026-08-18 05:28:22'),(16,12,'Your interview has been scheduled for 2026-09-02T10:00',1,'2026-08-18 05:30:54'),(17,12,'Your interview scheduled for 2026-09-02T10:00 has been completed',1,'2026-08-18 05:32:08'),(18,12,'Your interview scheduled for 2026-09-02T10:00 has been completed',1,'2026-08-18 05:32:14'),(19,12,'Your interview scheduled for 2026-09-02T10:00 has been cancelled',1,'2026-08-18 05:35:32'),(20,12,'Your interview details have been updated',1,'2026-08-18 05:39:06'),(21,12,'Your interview has been scheduled for 2026-09-02T10:00',1,'2026-08-18 05:40:39'),(22,12,'Your interview scheduled for 2026-09-02T10:00 has been completed',1,'2026-08-18 05:41:00'),(23,12,'Your interview has been rescheduled to 2026-09-05T14:00',1,'2026-08-18 05:41:42'),(24,12,'Your interview scheduled for 2026-09-05T14:00 has been cancelled',1,'2026-08-18 09:01:24'),(25,12,'Your interview has been scheduled for 2026-09-05T14:00',1,'2026-08-18 09:01:42'),(26,12,'Your interview has been rescheduled to 2026-09-10T11:30',1,'2026-08-18 09:01:53'),(27,12,'Your interview details have been updated',1,'2026-08-18 09:02:07'),(28,12,'Your interview scheduled for 2026-09-12T15:00 has been completed',1,'2026-08-18 09:02:18'),(30,12,'Your application has been rejected',1,'2026-08-18 09:08:04'),(31,12,'Congratulations! You have been selected for the job',1,'2026-08-18 09:08:29'),(32,3,'Your application has been shortlisted',0,'2026-08-18 09:22:18'),(33,3,'Congratulations! You have been selected for the job',0,'2026-08-18 09:22:29'),(34,2,'Your application has been shortlisted',0,'2026-08-18 09:33:51'),(35,2,'Your interview has been scheduled for 2026-09-20T10:00',0,'2026-08-18 09:34:59'),(36,2,'Your application has been rejected',0,'2026-08-18 09:35:55'),(37,2,'Your interview scheduled for 2026-09-20T10:00 has been completed',0,'2026-08-18 09:38:12'),(38,13,'Your application has been received',1,'2026-08-18 10:29:38'),(39,13,'Your application status was updated',1,'2026-08-18 10:32:02'),(40,13,'Your resume was viewed',1,'2026-08-18 10:32:12'),(41,16,'Your application has been shortlisted',1,'2026-09-07 04:18:43'),(43,15,'Your application has been shortlisted',0,'2026-09-07 05:17:50'),(44,15,'Congratulations! You have been selected for the job',0,'2026-09-07 05:17:55'),(45,15,'Your application has been shortlisted',0,'2026-09-07 05:22:12'),(46,15,'Congratulations! You have been selected for the job',0,'2026-09-07 05:23:12'),(47,16,'Your application has been shortlisted',1,'2026-09-07 05:25:11'),(48,16,'Congratulations! You have been selected for the job',1,'2026-09-07 05:32:54'),(49,6,'Your application has been shortlisted',0,'2026-09-07 09:43:58'),(50,6,'Congratulations! You have been selected for the job',0,'2026-09-07 09:45:13'),(51,16,'Your application has been shortlisted',1,'2026-09-08 09:51:48'),(52,16,'Interview has been scheduled',1,'2026-09-08 09:55:50'),(53,16,'Interview has been scheduled',1,'2026-09-08 09:56:15'),(54,16,'Interview has been scheduled',1,'2026-09-08 09:56:57'),(55,22,'Your application has been shortlisted',0,'2026-09-08 09:58:44'),(56,16,'Test notification',1,'2026-09-08 10:02:43'),(57,22,'Congratulations! You have been selected for the job',0,'2026-09-08 10:22:38'),(58,6,'Your application has been shortlisted',0,'2026-09-09 06:15:24'),(59,23,'Your application has been shortlisted',0,'2026-09-10 06:31:41'),(60,23,'Your application has been rejected',0,'2026-09-10 06:32:26'),(61,25,'Your application has been shortlisted',0,'2026-09-10 07:08:03'),(62,26,'Your application has been shortlisted',1,'2026-09-10 09:18:06'),(63,25,'Your interview has been scheduled. Date: 2026-09-11T10:30. Type: Online. Meeting link: https://meet.google.com/test',0,'2026-09-10 09:23:29'),(64,25,'Your interview has been completed.',0,'2026-09-10 09:23:38'),(65,25,'Congratulations! You have been selected for the job',0,'2026-09-10 09:23:43'),(66,26,'Your interview has been scheduled. Date: 2026-09-11T11:00. Type: Online. Meeting link: https://meet.google.com/test',1,'2026-09-10 09:26:22'),(67,26,'Your interview has been completed.',1,'2026-09-10 09:26:27'),(68,26,'Congratulations! You have been selected for the job',1,'2026-09-10 09:26:31'),(69,27,'Your application has been shortlisted',0,'2026-09-11 04:00:44'),(70,27,'Your interview has been scheduled. Date: 2026-09-21T13:31. Type: Online. Meeting link: https://meet/link',0,'2026-09-11 04:01:51'),(71,27,'Your interview has been completed.',0,'2026-09-11 04:03:01'),(72,27,'Congratulations! You have been selected for the job',0,'2026-09-11 04:03:07'),(73,28,'Your application has been shortlisted',0,'2026-09-11 04:27:16'),(74,28,'Your interview has been scheduled. Date: 2026-09-18T14:57. Type: Online. Meeting link: https://meet/link',0,'2026-09-11 04:27:42'),(75,28,'Your interview has been completed.',0,'2026-09-11 04:28:15'),(76,28,'Your application has been rejected',0,'2026-09-11 04:28:19'),(77,28,'Your application has been shortlisted',0,'2026-09-11 04:53:32'),(78,28,'Your interview has been scheduled. Date: 2026-09-12T11:00. Type: Online. Meeting link: https://meet.google.com/hirehub-test',0,'2026-09-11 04:54:09'),(79,28,'Your interview has been completed.',0,'2026-09-11 04:54:36'),(80,28,'Congratulations! You have been selected for the job',0,'2026-09-11 04:55:05'),(81,6,'Your interview has been scheduled. Date: 2026-09-24T14:31. Type: Online. Meeting link: https://meet.google.com/test',0,'2026-09-11 05:01:59'),(82,6,'Your interview has been completed.',0,'2026-09-11 05:02:05'),(83,6,'Congratulations! You have been selected for the job',0,'2026-09-11 05:02:12');
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recruiters`
--

DROP TABLE IF EXISTS `recruiters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recruiters` (
  `recruiter_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `designation` varchar(100) DEFAULT NULL,
  `company_id` int NOT NULL,
  PRIMARY KEY (`recruiter_id`),
  UNIQUE KEY `user_id` (`user_id`),
  KEY `fk_recruiter_company` (`company_id`),
  CONSTRAINT `fk_recruiter_company` FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`) ON DELETE CASCADE,
  CONSTRAINT `recruiters_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recruiters`
--

LOCK TABLES `recruiters` WRITE;
/*!40000 ALTER TABLE `recruiters` DISABLE KEYS */;
INSERT INTO `recruiters` VALUES (1,1,'Senior HR Manager',1),(2,21,'HR Recruiter',1),(3,24,'HR Recruiter',1);
/*!40000 ALTER TABLE `recruiters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resumes`
--

DROP TABLE IF EXISTS `resumes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resumes` (
  `resume_id` int NOT NULL AUTO_INCREMENT,
  `seeker_id` int NOT NULL,
  `file_name` varchar(255) NOT NULL,
  `file_path` varchar(500) NOT NULL,
  `uploaded_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`resume_id`),
  KEY `seeker_id` (`seeker_id`),
  CONSTRAINT `resumes_ibfk_1` FOREIGN KEY (`seeker_id`) REFERENCES `job_seekers` (`seeker_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resumes`
--

LOCK TABLES `resumes` WRITE;
/*!40000 ALTER TABLE `resumes` DISABLE KEYS */;
INSERT INTO `resumes` VALUES (1,2,'Nila_Resume.pdf','C:/resumes/Nila_Resume.pdf','2026-08-16 05:39:05'),(2,2,'nilas pdf.pdf','uploads\\resumes\\1786867305338_nilas pdf.pdf','2026-08-16 08:01:45'),(4,3,'nilas pdf.pdf','uploads\\resumes\\1787030676469_nilas pdf.pdf','2026-08-18 05:24:37'),(6,3,'Nila D.pdf','uploads\\resumes\\1787072702014_Nila D.pdf','2026-08-18 17:05:02'),(7,3,'Nila D.pdf','uploads\\resumes\\1787151726910_Nila D.pdf','2026-08-19 15:02:07'),(8,6,'Nila D.pdf','uploads\\resumes\\1788619072029_Nila D.pdf','2026-09-05 14:37:52'),(9,5,'resume2.pdf','uploads\\resumes\\1788758076473_resume2.pdf','2026-09-07 05:14:36'),(10,7,'application10-resume.pdf','uploads\\resumes\\1788773305300_application10-resume.pdf','2026-09-07 09:28:25'),(12,8,'nilas pdf (1).pdf','uploads\\resumes\\1788840305179_nilas pdf (1).pdf','2026-09-08 04:05:05'),(13,8,'Nila D.pdf','uploads\\resumes\\1788842130582_Nila D.pdf','2026-09-08 04:35:31'),(14,8,'Nila D.pdf','uploads\\resumes\\1788842219596_Nila D.pdf','2026-09-08 04:37:00'),(15,10,'nilas pdf (1).pdf','uploads\\resumes\\1789020777059_nilas pdf (1).pdf','2026-09-10 06:12:57'),(16,11,'resume-download-test.pdf','uploads\\resumes\\1789023986069_resume-download-test.pdf','2026-09-10 07:06:26'),(17,12,'nilas pdf (1).pdf','uploads\\resumes\\1789031818202_nilas pdf (1).pdf','2026-09-10 09:16:58'),(18,13,'resume-download-test.pdf','uploads\\resumes\\1789099191310_resume-download-test.pdf','2026-09-11 03:59:51'),(19,14,'resume-download-test.pdf','uploads\\resumes\\1789100774914_resume-download-test.pdf','2026-09-11 04:26:15');
/*!40000 ALTER TABLE `resumes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saved_jobs`
--

DROP TABLE IF EXISTS `saved_jobs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `saved_jobs` (
  `saved_id` int NOT NULL AUTO_INCREMENT,
  `seeker_id` int NOT NULL,
  `job_id` int NOT NULL,
  `saved_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`saved_id`),
  UNIQUE KEY `seeker_id` (`seeker_id`,`job_id`),
  KEY `job_id` (`job_id`),
  CONSTRAINT `saved_jobs_ibfk_1` FOREIGN KEY (`seeker_id`) REFERENCES `job_seekers` (`seeker_id`) ON DELETE CASCADE,
  CONSTRAINT `saved_jobs_ibfk_2` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`job_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saved_jobs`
--

LOCK TABLES `saved_jobs` WRITE;
/*!40000 ALTER TABLE `saved_jobs` DISABLE KEYS */;
/*!40000 ALTER TABLE `saved_jobs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skills`
--

DROP TABLE IF EXISTS `skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skills` (
  `skill_id` int NOT NULL AUTO_INCREMENT,
  `skill_name` varchar(100) NOT NULL,
  PRIMARY KEY (`skill_id`),
  UNIQUE KEY `skill_name` (`skill_name`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skills`
--

LOCK TABLES `skills` WRITE;
/*!40000 ALTER TABLE `skills` DISABLE KEYS */;
INSERT INTO `skills` VALUES (17,'Angular'),(11,'C++'),(7,'CSS'),(12,'Git'),(13,'GitHub'),(6,'HTML'),(1,'Java'),(8,'JavaScript'),(2,'JDBC'),(3,'JSP'),(5,'MySQL'),(10,'Python'),(9,'React'),(4,'Servlets'),(15,'Spring Boot'),(14,'SQL');
/*!40000 ALTER TABLE `skills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(100) NOT NULL,
  `email` varchar(150) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('JOB_SEEKER','RECRUITER','ADMIN') NOT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `bio` varchar(1000) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `skills` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Arun Kumar','recruiter@hirehub.com','$2a$10$ymUkjdNlfKe1YEhcIywb6e/EFv2QfXRkME4axW8T/8JC5GLI5XD8q','RECRUITER','9876543210','2026-08-14 04:07:45',NULL,NULL,NULL),(2,'Nila Candidate','candidate@hirehub.com','123456','JOB_SEEKER','9876501234','2026-08-14 04:10:25',NULL,NULL,NULL),(3,'Nila Devaraj','niladevaraj51@gmail.com','$2a$10$nHAjIzMYEyB0AJDAMtZzC.90UFzzN3AQTsXJwS5ID7ChOt3Ks0qGu','JOB_SEEKER','9876501234','2026-08-14 08:28:34',NULL,NULL,NULL),(4,'New HireHub User','newuser@hirehub.com','$2a$10$gjR4USwfnn51UMnQ0lnvHOuwVEUOTzELFw.n2i2DI1RwNGutotvo6','JOB_SEEKER','9876543211','2026-08-16 04:48:31',NULL,NULL,NULL),(5,'Full Name: Test User','testuser123@gmail.com','$2a$10$oJhrgABImSb.tYZkP006GOEFZCgMJKxueyT56Al7yXcLPBsFdCF6G','RECRUITER','9876543210','2026-08-16 08:33:35',NULL,NULL,NULL),(6,'Akash D','Akash@gmail.com','$2a$10$COsbDdgtiiUr0cvckcR0DuRSF/TxKWmSYbn5VWKZphZqvcKs5Q8My','JOB_SEEKER','9876543210','2026-08-16 08:40:10',NULL,NULL,NULL),(7,'Nila','niladevaraj007@gmail.com','$2a$10$8eUY6NwrtsfzSw06btmEP.jJYleMBdsbw8tBQlU3TCVL4TbyIIJlS','JOB_SEEKER','4567892345','2026-08-16 08:49:15',NULL,NULL,NULL),(12,'Test Candidate','testcandidate2026@gmail.com','$2a$10$nxBw4fTmgSHsGoWsiPu4xe3X0B8uYu2S.jYGwZwI8l3PMOag3hwFm','JOB_SEEKER','9999999999','2026-08-18 05:18:54',NULL,NULL,NULL),(13,'Backend Test User','backendtest2026@gmail.com','$2a$10$GoBY/9N5r6aAJW.r4zbZv.h4lpnvJ.b2kUFin5TFkXR0ljUSYINiG','JOB_SEEKER',NULL,'2026-08-18 10:02:15',NULL,NULL,NULL),(14,'Nila Test','nila.test2026@hirehub.com','$2a$10$tbw7FcY89quTwHpcvxPBdu3eak2S8oLKWKaKMTZKbjYB/l8/lQWOG','JOB_SEEKER',NULL,'2026-08-20 10:29:33',NULL,NULL,NULL),(15,'Santhi R','santhi@gmail.com','$2a$10$zUYWLmKq/MaUQ45WqLAiL.u8E1k3.NaSaCOu186H7KZtMLCKLVZ12','JOB_SEEKER',NULL,'2026-08-20 14:10:44',NULL,NULL,NULL),(16,'Ammu','ammu@gmail.com','$2a$10$StZ6LloeRkLx6I0Dzzbj9utZ3y.mcHt5tHRjGuZMJAHOa6ghHG75q','JOB_SEEKER',NULL,'2026-08-24 13:54:35',NULL,NULL,NULL),(17,'anu','anuu@gmail.com','$2a$10$nDwXjDD1y6FnBti7Q79DkuH81GY3rMDwzML7s.aa6a/9ocJQd1maG','RECRUITER',NULL,'2026-08-24 13:55:44',NULL,NULL,NULL),(18,'anu','anu@gmail.com','$2a$10$d/fE.cZFr9uCG69xA7vSCOkRvCmoURN.fhVa40SvQdTwTR/tBh4Oi','RECRUITER',NULL,'2026-09-05 05:47:08',NULL,NULL,NULL),(19,'asha','asha@gmail.com','$2a$10$Dcnp9jbrkzinAN.Ek/4NquMLdhf.7vmrF3b/IgXWXE8j.3Z7QqwVG','RECRUITER',NULL,'2026-09-05 05:48:32',NULL,NULL,NULL),(20,'Priya Sharma','priya.recruiter@hirehub.com','$2a$10$gGdyejLax.l8OwctBVS2Me6j/zvOPAOF06InXohZeP.3FsJ6dKj.O','RECRUITER','9443125248','2026-09-05 05:56:01',NULL,NULL,NULL),(21,'ash','ash@gmail.com','$2a$10$VNWtSsOTZE/B5Z2s41JapeYCAn3R09XgwPZVEZj6Rg0gL5uXlP1PK','RECRUITER',NULL,'2026-09-06 04:54:44',NULL,NULL,NULL),(22,'priyadharhini L','priyadharshiniloganathan04@gmail.com','$2a$10$IOX5qDw0ZEGwDzElC001C.HlyjUlOMxMqAn.rdy7.xGVH0QEq7XPm','JOB_SEEKER','9876543211','2026-09-07 17:14:36','','','python,sql'),(23,'priyadharshini','priyachandru1475@gmail.com','$2a$10$gDI.cBzOX0lE2w1JeDTC/OjULp7LSLZg0d6KS1jcMyCBOdku73uhy','JOB_SEEKER',NULL,'2026-09-09 17:15:24',NULL,NULL,NULL),(24,'hariprasath','hari6905@gmail.com','$2a$10$rNrh88IiYfnRvwcIwLWsW.9Psc./qkxQolbbONMRO8bNHBB1z6./S','RECRUITER',NULL,'2026-09-10 06:14:27',NULL,NULL,NULL),(25,'dharshini','kittyze8008@gmail.com','$2a$10$Kss4WozuU0u.D8AOJcuoz.cpj3NAchMJ8Rd9/SBVU46KcEmmj/1m2','JOB_SEEKER',NULL,'2026-09-10 07:00:45',NULL,NULL,NULL),(26,'anusha','anusha@gmail.com','$2a$10$QmPsT2k0QG3qlSoTme7GdOlsGcwHvCPSlB/p.HeMTjZo0VVnTDqXa','JOB_SEEKER',NULL,'2026-09-10 09:16:19',NULL,NULL,NULL),(27,'Devaraj G','devaraj@gmail.com','$2a$10$YZbWNBQxhhxQShHSMgRof.63moZt36Ye5oq38b6qJ0B/0s718AnNu','JOB_SEEKER',NULL,'2026-09-11 03:59:08',NULL,NULL,NULL),(28,'Albin X.E.','albin@gmail.com','$2a$10$FdLg7qHhNqsU8XxXvIf4TutyqavKv0leMOw6TQDmMYYWT6ex5/xVW','JOB_SEEKER',NULL,'2026-09-11 04:25:20',NULL,NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-09-11 12:20:19
