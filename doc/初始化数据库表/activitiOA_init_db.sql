-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: student_test
-- ------------------------------------------------------
-- Server version	5.7.18-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `pm_activiti_leave`
--

DROP TABLE IF EXISTS `pm_activiti_leave`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pm_activiti_leave` (
  `leave_id` int(32) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `leave_user_id` int(32) NOT NULL COMMENT '归属部门',
  `leave_date` datetime NOT NULL COMMENT '登录名',
  `leave_days` int(3) NOT NULL COMMENT '密码',
  `leave_reason` varchar(100) NOT NULL COMMENT '工号',
  `remark` varchar(100) DEFAULT NULL COMMENT '姓名',
  `leave_state` int(3) NOT NULL COMMENT '邮箱',
  `process_instance_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`leave_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1059 DEFAULT CHARSET=utf8 COMMENT='请假单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pm_activiti_leave`
--

LOCK TABLES `pm_activiti_leave` WRITE;
/*!40000 ALTER TABLE `pm_activiti_leave` DISABLE KEYS */;
/*!40000 ALTER TABLE `pm_activiti_leave` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pm_sys_area`
--

DROP TABLE IF EXISTS `pm_sys_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pm_sys_area` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `parent_id` int(32) NOT NULL COMMENT '父级编号',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `sort` decimal(10,0) NOT NULL COMMENT '排序',
  `code` varchar(100) DEFAULT NULL COMMENT '区域编码',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_area_parent_id` (`parent_id`),
  KEY `sys_area_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='区域表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pm_sys_area`
--

LOCK TABLES `pm_sys_area` WRITE;
/*!40000 ALTER TABLE `pm_sys_area` DISABLE KEYS */;
INSERT INTO `pm_sys_area` VALUES (1,0,'中国',10,'086','1','2015-07-10 08:00:00','','0');
/*!40000 ALTER TABLE `pm_sys_area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pm_sys_dept`
--

DROP TABLE IF EXISTS `pm_sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pm_sys_dept` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `parent_id` int(32) NOT NULL COMMENT '父级编号',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `sort` decimal(10,0) NOT NULL COMMENT '排序',
  `code` varchar(100) DEFAULT NULL COMMENT '区域编码',
  `address` varchar(255) DEFAULT NULL COMMENT '联系地址',
  `master` varchar(100) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(200) DEFAULT NULL COMMENT '电话',
  `fax` varchar(200) DEFAULT NULL COMMENT '传真',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_office_parent_id` (`parent_id`),
  KEY `sys_office_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='机构表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pm_sys_dept`
--

LOCK TABLES `pm_sys_dept` WRITE;
/*!40000 ALTER TABLE `pm_sys_dept` DISABLE KEYS */;
INSERT INTO `pm_sys_dept` VALUES (1,0,'xx学院',10,'100000','','','','','','1','2016-12-05 22:04:26','','0');
/*!40000 ALTER TABLE `pm_sys_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pm_sys_dict`
--

DROP TABLE IF EXISTS `pm_sys_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pm_sys_dict` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `value` varchar(100) NOT NULL COMMENT '数据值',
  `label` varchar(100) NOT NULL COMMENT '标签名',
  `type` varchar(100) NOT NULL COMMENT '类型',
  `description` varchar(100) NOT NULL COMMENT '描述',
  `sort` decimal(10,0) NOT NULL COMMENT '排序（升序）',
  `parent_id` varchar(64) DEFAULT '0' COMMENT '父级编号',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_dict_value` (`value`),
  KEY `sys_dict_label` (`label`),
  KEY `sys_dict_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8 COMMENT='字典表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pm_sys_dict`
--

LOCK TABLES `pm_sys_dict` WRITE;
/*!40000 ALTER TABLE `pm_sys_dict` DISABLE KEYS */;
INSERT INTO `pm_sys_dict` VALUES (1,'0','正常','del_flag','删除标记',10,'0','1','2015-07-10 08:00:00',NULL,'0'),(2,'1','删除','del_flag','删除标记',20,'0','1','2015-07-10 08:00:00',NULL,'0'),(3,'1','显示','show_hide','显示/隐藏',10,'0','1','2015-07-10 08:00:00',NULL,'0'),(4,'0','隐藏','show_hide','显示/隐藏',20,'0','1','2015-07-10 08:00:00',NULL,'0'),(5,'1','是','yes_no','是/否',10,'0','1','2015-07-10 08:00:00',NULL,'0'),(6,'0','否','yes_no','是/否',20,'0','1','2015-07-10 08:00:00',NULL,'0'),(17,'1','国家','sys_area_type','区域类型',10,'0','1','2015-07-10 08:00:00',NULL,'0'),(18,'2','省份、直辖市','sys_area_type','区域类型',20,'0','1','2015-07-10 08:00:00',NULL,'0'),(19,'3','地市','sys_area_type','区域类型',30,'0','1','2015-07-10 08:00:00',NULL,'0'),(20,'4','区县','sys_area_type','区域类型',40,'0','1','2015-07-10 08:00:00',NULL,'0'),(21,'1','公司','sys_office_type','机构类型',60,'0','1','2015-07-10 08:00:00',NULL,'0'),(22,'2','部门','sys_office_type','机构类型',70,'0','1','2015-07-10 08:00:00',NULL,'0'),(23,'3','小组','sys_office_type','机构类型',80,'0','1','2015-07-10 08:00:00',NULL,'0'),(24,'4','其它','sys_office_type','机构类型',90,'0','1','2015-07-10 08:00:00',NULL,'0'),(28,'1','一级','sys_office_grade','机构等级',10,'0','1','2015-07-10 08:00:00',NULL,'0'),(29,'2','二级','sys_office_grade','机构等级',20,'0','1','2015-07-10 08:00:00',NULL,'0'),(30,'3','三级','sys_office_grade','机构等级',30,'0','1','2015-07-10 08:00:00',NULL,'0'),(31,'4','四级','sys_office_grade','机构等级',40,'0','1','2015-07-10 08:00:00',NULL,'0'),(32,'1','所有数据','sys_data_scope','数据范围',10,'0','1','2015-07-10 08:00:00',NULL,'0'),(33,'2','所在公司及以下数据','sys_data_scope','数据范围',20,'0','1','2015-07-10 08:00:00',NULL,'0'),(34,'3','所在公司数据','sys_data_scope','数据范围',30,'0','1','2015-07-10 08:00:00',NULL,'0'),(35,'4','所在部门及以下数据','sys_data_scope','数据范围',40,'0','1','2015-07-10 08:00:00',NULL,'0'),(36,'5','所在部门数据','sys_data_scope','数据范围',50,'0','1','2015-07-10 08:00:00',NULL,'0'),(37,'8','仅本人数据','sys_data_scope','数据范围',90,'0','1','2015-07-10 08:00:00',NULL,'0'),(38,'9','按明细设置','sys_data_scope','数据范围',100,'0','1','2015-07-10 08:00:00',NULL,'0'),(39,'1','系统管理','sys_user_type','用户类型',10,'0','1','2015-07-10 08:00:00',NULL,'0'),(40,'2','部门经理','sys_user_type','用户类型',20,'0','1','2015-07-10 08:00:00',NULL,'0'),(41,'3','普通用户','sys_user_type','用户类型',30,'0','1','2015-07-10 08:00:00',NULL,'0'),(67,'1','操作日志','sys_log_type','日志类型',30,'0','1','2015-07-10 08:00:00',NULL,'0'),(68,'2','异常日志','sys_log_type','日志类型',40,'0','1','2015-07-10 08:00:00',NULL,'0'),(96,'1','男','sex','性别',10,'0','1','2015-07-10 08:00:00',NULL,'0'),(97,'2','女','sex','性别',20,'0','1','2015-07-10 08:00:00',NULL,'0');
/*!40000 ALTER TABLE `pm_sys_dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pm_sys_menu`
--

DROP TABLE IF EXISTS `pm_sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pm_sys_menu` (
  `id` int(64) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `parent_id` int(64) NOT NULL COMMENT '父级编号',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `sort` decimal(10,0) NOT NULL COMMENT '排序',
  `href` varchar(2000) DEFAULT NULL COMMENT '链接',
  `target` varchar(20) DEFAULT NULL COMMENT '目标',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `is_show` char(1) NOT NULL COMMENT '是否在菜单中显示',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限标识',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_menu_parent_id` (`parent_id`),
  KEY `sys_menu_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8 COMMENT='菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pm_sys_menu`
--

LOCK TABLES `pm_sys_menu` WRITE;
/*!40000 ALTER TABLE `pm_sys_menu` DISABLE KEYS */;
INSERT INTO `pm_sys_menu` VALUES (1,0,'功能菜单',10,'','','','1','','1','2017-04-20 16:52:01','','0'),(3,1,'系统设置',20,'','','','1','','1','2017-04-21 16:25:00','','0'),(4,3,'菜单管理',10,'/sysmg/menu/gotoMenuList',NULL,'list-alt','1',NULL,'1','2015-07-10 08:00:00',NULL,'0'),(7,3,'角色管理',20,'/sysmg/role/gotoRoleList','','lock','1','','1','2016-11-29 17:09:58','','0'),(10,3,'字典管理',30,'/sysmg/dict/gotoDictList','','th-list','1',NULL,'keven','2018-02-01 17:24:39','','0'),(14,3,'区域管理',30,'/sysmg/area/gotoAreaList','','th','1','','1','2017-04-21 16:29:17','','0'),(17,3,'部门管理',20,'/sysmg/dept/gotoDeptList','','th-large','1','','1','2017-04-21 16:29:52','','0'),(20,3,'用户管理',10,'/sysmg/user/gotoUserList','','user','1','','1','2017-04-21 16:30:09','','0'),(28,1,'个人信息管理',10,'','',NULL,'1',NULL,'1','2017-04-21 16:31:44','','0'),(29,28,'个人信息',20,'/sysmg/user/gotoUserInfo','','user','1',NULL,'1','2017-04-21 16:31:25','','0'),(30,28,'修改密码',30,'/sysmg/user/gotoChangePwd','','lock','1','','1','2017-04-21 16:32:12','','0'),(45,1,'流程管理',10,'','',NULL,'1',NULL,'1','2018-02-08 18:16:41','','0'),(46,45,'流程定义',10,'/activiti/processDefinition/gotoProcessDefinitionList','',NULL,'1',NULL,'1','2018-02-09 09:06:07','','0'),(47,45,'流程部署',5,'/activiti/processDeploy/gotoProcessDeployList','',NULL,'1',NULL,'1','2018-02-09 09:05:57','','0'),(48,1,'任务管理',10,'','',NULL,'1',NULL,'1','2018-02-09 10:37:09','','0'),(49,48,'请假申请',10,'/activiti/leaveProcess/gotoLeaveProcessList','',NULL,'1',NULL,'1','2018-02-09 10:37:25','','0'),(50,48,'任务处理',20,'/activiti/processTask/gotoProcessTaskList','',NULL,'1',NULL,'1','2018-02-10 11:12:42','','0');
/*!40000 ALTER TABLE `pm_sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pm_sys_role`
--

DROP TABLE IF EXISTS `pm_sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pm_sys_role` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_role_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pm_sys_role`
--

LOCK TABLES `pm_sys_role` WRITE;
/*!40000 ALTER TABLE `pm_sys_role` DISABLE KEYS */;
INSERT INTO `pm_sys_role` VALUES (1,'超级系统管理员','1','2018-02-06 17:34:23','','0'),(2,'部门主管','1','2018-02-22 21:16:50','','0'),(3,'部门经理','1','2018-02-22 21:20:27','','0');
/*!40000 ALTER TABLE `pm_sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pm_sys_role_area`
--

DROP TABLE IF EXISTS `pm_sys_role_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pm_sys_role_area` (
  `role_id` int(32) NOT NULL COMMENT '角色编号',
  `area_id` int(32) NOT NULL COMMENT '菜单编号',
  PRIMARY KEY (`role_id`,`area_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-菜单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pm_sys_role_area`
--

LOCK TABLES `pm_sys_role_area` WRITE;
/*!40000 ALTER TABLE `pm_sys_role_area` DISABLE KEYS */;
INSERT INTO `pm_sys_role_area` VALUES (1,1),(2,1),(2,2),(3,1);
/*!40000 ALTER TABLE `pm_sys_role_area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pm_sys_role_dept`
--

DROP TABLE IF EXISTS `pm_sys_role_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pm_sys_role_dept` (
  `role_id` int(32) NOT NULL COMMENT '角色编号',
  `dept_id` int(32) NOT NULL COMMENT '菜单编号',
  PRIMARY KEY (`role_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-菜单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pm_sys_role_dept`
--

LOCK TABLES `pm_sys_role_dept` WRITE;
/*!40000 ALTER TABLE `pm_sys_role_dept` DISABLE KEYS */;
INSERT INTO `pm_sys_role_dept` VALUES (1,1),(2,1),(2,2),(3,1);
/*!40000 ALTER TABLE `pm_sys_role_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pm_sys_role_menu`
--

DROP TABLE IF EXISTS `pm_sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pm_sys_role_menu` (
  `role_id` int(32) NOT NULL COMMENT '角色编号',
  `menu_id` int(32) NOT NULL COMMENT '菜单编号',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-菜单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pm_sys_role_menu`
--

LOCK TABLES `pm_sys_role_menu` WRITE;
/*!40000 ALTER TABLE `pm_sys_role_menu` DISABLE KEYS */;
INSERT INTO `pm_sys_role_menu` VALUES (1,1),(1,3),(1,4),(1,7),(1,10),(1,14),(1,17),(1,20),(1,28),(1,29),(1,30),(1,45),(1,46),(1,47),(1,48),(1,49),(1,50),(2,1),(2,2),(2,28),(2,29),(2,30),(2,48),(2,49),(2,50),(3,1),(3,28),(3,29),(3,30),(3,45),(3,46),(3,47),(3,48),(3,49),(3,50);
/*!40000 ALTER TABLE `pm_sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pm_sys_user`
--

DROP TABLE IF EXISTS `pm_sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pm_sys_user` (
  `user_id` int(32) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `dept_id` int(32) NOT NULL COMMENT '归属部门',
  `login_name` varchar(100) NOT NULL COMMENT '登录名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `user_no` varchar(100) DEFAULT NULL COMMENT '工号',
  `user_name` varchar(100) NOT NULL COMMENT '姓名',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(200) DEFAULT NULL COMMENT '电话',
  `mobile` varchar(200) DEFAULT NULL COMMENT '手机',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`user_id`),
  KEY `sys_user_office_id` (`dept_id`),
  KEY `sys_user_login_name` (`login_name`),
  KEY `sys_user_update_date` (`update_date`),
  KEY `sys_user_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pm_sys_user`
--

LOCK TABLES `pm_sys_user` WRITE;
/*!40000 ALTER TABLE `pm_sys_user` DISABLE KEYS */;
INSERT INTO `pm_sys_user` VALUES (1,1,'admin','24e3829ffe5522e801fd4c80816e7331e33445ee3ed510f1fefa5592','0001','keven','tz4@tanzhouedu.com','12345','45645','1','2016-12-02 15:58:08','78945','0'),(2,1,'test1','2f21bd8efb614783c630bf7cbcf0003477c54f2c834f19ac081f5e14','1000','张三','','','','1','2018-02-22 21:20:09','','0'),(3,1,'test2','5c46c47a6b79bcba52dedcccdd2240f5ceb30ad67abc48fe9736dc7a','1001','李四','','','','1','2018-02-22 21:30:39','','0');
/*!40000 ALTER TABLE `pm_sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pm_sys_user_role`
--

DROP TABLE IF EXISTS `pm_sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pm_sys_user_role` (
  `user_id` int(32) NOT NULL COMMENT '用户编号',
  `role_id` int(32) NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pm_sys_user_role`
--

LOCK TABLES `pm_sys_user_role` WRITE;
/*!40000 ALTER TABLE `pm_sys_user_role` DISABLE KEYS */;
INSERT INTO `pm_sys_user_role` VALUES (1,1),(2,2),(3,3);
/*!40000 ALTER TABLE `pm_sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-23 14:41:08
