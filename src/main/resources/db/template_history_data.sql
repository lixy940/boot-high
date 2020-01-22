/*
Navicat MySQL Data Transfer
Source Database       : pas

Target Server Type    : MYSQL
Target Server Version : 50639
File Encoding         : 65001

Date: 2018-11-22 16:18:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for template_history_data
-- ----------------------------
DROP TABLE IF EXISTS `template_history_data`;
CREATE TABLE `template_history_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `template_upload_id` int(11) DEFAULT NULL COMMENT '模板上传主键id',
  `remote_file_name` varchar(255) DEFAULT NULL COMMENT '远程文件名称',
  `type` tinyint(3) DEFAULT NULL COMMENT '类型 1 节点，2 关系',
  `relnode_num` int(11) DEFAULT NULL COMMENT '节点个数',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='模板历史上传数据表';
