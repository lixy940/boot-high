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
  `area_type` int(4) DEFAULT NULL COMMENT '区域类型：离线：1  专题：2  本地：3  数据结果：10',
  `db_ip` varchar(64) NOT NULL COMMENT '数据库ip',
  `db_port` varchar(32) NOT NULL COMMENT '数据库端口号',
  `db_server_name` varchar(64) NOT NULL COMMENT '服务名称',
  `db_table_schema` varchar(64) DEFAULT NULL COMMENT 'postgresql,同一个库需要区分不同的模式',
  `db_relkind` varchar(32) DEFAULT NULL COMMENT 'postgresql库区分视图与表 r:表 v:视图',
  `db_user` varchar(64) DEFAULT NULL COMMENT '数据库用户名',
  `db_password` varchar(64) DEFAULT NULL COMMENT '数据库密码',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1 可用 0 不可用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_person_id` varchar(36) DEFAULT NULL COMMENT '创建人用户id',
  PRIMARY KEY (`db_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='数据库连接配置';

-- ----------------------------
-- Records of database_config
-- ----------------------------
INSERT INTO `database_config` ( `db_name`, `db_type`, `area_type`, `db_ip`, `db_port`, `db_server_name`, `db_table_schema`, `db_relkind`, `db_user`, `db_password`, `status`, `create_time`, `update_time`, `create_person_id`) VALUES ( 'tidb', 'mysql', NULL, '192.168.19.xx', '3306', 'uploadlocal', NULL, NULL, 'root', '123456', '1', '2018-05-28 16:20:54', '2018-09-06 15:50:36', NULL);
INSERT INTO `database_config` ( `db_name`, `db_type`, `area_type`, `db_ip`, `db_port`, `db_server_name`, `db_table_schema`, `db_relkind`, `db_user`, `db_password`, `status`, `create_time`, `update_time`, `create_person_id`) VALUES ('demo数据源', 'postgresql', NULL, '192.168.19.xx', '5432', 'PAS2', 'demo', 'r', 'postgres', '123456', '1', '2018-05-28 16:44:00', '2018-10-09 18:08:50', NULL);
INSERT INTO `database_config` ( `db_name`, `db_type`, `area_type`, `db_ip`, `db_port`, `db_server_name`, `db_table_schema`, `db_relkind`, `db_user`, `db_password`, `status`, `create_time`, `update_time`, `create_person_id`) VALUES ( 'demo', 'mysql', '3', '192.168.19.xx', '3306', 'demo', NULL, NULL, 'root', '123456', '1', '2018-05-28 16:20:54', '2018-09-12 18:20:04', NULL);
INSERT INTO `database_config` ( `db_name`, `db_type`, `area_type`, `db_ip`, `db_port`, `db_server_name`, `db_table_schema`, `db_relkind`, `db_user`, `db_password`, `status`, `create_time`, `update_time`, `create_person_id`) VALUES ( 'demo_view', 'postgresql', NULL, '192.168.19.xx', '5432', 'PAS2', 'demo', 'v', 'postgres', '123456', '1', NULL, '2018-10-09 18:27:58', NULL);

