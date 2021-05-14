-- ----------------------------
-- Table structure for `fund_data`
-- ----------------------------
DROP TABLE IF EXISTS `fund_data`;
CREATE TABLE `fund_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(6) NOT NULL COMMENT '基金代码',
  `name` varchar(32) DEFAULT NULL COMMENT '基金名称',
  `net_worth_date` varchar(10) DEFAULT NULL COMMENT '净值日期',
  `net_asset_value` decimal(6,2) DEFAULT NULL COMMENT '基金单位净值',
  `net_accumulate_value` decimal(6,2) DEFAULT NULL COMMENT '积累净值',
  `day_of_growth` decimal(6,2) DEFAULT NULL,
  `state_purchase` int(1) DEFAULT NULL COMMENT '申购状态（0-关闭，1-开放）',
  `state_redeem` int(1) DEFAULT NULL COMMENT '申购状态（0-关闭，1-开放）',
  `buy_rate` varchar(10) DEFAULT NULL COMMENT '购买费率',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_code_date` (`code`,`net_worth_date`),
  KEY `idx_date` (`net_worth_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4423469 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `fund_list`
-- ----------------------------
DROP TABLE IF EXISTS `fund_list`;
CREATE TABLE `fund_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(6) NOT NULL COMMENT '基金代码',
  `name` varchar(32) DEFAULT NULL COMMENT '基金名称',
  `simple_name` varchar(255) DEFAULT NULL COMMENT '基金简称',
  `catagory` varchar(32) DEFAULT NULL COMMENT '基金类型',
  `risk_level` varchar(16) DEFAULT NULL COMMENT '风险等级',
  `fund_scale` varchar(32) DEFAULT NULL COMMENT '基金规模',
  `fund_manager` varchar(16) DEFAULT NULL COMMENT '基金经理',
  `build_date` date DEFAULT NULL COMMENT '成立日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_code` (`code`),
  KEY `idx_catagory` (`catagory`)
) ENGINE=InnoDB AUTO_INCREMENT=23354 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `scheduled_cron`
-- ----------------------------
DROP TABLE IF EXISTS `scheduled_cron`;
CREATE TABLE `scheduled_cron` (
  `cron_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `cron_key` varchar(32) NOT NULL COMMENT '定时任务名称',
  `cron_class` varchar(128) NOT NULL COMMENT '定时任务完整类名',
  `cron_expression` varchar(20) NOT NULL COMMENT 'cron表达式',
  `task_explain` varchar(50) NOT NULL DEFAULT '' COMMENT '任务描述',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态,1:正常;2:停用',
  PRIMARY KEY (`cron_id`),
  UNIQUE KEY `cron_key` (`cron_key`),
  UNIQUE KEY `cron_class` (`cron_class`),
  UNIQUE KEY `cron_key_unique_idx` (`cron_key`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='定时任务表';

-- ----------------------------
-- Table structure for `sys_sequence`
-- ----------------------------
DROP TABLE IF EXISTS `sys_sequence`;
CREATE TABLE `sys_sequence` (
  `id` varchar(32) NOT NULL COMMENT '序号',
  `module_name` varchar(50) DEFAULT NULL COMMENT '模块名称',
  `module_code` varchar(50) DEFAULT NULL COMMENT '模块编码',
  `templet` varchar(50) DEFAULT NULL COMMENT '当前模块使用序号模板',
  `max_serial` varchar(32) DEFAULT NULL COMMENT '存放当前流水号的值',
  `pre_max_num` int(3) DEFAULT NULL COMMENT '预生成流水号存放在缓存中的个数',
  `icre_num` int(1) DEFAULT NULL COMMENT '增长数量',
  `seq_length` int(2) DEFAULT NULL COMMENT '流水长度',
  UNIQUE KEY `pri_id` (`id`),
  UNIQUE KEY `uni_code` (`module_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流水定义表';