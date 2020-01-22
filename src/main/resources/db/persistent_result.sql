/*
Navicat MySQL Data Transfer
Target Server Type    : MYSQL
Target Server Version : 50639
File Encoding         : 65001

Date: 2018-05-29 14:14:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for persistent_result
-- ----------------------------
DROP TABLE IF EXISTS `persistent_result`;
CREATE TABLE `persistent_result` (
  `result_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `result_cname` varchar(64) NOT NULL COMMENT '表的中文名（数据结果中文名）',
  `result_ename` varchar(64) NOT NULL COMMENT '表的英文名（数据结果英文名）',
  `row_num` int(11) DEFAULT NULL COMMENT '表记录数',
  `db_id` int(11) NOT NULL COMMENT '数据库配置id',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1 可用 0 不可用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_person_id` varchar(36) DEFAULT NULL COMMENT '创建人用户id',
  PRIMARY KEY (`result_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='数据结果（表）';

-- ----------------------------
-- Records of persistent_result
-- ----------------------------
INSERT INTO `persistent_result` VALUES ('1', '126分析结果', 'analyze_case_result', '5000', '1', '1', '2018-05-24 14:42:47', '2018-05-24 14:42:47', 'xx');
INSERT INTO `persistent_result` VALUES ('2', '119案', 'drug_case', '118', '1', '1', '2018-05-24 14:55:04', '2018-05-24 14:55:04', 'xx');
INSERT INTO `persistent_result` VALUES ('3', '119毒案', 'drug_case', '118', '1', '1', '2018-05-24 15:17:24', '2018-05-24 15:17:24', 'xx');
INSERT INTO `persistent_result` VALUES ('4', '119毒案', 'drug_case', '118', '1', '1', '2018-05-24 15:18:50', '2018-05-24 15:18:50', 'xx');
