/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50713
 Source Host           : localhost:3306
 Source Schema         : springboot-easypoi

 Target Server Type    : MySQL
 Target Server Version : 50713
 File Encoding         : 65001

 Date: 16/04/2019 10:31:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `birthday` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, 'tellsea', '1', '2019-04-15 00:00:00');
INSERT INTO `tb_user` VALUES (2, 'tellsea', '1', '2019-04-15 00:00:00');
INSERT INTO `tb_user` VALUES (3, 'fdsfds', '1', '2019-04-15 00:00:00');
INSERT INTO `tb_user` VALUES (4, 'tellsea', '1', '2019-04-15 00:00:00');
INSERT INTO `tb_user` VALUES (5, 'tellsea', '1', '2019-04-15 00:00:00');
INSERT INTO `tb_user` VALUES (6, 'dfdfds', '1', '2019-04-15 00:00:00');
INSERT INTO `tb_user` VALUES (7, 'tellsea', '1', '2019-04-15 00:00:00');
INSERT INTO `tb_user` VALUES (8, 'tellsea', '1', '2019-04-15 00:00:00');
INSERT INTO `tb_user` VALUES (9, 'tellsea', '1', '2019-04-15 00:00:00');
INSERT INTO `tb_user` VALUES (10, 'tellsea', '1', '2019-04-15 00:00:00');
INSERT INTO `tb_user` VALUES (11, 'tellsea', '1', '2019-04-15 00:00:00');
INSERT INTO `tb_user` VALUES (12, 'tellsea', '1', '2019-04-15 00:00:00');

SET FOREIGN_KEY_CHECKS = 1;
