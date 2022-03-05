-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: threeam
-- ------------------------------------------------------
-- Server version	8.0.26

USE threeam;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '장바구니 고유값',
  `user_seq` int unsigned NOT NULL COMMENT '유저 고유값',
  `product_seq` int unsigned NOT NULL COMMENT '상품 고유값',
  `product_qty` int NOT NULL COMMENT '상품 수량',
  `delivery_date` date NOT NULL COMMENT '배송 날짜',
  `total_price` int NOT NULL COMMENT '상품별 총 가격',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `user_seq` (`user_seq`),
  KEY `product_seq_idx` (`product_seq`),
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`product_seq`) REFERENCES `product` (`id`),
  CONSTRAINT `user_seq` FOREIGN KEY (`user_seq`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci COMMENT='장바구니 테이블';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `id` varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT '주문 고유값',
  `user_seq` int unsigned NOT NULL COMMENT '유저 고유값',
  `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '유저 이름',
  `address` varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT '주소',
  `phone_num` varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT '전화번호',
  `email` varchar(120) COLLATE utf8_unicode_ci NOT NULL COMMENT '유저 이메일',
  `delivery_date` date NOT NULL COMMENT '배송 날짜',
  `order_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '주문 날짜',
  `order_total_price` int NOT NULL COMMENT '총 주문 가격',
  PRIMARY KEY (`id`),
  KEY `user_seq` (`user_seq`),
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`user_seq`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci COMMENT='주문 정보 테이블';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES ('user302022021257',30,'이이이','서울시 강남구','0100000000','test@naver.com','2022-02-22','2022-02-12 00:00:00',37000),('user302022021454',30,'이이이','서울시 강남구','0100000000','test@naver.com','2022-02-22','2022-02-14 00:00:00',37000),('user3020220226204341',30,'이이이','서울시 강남구','0100000000','test@naver.com','2022-03-18','2022-02-26 00:00:00',35800),('user3020220226204756',30,'이이이','서울시 강남구','0100000000','test@naver.com','2022-04-02','2022-02-26 00:00:00',22200),('user3020220226221813',30,'이이이','서울시 강남구','0100000000','test@naver.com','2022-03-05','2022-02-26 00:00:00',14800),('user302022022633',30,'이이이','서울시 강남구','0100000000','test@naver.com','2022-02-22','2022-02-26 00:00:00',37000),('user302022022634',30,'이이이','서울시 강남구','0100000000','test@naver.com','2022-02-22','2022-02-26 00:00:00',37000),('user302022022645',30,'이이이','서울시 강남구','0100000000','test@naver.com','2022-03-01','2022-02-26 00:00:00',16100),('user302022022653',30,'이이이','서울시 강남구','0100000000','test@naver.com','2022-02-27','2022-02-26 00:00:00',10500),('user3020220227011620',30,'이이이','서울시 강남구','0100000000','test@naver.com','2022-04-02','2022-02-27 00:00:00',22200);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_detail` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '주문상세 고유값',
  `order_seq` varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT '주문 고유값',
  `product_seq` int unsigned NOT NULL COMMENT '상품 고유값',
  `product_qty` int NOT NULL COMMENT '상품 수량',
  `product_price` int NOT NULL COMMENT '상품 가격',
  `delivery_date` date DEFAULT NULL COMMENT '배송 날짜',
  PRIMARY KEY (`id`),
  KEY `product_seq_idx` (`product_seq`),
  KEY `order_seq` (`order_seq`),
  CONSTRAINT `order_detail_ibfk_1` FOREIGN KEY (`order_seq`) REFERENCES `order` (`id`),
  CONSTRAINT `product_seq` FOREIGN KEY (`product_seq`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci COMMENT='주문 상품 테이블';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
INSERT INTO `order_detail` VALUES (13,'user302022021257',8,3,7400,NULL),(14,'user302022021257',9,2,7400,NULL),(15,'user302022021454',8,3,7400,NULL),(16,'user302022021454',9,2,7400,NULL),(17,'user302022022633',8,3,7400,NULL),(18,'user302022022633',9,2,7400,NULL),(19,'user302022022634',8,3,7400,NULL),(20,'user302022022634',9,2,7400,NULL),(21,'user302022022653',15,1,5300,NULL),(22,'user302022022653',11,1,5200,NULL),(23,'user302022022645',5,1,7400,NULL),(24,'user302022022645',16,3,2900,NULL),(25,'user3020220226204341',8,1,7400,NULL),(26,'user3020220226204341',7,1,7400,NULL),(27,'user3020220226204341',11,1,5200,NULL),(28,'user3020220226204341',10,1,5200,NULL),(29,'user3020220226204341',14,1,5300,NULL),(30,'user3020220226204341',13,1,5300,NULL),(31,'user3020220226204756',3,1,7400,NULL),(32,'user3020220226204756',2,1,7400,NULL),(33,'user3020220226204756',1,1,7400,NULL),(34,'user3020220226221813',6,1,7400,NULL),(35,'user3020220226221813',8,1,7400,NULL),(36,'user3020220227011620',7,1,7400,NULL),(37,'user3020220227011620',4,1,7400,NULL),(38,'user3020220227011620',6,1,7400,NULL);
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int unsigned NOT NULL COMMENT '상품 고유값',
  `product_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '상품명',
  `product_price` int NOT NULL COMMENT '상품 가격',
  `product_qty` int NOT NULL COMMENT '상품 수량',
  `category1` varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT '카테고리1: 샐러드/간편식',
  `category2` varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT '카테고리2: 샐러드/죽/도시락/음료',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci COMMENT='상품 테이블';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'[저염]비빔 샐러드',7400,0,'salad','salad'),(2,'[저염]불고기 샐러드',7400,0,'salad','salad'),(3,'[저염]초계 샐러드',7400,0,'salad','salad'),(4,'[단백질21g]커리치킨 샐러드',7400,0,'salad','salad'),(5,'[단백질23g]폴드포크 샐러드',7400,0,'salad','salad'),(6,'[단백질17g]베지두부면 샐러드',7400,0,'salad','salad'),(7,'[단백질19g]멕시칸 비프타코 샐러드',7400,0,'salad','salad'),(8,'그릭 다-요거트 보울 세트',7400,0,'salad','salad'),(9,'다-요거트 보울 세트',7400,0,'salad','salad'),(10,'[저칼로리141kcal]단호박죽',5200,0,'conven_food','soup'),(11,'[저칼로리166kcal]버섯죽',5200,0,'conven_food','soup'),(12,'[저칼로리234kcal]옛날팥죽',5200,0,'conven_food','soup'),(13,'[단백질22g]야채새우볶음밥',5300,0,'conven_food','packed_meal'),(14,'[단백질16g]매콤닭가슴살볶음밥',5300,0,'conven_food','packed_meal'),(15,'[단백질18g]카레큐브현미잡곡밥',5300,0,'conven_food','packed_meal'),(16,'플레인 요거트',2900,0,'conven_food','beverage'),(17,'ABC주스',3200,0,'conven_food','beverage'),(18,'곤약 젤리',2400,0,'conven_food','beverage');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '유저 고유값',
  `user_id` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '유저 아이디',
  `password` varchar(130) COLLATE utf8_unicode_ci NOT NULL COMMENT '비밀번호',
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '유저 이름',
  `phone_num` varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT '전화번호',
  `address` varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT '주소',
  `email` varchar(120) COLLATE utf8_unicode_ci NOT NULL COMMENT '유저 이메일',
  `refresh_token` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'refresh token',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `user_id` (`user_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci COMMENT='유저 테이블';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (30,'test','$2a$10$x0JQ0OHNwC5jApFq2FWtOOqfRDjw8/28obNEkoPvsJl2EUCFTXSOi','이이이','0100000000','서울시 강남구','test@naver.com','eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2NDg0ODQwMjR9.rb6eFgNu60eMXQQwnwsjhR1QWead8ajnsHB41ormvOamv9mrYeoFH6N1fUazbfU-aXWpAy4jC5RH_Yfm_zV_uA');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-05 13:57:50
