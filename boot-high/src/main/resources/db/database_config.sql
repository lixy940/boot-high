/*
Navicat MySQL Data Transfer
Date: 2018-05-29 14:11:47
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
  `db_table_schema` varchar(64) DEFAULT NULL COMMENT 'postgresql,同一个库需要区分不同的模式',
  `db_user` varchar(64) DEFAULT NULL COMMENT '数据库用户名',
  `db_password` varchar(64) DEFAULT NULL COMMENT '数据库密码',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1 可用 0 不可用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_person_id` varchar(36) DEFAULT NULL COMMENT '创建人用户id',
  PRIMARY KEY (`db_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='沙盘数据库连接配置';

-- ----------------------------
-- Records of database_config
-- ----------------------------
INSERT INTO `database_config` VALUES ('1', '沙盘数据存储库', 'tidb', '192.168.20.xx', '4000', 'pas2', null, 'root', '123456', '1', '2018-05-24 14:39:09', '2018-05-24 17:50:38', 'lis');
INSERT INTO `database_config` VALUES ('2', '本地数据', 'mysql', 'localhost', '3306', 'db', null, 'root', '123456', '1', '2018-05-24 17:49:19', '2018-05-25 09:21:35', null);
INSERT INTO `database_config` VALUES ('3', 'oracle数据源1', 'oracle', '192.168.19.xx', '1521', 'BOL', null, 'bol', 'bol', '1', '2018-05-25 09:03:29', '2018-05-25 09:03:29', null);
INSERT INTO `database_config` VALUES ('4', 'es数据源', 'es', '192.168.19.xx', '9300', 'my-application', null, null, null, '1', '2018-05-28 09:59:42', '2018-05-28 10:00:10', null);
INSERT INTO `database_config` VALUES ('5', '测试环境数据源', 'mysql', '192.168.19.xx', '3306', 'pas', null, 'root', '123456', '1', '2018-05-28 16:20:54', '2018-05-29 11:32:53', null);
INSERT INTO `database_config` VALUES ('6', 'postgres数据源', 'postgresql', '192.168.20.xx', '5432', 'PAS2', 'public', 'postgres', '123456', '1', '2018-05-28 16:44:00', '2018-05-29 12:56:10', null);
