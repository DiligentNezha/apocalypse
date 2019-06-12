/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : apocalypse-system

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 12/06/2019 16:10:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `mail` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '邮箱',
  `nickname` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '昵称',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除（1：已删除；0：未删除）',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for admin_admingroup_union
-- ----------------------------
DROP TABLE IF EXISTS `admin_admingroup_union`;
CREATE TABLE `admin_admingroup_union`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `admin_id` int(11) NOT NULL DEFAULT 0 COMMENT '管理员编号',
  `admingroup_id` int(11) NOT NULL DEFAULT 0 COMMENT '管理员分组编号',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '组名',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除（1：已删除；0：未删除）',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员-管理员分组关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for admin_resource_union
-- ----------------------------
DROP TABLE IF EXISTS `admin_resource_union`;
CREATE TABLE `admin_resource_union`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `admin_id` int(11) NOT NULL DEFAULT 0 COMMENT '管理员编号',
  `resource_id` int(11) NOT NULL DEFAULT 0 COMMENT '资源编号',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除（1：已删除；0：未删除）',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员资源关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for admin_role_union
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_union`;
CREATE TABLE `admin_role_union`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `admin_id` int(11) NOT NULL DEFAULT 0 COMMENT '管理员编号',
  `role_id` int(11) NOT NULL DEFAULT 0 COMMENT '角色编号',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除（1：已删除；0：未删除）',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for admingroup
-- ----------------------------
DROP TABLE IF EXISTS `admingroup`;
CREATE TABLE `admingroup`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '管理员组名',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除（1：已删除；0：未删除）',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员分组表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `pid` int(11) NOT NULL COMMENT '父级资源编号',
  `system_id` int(11) NOT NULL DEFAULT 0 COMMENT '系统编号',
  `display_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '展示名',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '资源路径',
  `component` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '前端组件',
  `type` tinyint(4) NOT NULL COMMENT '资源类型（1：菜单；2：组件；3：按钮）',
  `icon` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '图标',
  `sign` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '权限标识',
  `level` tinyint(11) NOT NULL DEFAULT 1 COMMENT '资源层级',
  `orders` int(11) NOT NULL DEFAULT 0 COMMENT '顺序',
  `require_auth` bit(1) NOT NULL DEFAULT b'1' COMMENT '需要授权',
  `visible` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否可见',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '资源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色名',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除（1：已删除；0：未删除）',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role_admingroup_union
-- ----------------------------
DROP TABLE IF EXISTS `role_admingroup_union`;
CREATE TABLE `role_admingroup_union`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role_id` int(11) NOT NULL DEFAULT 0 COMMENT '角色编号',
  `admingroup_id` int(11) NOT NULL DEFAULT 0 COMMENT '管理员分组编号',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除（1：已删除；0：未删除）',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色分组关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role_resource_union
-- ----------------------------
DROP TABLE IF EXISTS `role_resource_union`;
CREATE TABLE `role_resource_union`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role_id` int(11) NOT NULL DEFAULT 0 COMMENT '角色编号',
  `resource_id` int(11) NOT NULL DEFAULT 0 COMMENT '资源编号',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除（1：已删除；0：未删除）',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色资源关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system
-- ----------------------------
DROP TABLE IF EXISTS `system`;
CREATE TABLE `system`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '系统名',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除（1：已删除；0：未删除）',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
