CREATE TABLE `xx_member` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `store_id` bigint(20) DEFAULT '0' COMMENT '门店id',
  `username` varchar(32) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `sex` smallint(2) DEFAULT '0' COMMENT '性别:0=男,1=女',
  `id_card_no` varchar(32) DEFAULT NULL COMMENT '身份证号码',
  `age` int(11) DEFAULT '0' COMMENT '年龄',
  `usertype` smallint(2) DEFAULT '0' COMMENT '用户类型:0=农民/1=推广员',
  `enabled` int(2) DEFAULT '0' COMMENT '是否锁定:0=否/1=是',
  `realname` varchar(32) DEFAULT NULL COMMENT '真实姓名',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `fieldsfact` varchar(100) DEFAULT NULL COMMENT '实际种地',
  `fields` varchar(100) DEFAULT NULL COMMENT '分地面积',
  `phone_number` varchar(12) DEFAULT NULL COMMENT '联系电话',
  `members` int(5) DEFAULT '1' COMMENT '家庭成员数量',
  `crop` varchar(20) DEFAULT NULL COMMENT '作物',
  `vip_card` varchar(16) DEFAULT NULL COMMENT '会员卡号',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` int(11) DEFAULT '0' COMMENT '创建人id',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `member_desc` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_vipcard` (`vip_card`),
  KEY `idx_username_storeId_enabled` (`store_id`,`username`,`enabled`)
) ENGINE=InnoDB AUTO_INCREMENT=6072 DEFAULT CHARSET=utf8 COMMENT='会员信息表';