/*
 Navicat Premium Data Transfer

 Source Server         : Local_Host
 Source Server Type    : MySQL
 Source Server Version : 80014
 Source Host           : 127.0.0.1:3306
 Source Schema         : zhihu

 Target Server Type    : MySQL
 Target Server Version : 80014
 File Encoding         : 65001

 Date: 30/12/2019 01:18:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for history_today
-- ----------------------------
DROP TABLE IF EXISTS `history_today`;
CREATE TABLE `history_today`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `year` int(10) NULL DEFAULT NULL COMMENT '年',
  `month` int(10) NULL DEFAULT NULL COMMENT '月',
  `day` int(10) NULL DEFAULT NULL COMMENT '天',
  `type` int(10) NULL DEFAULT NULL COMMENT '类型：1：国外，2：国内',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '历史的今天' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
