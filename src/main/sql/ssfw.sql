/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3308
 Source Server Type    : MySQL
 Source Server Version : 100503
 Source Host           : localhost:3308
 Source Schema         : ssfw

 Target Server Type    : MySQL
 Target Server Version : 100503
 File Encoding         : 65001

 Date: 18/09/2022 19:58:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for auth_group
-- ----------------------------
DROP TABLE IF EXISTS `auth_group`;
CREATE TABLE `auth_group`  (
  `group_id` bigint(19) UNSIGNED NOT NULL COMMENT '小组id',
  `group_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '小组名称',
  `group_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '小组编号',
  `tenant_id` int(9) UNSIGNED NOT NULL COMMENT '租户id',
  `creator_id` int(19) UNSIGNED NULL DEFAULT NULL COMMENT '创建者ID',
  `creator_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建者',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updater_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '更新者ID',
  `updater_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`group_id`) USING BTREE,
  UNIQUE INDEX `uni_auth_group_code`(`group_code`) USING BTREE,
  INDEX `fk_auth_group_tenant_id`(`tenant_id`) USING BTREE,
  CONSTRAINT `fk_auth_group_tenant_id` FOREIGN KEY (`tenant_id`) REFERENCES `com_tenant` (`tenant_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户小组' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of auth_group
-- ----------------------------
INSERT INTO `auth_group` VALUES (1570616152429735937, 'sddf', 'sse', 1, NULL, '1', '2022-09-18 13:33:31', NULL, NULL, NULL);
INSERT INTO `auth_group` VALUES (1570638458648641537, 'sddf', 'ss3e', 1, NULL, '1', '2022-09-18 13:33:31', NULL, NULL, NULL);
INSERT INTO `auth_group` VALUES (1570648207603642370, 'sddf', 'ss3g', 1, NULL, '1', '2022-09-18 13:33:31', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for auth_res
-- ----------------------------
DROP TABLE IF EXISTS `auth_res`;
CREATE TABLE `auth_res`  (
  `res_id` bigint(19) UNSIGNED NOT NULL COMMENT '资源id',
  `res_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '资源名称',
  `res_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '资源编号',
  `res_url` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '资源访问路径',
  `menu_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单名称',
  `menu_icon` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单图标',
  `tenant_id` int(9) UNSIGNED NOT NULL COMMENT '租户id',
  `creator_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '创建者ID',
  `creator_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建者',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updater_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '更新者ID',
  `updater_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`res_id`) USING BTREE,
  UNIQUE INDEX `uk_auth_res_code`(`res_code`) USING BTREE,
  INDEX `fk_auth_res_tenant_id`(`tenant_id`) USING BTREE,
  CONSTRAINT `fk_auth_res_tenant_id` FOREIGN KEY (`tenant_id`) REFERENCES `com_tenant` (`tenant_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '系统资源' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of auth_res
-- ----------------------------

-- ----------------------------
-- Table structure for auth_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_role`;
CREATE TABLE `auth_role`  (
  `role_id` bigint(19) UNSIGNED NOT NULL COMMENT '角色id',
  `role_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色code',
  `role_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色名称',
  `role_desc` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '描述',
  `tenant_id` int(9) UNSIGNED NOT NULL COMMENT '租户id',
  `creator_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '创建者ID',
  `creator_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建者',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updater_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '更新者ID',
  `updater_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE INDEX `uk_auth_role_code`(`tenant_id`, `role_code`) USING BTREE,
  INDEX `fk_auth_role_tenant_id`(`tenant_id`) USING BTREE,
  CONSTRAINT `fk_auth_role_tenant_id` FOREIGN KEY (`tenant_id`) REFERENCES `com_tenant` (`tenant_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of auth_role
-- ----------------------------
INSERT INTO `auth_role` VALUES (1, 'user', '用户', NULL, 1, NULL, '1', '2022-09-18 13:36:19', NULL, NULL, NULL);
INSERT INTO `auth_role` VALUES (2, 'admin', '管理员', NULL, 1, NULL, '1', '2022-09-18 13:36:19', NULL, NULL, NULL);
INSERT INTO `auth_role` VALUES (3, 'user', '用户', NULL, 2, NULL, '1', '2022-09-18 13:36:19', NULL, NULL, NULL);
INSERT INTO `auth_role` VALUES (4, 'admin', '管理员', NULL, 2, NULL, '1', '2022-09-18 13:36:19', NULL, NULL, NULL);
INSERT INTO `auth_role` VALUES (5, 'user', '用户', NULL, 3, NULL, '1', '2022-09-18 13:36:19', NULL, NULL, NULL);
INSERT INTO `auth_role` VALUES (6, 'admin', '管理员', NULL, 3, NULL, '1', '2022-09-18 13:36:19', NULL, NULL, NULL);
INSERT INTO `auth_role` VALUES (7, 'user', '用户', NULL, 4, NULL, '1', '2022-09-18 13:36:19', NULL, NULL, NULL);
INSERT INTO `auth_role` VALUES (8, 'admin', '管理员', NULL, 4, NULL, '1', '2022-09-18 13:36:19', NULL, NULL, NULL);
INSERT INTO `auth_role` VALUES (9, 'user', '用户', NULL, 5, NULL, '1', '2022-09-18 13:36:19', NULL, NULL, NULL);
INSERT INTO `auth_role` VALUES (10, 'admin', '管理员', NULL, 5, NULL, '1', '2022-09-18 13:36:19', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for auth_role_res
-- ----------------------------
DROP TABLE IF EXISTS `auth_role_res`;
CREATE TABLE `auth_role_res`  (
  `role_id` bigint(19) UNSIGNED NOT NULL COMMENT '角色id',
  `res_id` bigint(19) UNSIGNED NOT NULL COMMENT '资源id',
  `auth_date` datetime(0) NOT NULL COMMENT '授权日期',
  PRIMARY KEY (`role_id`, `res_id`) USING BTREE,
  INDEX `fk_auth_role_res_res_id`(`res_id`) USING BTREE,
  CONSTRAINT `fk_auth_role_res_res_id` FOREIGN KEY (`res_id`) REFERENCES `auth_res` (`res_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_auth_role_res_role_id` FOREIGN KEY (`role_id`) REFERENCES `auth_role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色权限' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of auth_role_res
-- ----------------------------

-- ----------------------------
-- Table structure for auth_user
-- ----------------------------
DROP TABLE IF EXISTS `auth_user`;
CREATE TABLE `auth_user`  (
  `user_id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '登录名称',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
  `nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户昵称',
  `use_status` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '账号状态 valid有效 suspend挂起',
  `group_id` bigint(19) UNSIGNED NULL DEFAULT NULL COMMENT '分组id',
  `tenant_id` int(9) UNSIGNED NOT NULL COMMENT '租户id',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 1.删除',
  `creator_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '创建者ID',
  `creator_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建者',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updater_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '更新者ID',
  `updater_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `uk_auth_user_username`(`username`) USING BTREE,
  INDEX `fk_auth_user_group_id`(`group_id`) USING BTREE,
  INDEX `fk_auth_user_tenant_id`(`tenant_id`) USING BTREE,
  CONSTRAINT `fk_auth_user_group_id` FOREIGN KEY (`group_id`) REFERENCES `auth_group` (`group_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_auth_user_tenant_id` FOREIGN KEY (`tenant_id`) REFERENCES `com_tenant` (`tenant_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of auth_user
-- ----------------------------
INSERT INTO `auth_user` VALUES (1, 'sysadmin', 'NjBmMTI5NDgwMGQ3ODI0YjkxZjMyYTdhMjgyZTVjODczZGM2ODIwOWM1ODMxNWEzYmRjNDg0NmVhN2M1MzAyYw==', '系统管理员', 'valid', NULL, 1, 0, 1, '1', '2022-09-18 13:37:11', NULL, NULL, NULL);
INSERT INTO `auth_user` VALUES (2, 'sysadmin2', '000000', '系统管理员', 'valid', NULL, 2, 0, 1, '1', '2022-09-18 13:37:11', NULL, NULL, NULL);
INSERT INTO `auth_user` VALUES (3, 'zhangs', '123456', '张三', 'valid', NULL, 1, 0, 1, '1', '2022-09-18 13:37:11', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for auth_user_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_user_role`;
CREATE TABLE `auth_user_role`  (
  `user_id` bigint(19) UNSIGNED NOT NULL COMMENT '用户id',
  `role_id` bigint(19) UNSIGNED NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户关联角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of auth_user_role
-- ----------------------------
INSERT INTO `auth_user_role` VALUES (1, 1);
INSERT INTO `auth_user_role` VALUES (1, 3);

-- ----------------------------
-- Table structure for com_action_detail
-- ----------------------------
DROP TABLE IF EXISTS `com_action_detail`;
CREATE TABLE `com_action_detail`  (
  `detail_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '操作明细ID',
  `action_id` int(11) UNSIGNED NOT NULL COMMENT '操作日志ID',
  `field_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字段名称',
  `old_val` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '旧值',
  `new_val` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '新值',
  PRIMARY KEY (`detail_id`) USING BTREE,
  INDEX `fk_sp_action_detail_act`(`action_id`) USING BTREE,
  CONSTRAINT `fk_sp_action_detail_act` FOREIGN KEY (`action_id`) REFERENCES `com_action_log` (`action_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作日志明细' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of com_action_detail
-- ----------------------------

-- ----------------------------
-- Table structure for com_action_log
-- ----------------------------
DROP TABLE IF EXISTS `com_action_log`;
CREATE TABLE `com_action_log`  (
  `action_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '操作记录ID',
  `action_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作描述',
  `action_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作类型',
  `tenant_id` int(9) UNSIGNED NULL DEFAULT NULL COMMENT '租户ID',
  `object_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对象类型',
  `master_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '主操作记录ID',
  `gmt_create` datetime(0) NOT NULL COMMENT '操作时间',
  `operator_id` int(19) NULL DEFAULT NULL COMMENT '操作人ID',
  `operator_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人名称',
  `client_id` int(19) NULL DEFAULT NULL COMMENT '客户端ID',
  `remote_host` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `sign_id` int(11) NULL DEFAULT NULL COMMENT '登录记录ID',
  `object_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '对象ID',
  `failure_reason` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '不成功原因',
  PRIMARY KEY (`action_id`) USING BTREE,
  INDEX `idx_sp_act_log_operator_id`(`operator_id`) USING BTREE,
  INDEX `fk_sp_action_log_master`(`master_id`) USING BTREE,
  INDEX `fk_sp_action_log_sign`(`sign_id`) USING BTREE,
  INDEX `tenant_id`(`tenant_id`) USING BTREE,
  CONSTRAINT `fk_sp_action_log_master` FOREIGN KEY (`master_id`) REFERENCES `com_action_log` (`action_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of com_action_log
-- ----------------------------
INSERT INTO `com_action_log` VALUES (2, '新增了用户', '新增', 1, 'auth.entity.UserEntity', NULL, '2022-09-18 18:44:23', 1, 'dsf', NULL, '127.0.0.1', NULL, NULL, '11', NULL);

-- ----------------------------
-- Table structure for com_dict_entry
-- ----------------------------
DROP TABLE IF EXISTS `com_dict_entry`;
CREATE TABLE `com_dict_entry`  (
  `dict_type_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典ID',
  `dict_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据项ID',
  `dict_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据项名称',
  `dict_status` tinyint(4) NOT NULL COMMENT '状态 1.有效，2.无效',
  `sort_no` smallint(6) NULL DEFAULT NULL COMMENT '排序',
  `dict_rank` smallint(6) NULL DEFAULT NULL COMMENT '级别',
  `parent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父级数据项ID',
  `seq_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '序列',
  `dict_en_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据项英文名称',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `filter1` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性一',
  `filter2` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性二',
  `filter3` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性三',
  `filter4` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性四',
  `filter5` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性五',
  `filter6` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性六',
  `creator_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '创建者ID',
  `creator_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建者',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updater_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '更新者ID',
  `updater_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dict_type_id`, `dict_id`) USING BTREE,
  INDEX `idx_dict_entryindex`(`dict_type_id`, `dict_id`) USING BTREE,
  CONSTRAINT `com_dict_entry_ibfk_1` FOREIGN KEY (`dict_type_id`) REFERENCES `com_dict_type` (`dict_type_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据字典项' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of com_dict_entry
-- ----------------------------

-- ----------------------------
-- Table structure for com_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `com_dict_type`;
CREATE TABLE `com_dict_type`  (
  `dict_type_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典ID',
  `dict_type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典名称',
  `dict_rank` smallint(6) NULL DEFAULT NULL COMMENT '级别',
  `parent_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典父类ID',
  `seq_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '序列',
  `dict_status` tinyint(4) NOT NULL COMMENT '字典状态 1.有效，2.无效',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `sort_no` smallint(6) NULL DEFAULT NULL COMMENT '排序',
  `filter1` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性一',
  `filter2` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性二',
  `filter3` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性三',
  `filter4` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性四',
  `filter5` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性五',
  `filter6` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性六',
  `creator_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '创建者ID',
  `creator_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建者',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updater_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '更新者ID',
  `updater_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dict_type_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据字典' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of com_dict_type
-- ----------------------------

-- ----------------------------
-- Table structure for com_env_properties
-- ----------------------------
DROP TABLE IF EXISTS `com_env_properties`;
CREATE TABLE `com_env_properties`  (
  `prop_key` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置键',
  `prop_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置说明',
  `value01` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置值1',
  `value02` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置值2',
  `value03` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置值3',
  `value04` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置值4',
  `value05` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置值5',
  `creator_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '创建者ID',
  `creator_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建者',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `updater_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '更新者ID',
  `updater_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`prop_key`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '环境配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of com_env_properties
-- ----------------------------

-- ----------------------------
-- Table structure for com_sign_log
-- ----------------------------
DROP TABLE IF EXISTS `com_sign_log`;
CREATE TABLE `com_sign_log`  (
  `log_id` bigint(19) UNSIGNED NOT NULL COMMENT '记录ID',
  `session_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会话ID',
  `sign_type` tinyint(4) NOT NULL COMMENT '登录或注册 1登录 2.注册',
  `user_id` int(19) UNSIGNED NULL DEFAULT NULL COMMENT '操作人',
  `oper_date` datetime(0) NOT NULL COMMENT '操作日期',
  `is_online` tinyint(4) NULL DEFAULT NULL COMMENT '是否在线',
  `remote_host` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP',
  `protocol` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '协议',
  `is_encrypted` int(11) NULL DEFAULT NULL COMMENT '是否加密',
  `is_ajax` tinyint(4) NULL DEFAULT NULL COMMENT '是否ajax',
  `is_failed` tinyint(4) NULL DEFAULT NULL COMMENT '是否失败',
  `failure_reason` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '失败原因',
  `browser_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器名称',
  `browser_version` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器版本',
  `os_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统名称',
  `os_manufactirer_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统厂家',
  `device_type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备类型',
  `device_id01` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备ID_1',
  `device_id02` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备ID_2',
  `device_id03` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备ID_3',
  `ip_country` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP归属国家',
  `ip_region` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP归属地区',
  `ip_city` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP归属城市',
  `ip_county` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP归属区县',
  `ip_isp` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网络服务提供商',
  `scheme` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'SCHEME',
  `os_type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统类型',
  `user_type` tinyint(4) NULL DEFAULT 1 COMMENT '用户分类',
  `username` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录用户名',
  `tenant_id` int(9) NOT NULL COMMENT '租户id',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户登录日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of com_sign_log
-- ----------------------------

-- ----------------------------
-- Table structure for com_tenant
-- ----------------------------
DROP TABLE IF EXISTS `com_tenant`;
CREATE TABLE `com_tenant`  (
  `tenant_id` int(9) UNSIGNED NOT NULL COMMENT '租户id',
  `tenant_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '租户名称',
  `tenant_homepage` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '租户主页',
  PRIMARY KEY (`tenant_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '租户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of com_tenant
-- ----------------------------
INSERT INTO `com_tenant` VALUES (1, '系统', NULL);
INSERT INTO `com_tenant` VALUES (2, '长沙', NULL);
INSERT INTO `com_tenant` VALUES (3, '株洲', NULL);
INSERT INTO `com_tenant` VALUES (4, '衡阳', NULL);
INSERT INTO `com_tenant` VALUES (5, '茅草街', NULL);

SET FOREIGN_KEY_CHECKS = 1;
