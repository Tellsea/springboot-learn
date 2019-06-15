/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50713
 Source Host           : localhost:3306
 Source Schema         : springboot-jdbc-templete

 Target Server Type    : MySQL
 Target Server Version : 50713
 File Encoding         : 65001

 Date: 15/06/2019 13:37:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `ctime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (4, 'tellsea', '654321', '2019-06-15 13:23:22');
INSERT INTO `tb_user` VALUES (5, 'tellsea', '123456', '2019-06-15 13:24:33');
INSERT INTO `tb_user` VALUES (6, 'tellsea', '123456', '2019-06-15 13:25:35');
INSERT INTO `tb_user` VALUES (7, 'tellsea', '123456', '2019-06-15 13:27:23');
INSERT INTO `tb_user` VALUES (8, 'tellsea', '123456', '2019-06-15 13:27:23');

SET FOREIGN_KEY_CHECKS = 1;
