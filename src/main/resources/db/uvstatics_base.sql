/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50723
Source Host           : 127.0.0.1:3306
Source Database       : uvstatics_base

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2019-07-05 15:51:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bz_product_mobile
-- ----------------------------
DROP TABLE IF EXISTS `bz_product_mobile`;
CREATE TABLE `bz_product_mobile` (
  `product_id` int(11) NOT NULL COMMENT '业务产品id',
  `phone_number` varchar(11) NOT NULL COMMENT '手机号',
  `short_url` varchar(100) DEFAULT NULL COMMENT '短链接url',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`product_id`,`phone_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品手机号关系表';

-- ----------------------------
-- Records of bz_product_mobile
-- ----------------------------

-- ----------------------------
-- Table structure for bz_product_short
-- ----------------------------
DROP TABLE IF EXISTS `bz_product_short`;
CREATE TABLE `bz_product_short` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `prodct_name` varchar(100) DEFAULT NULL COMMENT '产品名称',
  `product_url` varchar(100) DEFAULT NULL COMMENT '产品静态url',
  `phone_path` varchar(100) DEFAULT NULL COMMENT '上传文件路径',
  `phone_count` int(11) DEFAULT NULL COMMENT '产品对应手机号总量',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态 0:已创建，1:跑批处理中，2:跑批完成',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `operate_user` varchar(100) DEFAULT NULL COMMENT '创建用户id',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1011 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of bz_product_short
-- ----------------------------
INSERT INTO `bz_product_short` VALUES ('1001', '测试1', 'asf', 'C:\\fakepath\\output.txt', null, '0', null, '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2019-07-04 12:22:27', '2019-07-04 12:22:27');
INSERT INTO `bz_product_short` VALUES ('1002', '测试2', 'asf', 'C:\\fakepath\\output.txt', null, '0', null, '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2019-07-04 12:27:31', '2019-07-04 12:27:31');
INSERT INTO `bz_product_short` VALUES ('1003', '测试3', 'r', 'C:\\fakepath\\output.txt', null, '0', null, '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2019-07-04 13:51:37', '2019-07-04 13:51:37');
INSERT INTO `bz_product_short` VALUES ('1004', '测试4', 'asf', 'C:\\fakepath\\output.txt', null, '0', null, '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2019-07-04 13:55:39', '2019-07-04 13:55:39');
INSERT INTO `bz_product_short` VALUES ('1005', '测试5', 'asf', 'C:\\fakepath\\output.txt', null, '0', null, '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2019-07-04 16:02:27', '2019-07-04 16:02:27');
INSERT INTO `bz_product_short` VALUES ('1006', '测试6', 'asfasf', 'C:\\fakepath\\output.txt', null, '0', null, '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2019-07-04 16:05:58', '2019-07-04 16:05:58');
INSERT INTO `bz_product_short` VALUES ('1007', '测试7', 'afas', 'C:\\fakepath\\output.txt', null, '0', null, '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2019-07-04 16:08:43', '2019-07-04 16:08:43');
INSERT INTO `bz_product_short` VALUES ('1008', '测试8', 'afas', 'C:\\fakepath\\ddddddd.log', null, '0', null, '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2019-07-04 16:25:56', '2019-07-04 16:25:56');
INSERT INTO `bz_product_short` VALUES ('1009', '测试9', 'ffff', 'C:\\fakepath\\output.txt', null, '0', null, '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2019-07-04 16:29:25', '2019-07-04 16:29:25');
INSERT INTO `bz_product_short` VALUES ('1010', '测试10', 'asfas', 'C:\\fakepath\\output.txt', null, '0', null, '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2019-07-04 16:42:11', '2019-07-04 16:42:11');

-- ----------------------------
-- Table structure for bz_translate
-- ----------------------------
DROP TABLE IF EXISTS `bz_translate`;
CREATE TABLE `bz_translate` (
  `product_id` int(11) NOT NULL COMMENT '业务产品id',
  `phone_number` varchar(11) NOT NULL COMMENT '手机号',
  `pv_number` decimal(6,2) DEFAULT NULL COMMENT 'pv点击次数除以总数',
  `click_number` int(11) DEFAULT NULL COMMENT '点击次数',
  `mobile_system` varchar(11) DEFAULT NULL COMMENT '操作系统',
  `mobile_version` varchar(11) DEFAULT NULL COMMENT '手机浏览器版本',
  `mobile_brand` varchar(11) DEFAULT NULL COMMENT '手机品牌',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`product_id`,`phone_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户点击转化表';

-- ----------------------------
-- Records of bz_translate
-- ----------------------------
INSERT INTO `bz_translate` VALUES ('1001', '1', '1.00', '1', '1', '1', '1', '2019-07-05 13:48:22');
INSERT INTO `bz_translate` VALUES ('1002', '15810302515', '0.00', '1', '1', '1', '1', '2019-07-05 13:48:22');
INSERT INTO `bz_translate` VALUES ('1003', '15810302515', '0.00', '1', '1', '1', '1', '2019-07-05 13:48:22');
INSERT INTO `bz_translate` VALUES ('1004', '15810302515', '0.00', '1', '1', '1', '1', '2019-07-05 13:48:22');
INSERT INTO `bz_translate` VALUES ('1005', '15810302515', '1.00', '1', '1', '1', '1', '2019-07-05 13:48:22');
INSERT INTO `bz_translate` VALUES ('1006', '15810302515', '1.00', '1', '1', '1', '1', '2019-07-05 13:48:22');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` varchar(36) NOT NULL,
  `name` varchar(40) DEFAULT NULL COMMENT '菜单名称',
  `parent_id` varchar(36) DEFAULT NULL COMMENT '父菜单',
  `level` bigint(2) DEFAULT NULL COMMENT '菜单层级',
  `parent_ids` varchar(2000) DEFAULT NULL COMMENT '父菜单联集',
  `sort` smallint(6) DEFAULT NULL COMMENT '排序',
  `href` varchar(2000) DEFAULT NULL COMMENT '链接地址',
  `target` varchar(20) DEFAULT NULL COMMENT '打开方式',
  `icon` varchar(100) DEFAULT NULL COMMENT '菜单图标',
  `bg_color` varchar(255) DEFAULT NULL COMMENT '显示背景色',
  `is_show` tinyint(2) DEFAULT NULL COMMENT '是否显示',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限标识',
  `create_by` varchar(36) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` varchar(36) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `del_flag` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('30a9ce3b2bba47a7abd64801b350cc59', '产品任务管理', null, '1', '30a9ce3b2bba47a7abd64801b350cc59,', '2', '/admin/product/list', null, '', '', '1', '', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2019-07-04 12:08:58', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2019-07-04 12:08:58', null, '0');
INSERT INTO `sys_menu` VALUES ('3b54e2a2-9adb-11e8-aebe-1368d4ec24eb', '用户管理', '7d1020ee-9ad9-11e8-aebe-1368d4ec24eb', '2', '7d1020ee-9ad9-11e8-aebe-1368d4ec24eb,3b54e2a2-9adb-11e8-aebe-1368d4ec24eb,', '9', '/admin/system/user/list', null, '', '#47e69c', '1', 'sys:user:list', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-01-16 11:31:18', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-01-20 05:59:20', null, '0');
INSERT INTO `sys_menu` VALUES ('3b58e01e-9adb-11e8-aebe-1368d4ec24eb', '角色管理', '7d1020ee-9ad9-11e8-aebe-1368d4ec24eb', '2', '7d1020ee-9ad9-11e8-aebe-1368d4ec24eb,3b58e01e-9adb-11e8-aebe-1368d4ec24eb,', '10', '/admin/system/role/list', null, '', '#c23ab9', '1', 'sys:role:list', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-01-16 11:32:33', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-01-20 05:58:58', null, '0');
INSERT INTO `sys_menu` VALUES ('3b5cb607-9adb-11e8-aebe-1368d4ec24eb', '权限管理', '7d1020ee-9ad9-11e8-aebe-1368d4ec24eb', '2', '7d1020ee-9ad9-11e8-aebe-1368d4ec24eb,3b5cb607-9adb-11e8-aebe-1368d4ec24eb,', '20', '/admin/system/menu/list', null, '', '#d4573b', '1', 'sys:menu:list', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-01-16 11:33:19', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-02-08 09:49:28', null, '0');
INSERT INTO `sys_menu` VALUES ('3e0b86a3-9adc-11e8-aebe-1368d4ec24eb', '新增用户', '3b54e2a2-9adb-11e8-aebe-1368d4ec24eb', '3', '7d1020ee-9ad9-11e8-aebe-1368d4ec24eb,3b54e2a2-9adb-11e8-aebe-1368d4ec24eb,3e0b86a3-9adc-11e8-aebe-1368d4ec24eb,', '0', '', null, null, null, '0', 'sys:user:add', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-02-08 10:10:32', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-02-08 10:10:32', null, '0');
INSERT INTO `sys_menu` VALUES ('3e2fa8b6-9adc-11e8-aebe-1368d4ec24eb', '编辑用户', '3b54e2a2-9adb-11e8-aebe-1368d4ec24eb', '3', '7d1020ee-9ad9-11e8-aebe-1368d4ec24eb,3b54e2a2-9adb-11e8-aebe-1368d4ec24eb,3e2fa8b6-9adc-11e8-aebe-1368d4ec24eb,', '10', '', null, null, null, '0', 'sys:user:edit', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-02-08 10:11:49', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-02-08 10:11:49', null, '0');
INSERT INTO `sys_menu` VALUES ('3e36cf2f-9adc-11e8-aebe-1368d4ec24eb', '删除用户', '3b54e2a2-9adb-11e8-aebe-1368d4ec24eb', '3', '7d1020ee-9ad9-11e8-aebe-1368d4ec24eb,3b54e2a2-9adb-11e8-aebe-1368d4ec24eb,3e36cf2f-9adc-11e8-aebe-1368d4ec24eb,', '20', '', null, null, null, '0', 'sys:user:delete', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-02-08 10:12:43', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-02-08 10:12:43', null, '0');
INSERT INTO `sys_menu` VALUES ('7d1020ee-9ad9-11e8-aebe-1368d4ec24eb', '系统管理', null, '1', '7d1020ee-9ad9-11e8-aebe-1368d4ec24eb,', '1', '', null, '', null, '1', '', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-01-16 11:29:46', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-01-20 03:09:26', null, '0');
INSERT INTO `sys_menu` VALUES ('96fd6a5a-9adb-11e8-aebe-1368d4ec24eb', '新增权限', '3b5cb607-9adb-11e8-aebe-1368d4ec24eb', '3', '7d1020ee-9ad9-11e8-aebe-1368d4ec24eb,3b5cb607-9adb-11e8-aebe-1368d4ec24eb,96fd6a5a-9adb-11e8-aebe-1368d4ec24eb,', '0', '', null, null, null, '0', 'sys:menu:add', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-02-08 09:49:15', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-02-08 09:49:38', null, '0');
INSERT INTO `sys_menu` VALUES ('9703ccf2-9adb-11e8-aebe-1368d4ec24eb', '编辑权限', '3b5cb607-9adb-11e8-aebe-1368d4ec24eb', '3', '7d1020ee-9ad9-11e8-aebe-1368d4ec24eb,3b5cb607-9adb-11e8-aebe-1368d4ec24eb,9703ccf2-9adb-11e8-aebe-1368d4ec24eb,', '10', '', null, null, null, '0', 'sys:menu:edit', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-02-08 09:50:16', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-02-08 09:50:25', null, '0');
INSERT INTO `sys_menu` VALUES ('9707cf58-9adb-11e8-aebe-1368d4ec24eb', '删除权限', '3b5cb607-9adb-11e8-aebe-1368d4ec24eb', '3', '7d1020ee-9ad9-11e8-aebe-1368d4ec24eb,3b5cb607-9adb-11e8-aebe-1368d4ec24eb,9707cf58-9adb-11e8-aebe-1368d4ec24eb,', '20', '', null, null, null, '0', 'sys:menu:delete', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-02-08 09:51:53', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-02-08 09:53:42', null, '0');
INSERT INTO `sys_menu` VALUES ('ed63866b30cf46bfb6797a1d31ae930c', '锁定用户', '3b54e2a2-9adb-11e8-aebe-1368d4ec24eb', '3', '7d1020ee-9ad9-11e8-aebe-1368d4ec24eb,3b54e2a2-9adb-11e8-aebe-1368d4ec24eb,ed63866b30cf46bfb6797a1d31ae930c,', '21', '', null, null, '', '0', 'sys:user:lock', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-08-21 17:44:05', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-08-21 17:44:05', null, '0');
INSERT INTO `sys_menu` VALUES ('ff619e04-9adb-11e8-aebe-1368d4ec24eb', '新增角色', '3b58e01e-9adb-11e8-aebe-1368d4ec24eb', '3', '7d1020ee-9ad9-11e8-aebe-1368d4ec24eb,3b58e01e-9adb-11e8-aebe-1368d4ec24eb,ff619e04-9adb-11e8-aebe-1368d4ec24eb,', '0', '', null, null, null, '0', 'sys:role:add', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-02-08 09:58:11', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-02-08 09:58:11', null, '0');
INSERT INTO `sys_menu` VALUES ('ff9477c9-9adb-11e8-aebe-1368d4ec24eb', '编辑权限', '3b58e01e-9adb-11e8-aebe-1368d4ec24eb', '3', '7d1020ee-9ad9-11e8-aebe-1368d4ec24eb,3b58e01e-9adb-11e8-aebe-1368d4ec24eb,ff9477c9-9adb-11e8-aebe-1368d4ec24eb,', '10', '', null, null, null, '0', 'sys:role:edit', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-02-08 09:59:01', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-02-08 09:59:01', null, '0');
INSERT INTO `sys_menu` VALUES ('ff9ad846-9adb-11e8-aebe-1368d4ec24eb', '删除角色', '3b58e01e-9adb-11e8-aebe-1368d4ec24eb', '3', '7d1020ee-9ad9-11e8-aebe-1368d4ec24eb,3b58e01e-9adb-11e8-aebe-1368d4ec24eb,ff9ad846-9adb-11e8-aebe-1368d4ec24eb,', '20', '', null, null, null, '0', 'sys:role:delete', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-02-08 09:59:56', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-02-08 09:59:56', null, '0');

-- ----------------------------
-- Table structure for sys_rescource
-- ----------------------------
DROP TABLE IF EXISTS `sys_rescource`;
CREATE TABLE `sys_rescource` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `file_name` varchar(255) DEFAULT NULL COMMENT '文件名称',
  `source` varchar(255) DEFAULT NULL COMMENT '来源',
  `web_url` varchar(500) DEFAULT NULL COMMENT '资源网络地址',
  `hash` varchar(255) DEFAULT NULL COMMENT '文件标识',
  `file_size` varchar(50) DEFAULT NULL COMMENT '文件大小',
  `file_type` varchar(255) DEFAULT NULL COMMENT '文件类型',
  `original_net_url` text,
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(36) DEFAULT NULL COMMENT '创建人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(36) DEFAULT NULL COMMENT '修改人',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(4) DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统资源';

-- ----------------------------
-- Records of sys_rescource
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(36) NOT NULL,
  `name` varchar(40) DEFAULT NULL COMMENT '角色名称',
  `create_date` datetime DEFAULT NULL,
  `create_by` varchar(36) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_by` varchar(36) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `del_flag` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('70689483-9ad7-11e8-aebe-1368d4ec24eb', '前台用户', '2017-11-02 14:19:07', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-08-21 20:04:02', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '', '0');
INSERT INTO `sys_role` VALUES ('706e0195-9ad7-11e8-aebe-1368d4ec24eb', '系统管理员', '2017-11-29 19:36:37', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2019-07-04 12:09:31', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '', '0');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` varchar(36) NOT NULL,
  `menu_id` varchar(36) NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('706e0195-9ad7-11e8-aebe-1368d4ec24eb', '30a9ce3b2bba47a7abd64801b350cc59');
INSERT INTO `sys_role_menu` VALUES ('706e0195-9ad7-11e8-aebe-1368d4ec24eb', '3b54e2a2-9adb-11e8-aebe-1368d4ec24eb');
INSERT INTO `sys_role_menu` VALUES ('706e0195-9ad7-11e8-aebe-1368d4ec24eb', '3b58e01e-9adb-11e8-aebe-1368d4ec24eb');
INSERT INTO `sys_role_menu` VALUES ('706e0195-9ad7-11e8-aebe-1368d4ec24eb', '3b5cb607-9adb-11e8-aebe-1368d4ec24eb');
INSERT INTO `sys_role_menu` VALUES ('706e0195-9ad7-11e8-aebe-1368d4ec24eb', '3e0b86a3-9adc-11e8-aebe-1368d4ec24eb');
INSERT INTO `sys_role_menu` VALUES ('706e0195-9ad7-11e8-aebe-1368d4ec24eb', '3e2fa8b6-9adc-11e8-aebe-1368d4ec24eb');
INSERT INTO `sys_role_menu` VALUES ('706e0195-9ad7-11e8-aebe-1368d4ec24eb', '3e36cf2f-9adc-11e8-aebe-1368d4ec24eb');
INSERT INTO `sys_role_menu` VALUES ('706e0195-9ad7-11e8-aebe-1368d4ec24eb', '7d1020ee-9ad9-11e8-aebe-1368d4ec24eb');
INSERT INTO `sys_role_menu` VALUES ('706e0195-9ad7-11e8-aebe-1368d4ec24eb', '96fd6a5a-9adb-11e8-aebe-1368d4ec24eb');
INSERT INTO `sys_role_menu` VALUES ('706e0195-9ad7-11e8-aebe-1368d4ec24eb', '9703ccf2-9adb-11e8-aebe-1368d4ec24eb');
INSERT INTO `sys_role_menu` VALUES ('706e0195-9ad7-11e8-aebe-1368d4ec24eb', '9707cf58-9adb-11e8-aebe-1368d4ec24eb');
INSERT INTO `sys_role_menu` VALUES ('706e0195-9ad7-11e8-aebe-1368d4ec24eb', 'ed63866b30cf46bfb6797a1d31ae930c');
INSERT INTO `sys_role_menu` VALUES ('706e0195-9ad7-11e8-aebe-1368d4ec24eb', 'ff619e04-9adb-11e8-aebe-1368d4ec24eb');
INSERT INTO `sys_role_menu` VALUES ('706e0195-9ad7-11e8-aebe-1368d4ec24eb', 'ff9477c9-9adb-11e8-aebe-1368d4ec24eb');
INSERT INTO `sys_role_menu` VALUES ('706e0195-9ad7-11e8-aebe-1368d4ec24eb', 'ff9ad846-9adb-11e8-aebe-1368d4ec24eb');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(36) NOT NULL COMMENT '用户ID',
  `login_name` varchar(36) DEFAULT NULL COMMENT '登录名',
  `nick_name` varchar(40) DEFAULT NULL COMMENT '昵称',
  `icon` varchar(2000) DEFAULT NULL,
  `password` varchar(40) DEFAULT NULL COMMENT '密码',
  `salt` varchar(40) DEFAULT NULL COMMENT 'shiro加密盐',
  `tel` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱地址',
  `locked` tinyint(2) DEFAULT NULL COMMENT '是否锁定',
  `create_date` datetime DEFAULT NULL,
  `create_by` varchar(36) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `update_by` varchar(36) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `del_flag` tinyint(4) NOT NULL,
  `is_admin` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('18b8b543-9ad7-11e8-aebe-1368d4ec24eb', 'admin', '我是管理员', null, '9c5feb7aba88c7c87bc047c7cec7c6e3b63e1894', '08c5900125b80cd2', '13776055179', 'b@qq.com', '0', '2017-11-27 22:19:39', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '2018-08-28 13:25:18', '18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '', '0', '1');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` varchar(36) NOT NULL,
  `role_id` varchar(36) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('18b8b543-9ad7-11e8-aebe-1368d4ec24eb', '706e0195-9ad7-11e8-aebe-1368d4ec24eb');
