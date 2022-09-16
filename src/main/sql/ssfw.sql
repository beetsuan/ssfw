/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.0.224-5.7
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : 192.168.0.224:3309
 Source Schema         : ssfw

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 16/09/2022 18:52:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for auth_group
-- ----------------------------
DROP TABLE IF EXISTS `auth_group`;
CREATE TABLE `auth_group`  (
  `group_id` bigint(19) NOT NULL COMMENT '小组id',
  `group_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '小组名称',
  `group_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '小组编号',
  `tenant_id` int(9) NOT NULL COMMENT '租户id',
  `create_user` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '修改人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`group_id`) USING BTREE,
  UNIQUE INDEX `uni_auth_group_code`(`group_code`) USING BTREE,
  INDEX `fk_auth_group_tenant_id`(`tenant_id`) USING BTREE,
  CONSTRAINT `fk_auth_group_tenant_id` FOREIGN KEY (`tenant_id`) REFERENCES `info_tenant` (`tenant_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户小组' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_group
-- ----------------------------
INSERT INTO `auth_group` VALUES (1570616152429735937, 'sddf', 'sse', 1, NULL, NULL, NULL, NULL);
INSERT INTO `auth_group` VALUES (1570638458648641537, 'sddf', 'ss3e', 1, NULL, NULL, NULL, NULL);
INSERT INTO `auth_group` VALUES (1570648207603642370, 'sddf', 'ss3g', 1, NULL, '2022-09-16 13:38:09', NULL, NULL);

-- ----------------------------
-- Table structure for auth_res
-- ----------------------------
DROP TABLE IF EXISTS `auth_res`;
CREATE TABLE `auth_res`  (
  `res_id` bigint(19) NOT NULL COMMENT '资源id',
  `res_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '资源名称',
  `res_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '资源编号',
  `res_url` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '资源访问路径',
  `menu_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单名称',
  `menu_icon` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单图标',
  `tenant_id` int(9) NOT NULL COMMENT '租户id',
  `create_user` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '修改人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`res_id`) USING BTREE,
  INDEX `fk_auth_res_tenant_id`(`tenant_id`) USING BTREE,
  UNIQUE INDEX `uni_auth_res_code`(`res_code`) USING BTREE,
  CONSTRAINT `fk_auth_res_tenant_id` FOREIGN KEY (`tenant_id`) REFERENCES `info_tenant` (`tenant_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '系统资源' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for auth_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_role`;
CREATE TABLE `auth_role`  (
  `role_id` bigint(19) NOT NULL COMMENT '角色id',
  `role_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色code',
  `role_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色名称',
  `role_desc` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '描述',
  `tenant_id` int(9) NOT NULL COMMENT '租户id',
  `create_user` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '修改人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`role_id`) USING BTREE,
  INDEX `fk_auth_role_tenant_id`(`tenant_id`) USING BTREE,
  CONSTRAINT `fk_auth_role_tenant_id` FOREIGN KEY (`tenant_id`) REFERENCES `info_tenant` (`tenant_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_role
-- ----------------------------
INSERT INTO `auth_role` VALUES (1, 'user', '用户', NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `auth_role` VALUES (2, 'admin', '管理员', NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `auth_role` VALUES (3, 'user', '用户', NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `auth_role` VALUES (4, 'admin', '管理员', NULL, 2, NULL, NULL, NULL, NULL);
INSERT INTO `auth_role` VALUES (5, 'user', '用户', NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `auth_role` VALUES (6, 'admin', '管理员', NULL, 3, NULL, NULL, NULL, NULL);
INSERT INTO `auth_role` VALUES (7, 'user', '用户', NULL, 4, NULL, NULL, NULL, NULL);
INSERT INTO `auth_role` VALUES (8, 'admin', '管理员', NULL, 4, NULL, NULL, NULL, NULL);
INSERT INTO `auth_role` VALUES (9, 'user', '用户', NULL, 5, NULL, NULL, NULL, NULL);
INSERT INTO `auth_role` VALUES (10, 'admin', '管理员', NULL, 5, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for auth_role_res
-- ----------------------------
DROP TABLE IF EXISTS `auth_role_res`;
CREATE TABLE `auth_role_res`  (
  `role_id` bigint(19) NOT NULL COMMENT '角色id',
  `res_id` bigint(19) NOT NULL COMMENT '资源id',
  `auth_date` datetime(0) NOT NULL COMMENT '授权日期',
  PRIMARY KEY (`role_id`, `res_id`) USING BTREE,
  INDEX `fk_auth_role_res_res_id`(`res_id`) USING BTREE,
  CONSTRAINT `fk_auth_role_res_res_id` FOREIGN KEY (`res_id`) REFERENCES `auth_res` (`res_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_auth_role_res_role_id` FOREIGN KEY (`role_id`) REFERENCES `auth_role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for auth_user
-- ----------------------------
DROP TABLE IF EXISTS `auth_user`;
CREATE TABLE `auth_user`  (
  `user_id` bigint(19) NOT NULL COMMENT '用户id',
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '登录名称',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
  `nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户昵称',
  `use_status` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '账号状态 valid有效 suspend挂起',
  `group_id` bigint(19) NULL DEFAULT NULL COMMENT '分组id',
  `tenant_id` int(9) NOT NULL COMMENT '租户id',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除 1.删除',
  `create_user` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '修改人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `uni_auth_user_username`(`username`, `tenant_id`) USING BTREE,
  INDEX `fk_auth_user_group_id`(`group_id`) USING BTREE,
  INDEX `fk_auth_user_tenant_id`(`tenant_id`) USING BTREE,
  CONSTRAINT `fk_auth_user_group_id` FOREIGN KEY (`group_id`) REFERENCES `auth_group` (`group_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_auth_user_tenant_id` FOREIGN KEY (`tenant_id`) REFERENCES `info_tenant` (`tenant_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_user
-- ----------------------------
INSERT INTO `auth_user` VALUES (1, 'sysadmin', '000000', '系统管理员', 'valid', NULL, 1, 0, NULL, NULL, NULL, NULL);
INSERT INTO `auth_user` VALUES (2, 'sysadmin', '000000', '系统管理员', 'valid', NULL, 2, 0, NULL, NULL, NULL, NULL);
INSERT INTO `auth_user` VALUES (1570663607091400705, 'zhangs', '123456', '张三', 'valid', NULL, 1, 0, NULL, '2022-09-16 14:39:20', NULL, NULL);
INSERT INTO `auth_user` VALUES (1570666356675784705, 'antoinette.mante', 'em35ue', 'ruben.rice', 'valid', NULL, 1, 0, NULL, '2022-09-16 14:50:16', NULL, NULL);
INSERT INTO `auth_user` VALUES (1570667405012721665, 'antoinette.mante3', 'em35ue', 'ruben.rice', 'valid', NULL, 1, 0, NULL, '2022-09-16 14:54:26', NULL, NULL);

-- ----------------------------
-- Table structure for auth_user_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_user_role`;
CREATE TABLE `auth_user_role`  (
  `user_id` bigint(19) NOT NULL COMMENT '用户id',
  `role_id` bigint(19) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户关联角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_user_role
-- ----------------------------
INSERT INTO `auth_user_role` VALUES (1, 1);
INSERT INTO `auth_user_role` VALUES (1, 3);

-- ----------------------------
-- Table structure for info_tenant
-- ----------------------------
DROP TABLE IF EXISTS `info_tenant`;
CREATE TABLE `info_tenant`  (
  `tenant_id` int(9) NOT NULL COMMENT '租户id',
  `tenant_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '租户名称',
  `tenant_homepage` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '租户主页',
  PRIMARY KEY (`tenant_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '租户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of info_tenant
-- ----------------------------
INSERT INTO `info_tenant` VALUES (1, '系统', NULL);
INSERT INTO `info_tenant` VALUES (2, '长沙', NULL);
INSERT INTO `info_tenant` VALUES (3, '株洲', NULL);
INSERT INTO `info_tenant` VALUES (4, '衡阳', NULL);
INSERT INTO `info_tenant` VALUES (5, '茅草街', NULL);

-- ----------------------------
-- Table structure for sp_action_detail
-- ----------------------------
DROP TABLE IF EXISTS `sp_action_detail`;
CREATE TABLE `sp_action_detail`  (
  `detail_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '操作明细ID',
  `action_id` int(11) NOT NULL COMMENT '操作日志ID',
  `field_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字段名称',
  `old_val` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '旧值',
  `new_val` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '新值',
  PRIMARY KEY (`detail_id`) USING BTREE,
  INDEX `fk_sp_action_detail_act`(`action_id`) USING BTREE,
  CONSTRAINT `fk_sp_action_detail_act` FOREIGN KEY (`action_id`) REFERENCES `sp_action_log` (`action_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作日志明细' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sp_action_log
-- ----------------------------
DROP TABLE IF EXISTS `sp_action_log`;
CREATE TABLE `sp_action_log`  (
  `action_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '操作记录ID',
  `action_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作描述',
  `action_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作类型',
  `tenant_id` int(9) NULL DEFAULT NULL COMMENT '租户ID',
  `object_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对象类型',
  `master_id` int(11) NULL DEFAULT NULL COMMENT '主操作记录ID',
  `oper_date` datetime(0) NOT NULL COMMENT '操作时间',
  `operator_id` int(11) NULL DEFAULT NULL COMMENT '操作人ID',
  `operator_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人名称',
  `client_id` int(11) NULL DEFAULT NULL COMMENT '客户端ID',
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
  CONSTRAINT `fk_sp_action_log_master` FOREIGN KEY (`master_id`) REFERENCES `sp_action_log` (`action_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sp_action_log
-- ----------------------------
INSERT INTO `sp_action_log` VALUES (1, '新增了用户', '新增', 1, 'auth.entity.UserEntity', NULL, '2022-09-16 14:54:26', NULL, NULL, NULL, '127.0.0.1', NULL, NULL, '1570667405012721665', NULL);

SET FOREIGN_KEY_CHECKS = 1;
