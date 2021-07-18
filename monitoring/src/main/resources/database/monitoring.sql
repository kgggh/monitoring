
CREATE DATABASE `monitoring` 

CREATE TABLE `host` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '번호',
  `host_name` varchar(50) UNIQUE KEY DEFAULT NULL COMMENT '호스트이름',
  `host_address` varchar(50) UNIQUE KEY DEFAULT NULL COMMENT '호스트주소',
  `alive` char(1) DEFAULT 'N',
  `last_alive_time` datetime DEFAULT NULL COMMENT '마지막 활성화시간',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
