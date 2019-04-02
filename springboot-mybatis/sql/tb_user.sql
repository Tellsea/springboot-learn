/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50713
Source Host           : localhost:3306
Source Database       : springboot-learn

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2019-04-02 13:35:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `passwd` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', 'tom', '123456');
INSERT INTO `tb_user` VALUES ('2', 'susan', '123456');
INSERT INTO `tb_user` VALUES ('3', 'super', '565656');
INSERT INTO `tb_user` VALUES ('4', 'about', '888888');
INSERT INTO `tb_user` VALUES ('5', 'relation', '000000');
INSERT INTO `tb_user` VALUES ('6', 'ship', '345678');
