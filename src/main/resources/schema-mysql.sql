  CREATE TABLE IF NOT EXISTS `foodmap` (
  `city` varchar(20) DEFAULT NULL,
  `name` varchar(20) NOT NULL,
  `score` float DEFAULT NULL,
  PRIMARY KEY (`name`));
  
PRIMARY KEY (`account`)CREATE TABLE IF NOT EXISTS `store` (
  `store_name` varchar(20) NOT NULL,
  `store_food` varchar(20) NOT NULL,
  `food_score` int DEFAULT NULL,
  `food_price` int DEFAULT NULL,
  PRIMARY KEY (`store_name`,`store_food`));