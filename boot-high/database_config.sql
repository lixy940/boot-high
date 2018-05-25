/*
Navicat MySQL Data Transfer

Source Server         : PAS
Source Server Version : 50639
Source Host           : 192.168.19.161:3306
Source Database       : pas

Target Server Type    : MYSQL
Target Server Version : 50639
File Encoding         : 65001

Date: 2018-05-25 12:57:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for database_config
-- ----------------------------
DROP TABLE IF EXISTS `database_config`;
CREATE TABLE `database_config` (
  `db_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `db_name` varchar(64) NOT NULL COMMENT '数据库名字',
  `db_type` varchar(64) NOT NULL COMMENT '数据库类型',
  `db_ip` varchar(64) NOT NULL COMMENT '数据库ip',
  `db_port` varchar(32) NOT NULL COMMENT '数据库端口号',
  `db_server_name` varchar(64) NOT NULL COMMENT '服务名称',
  `db_user` varchar(64) DEFAULT NULL COMMENT '数据库用户名',
  `db_password` varchar(64) DEFAULT NULL COMMENT '数据库密码',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1 可用 0 不可用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_person_id` varchar(36) DEFAULT NULL COMMENT '创建人用户id',
  PRIMARY KEY (`db_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='沙盘数据库连接配置';

-- ----------------------------
-- Records of database_config
-- ----------------------------
INSERT INTO `database_config` VALUES ('1', '沙盘数据存储库', 'tidb', '192.168.20.233', '4000', 'pas2', 'root', '123456', '1', '2018-05-24 14:39:09', '2018-05-24 17:50:38', 'lis');
INSERT INTO `database_config` VALUES ('2', '本地数据', 'mysql', 'localhost', '3306', 'db', 'root', '123456', '1', '2018-05-24 17:49:19', '2018-05-25 09:21:35', null);
INSERT INTO `database_config` VALUES ('3', 'oracle数据源1', 'oracle', '192.168.19.58', '1521', 'BOL', 'bol', 'bol', '1', '2018-05-25 09:03:29', '2018-05-25 09:03:29', null);
