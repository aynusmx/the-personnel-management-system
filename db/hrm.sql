/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50543
Source Host           : localhost:3306
Source Database       : hrm

Target Server Type    : MYSQL
Target Server Version : 50543
File Encoding         : 65001

Date: 2020-07-10 11:31:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dept_inf
-- ----------------------------
DROP TABLE IF EXISTS `dept_inf`;
CREATE TABLE `dept_inf` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `REMARK` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dept_inf
-- ----------------------------
INSERT INTO `dept_inf` VALUES ('1', '技术处', '技术部');
INSERT INTO `dept_inf` VALUES ('2', '运营部', '运营部');
INSERT INTO `dept_inf` VALUES ('3', '财务部', '财务部');
INSERT INTO `dept_inf` VALUES ('5', '总公办', '总公办');

-- ----------------------------
-- Table structure for document_inf
-- ----------------------------
DROP TABLE IF EXISTS `document_inf`;
CREATE TABLE `document_inf` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(50) NOT NULL,
  `filename` varchar(300) NOT NULL,
  `REMARK` varchar(300) DEFAULT NULL,
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `USER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_DOCUMENT_USER` (`USER_ID`),
  CONSTRAINT `document_inf_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `user_inf` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of document_inf
-- ----------------------------
INSERT INTO `document_inf` VALUES ('1', '大法师', '打', '发的', '2020-03-23 09:25:04', '1');
INSERT INTO `document_inf` VALUES ('5', '按规定发的', ' 爱国福', ' 发过的', '2020-03-23 09:25:42', '10');
INSERT INTO `document_inf` VALUES ('6', '图片1', '1585479897992_QQ截图20180316165437.png', '图片图片', '2020-03-23 11:39:21', '1');
INSERT INTO `document_inf` VALUES ('7', '图片1', '5a2fa21f00d3b.jpg', '团票图片', '2020-03-23 12:01:09', '1');
INSERT INTO `document_inf` VALUES ('8', '图片2', '1584936423846_5a34e408138bb.jpg', '图片图片', '2020-03-23 12:07:03', '1');
INSERT INTO `document_inf` VALUES ('9', '图片3', '1585306043791_5a34e408138bb.jpg', '好图片', '2020-03-27 18:47:23', '1');
INSERT INTO `document_inf` VALUES ('10', 'picter4', '1585306192840_32a17eea15ce36d3976e5b0931f33a87e950b1b2.jpg', 'lalalal', '2020-03-27 18:49:52', '1');
INSERT INTO `document_inf` VALUES ('11', '乱码01', '1585484879781_下载文件乱码.txt', '乱码1', '2020-03-29 20:27:59', '1');
INSERT INTO `document_inf` VALUES ('12', '嗯嗯嗯嗯', '1585533472606_下载文件乱码.txt', '嗯', '2020-03-30 09:57:52', '1');

-- ----------------------------
-- Table structure for employee_inf
-- ----------------------------
DROP TABLE IF EXISTS `employee_inf`;
CREATE TABLE `employee_inf` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `DEPT_ID` int(11) NOT NULL,
  `JOB_ID` int(11) NOT NULL,
  `NAME` varchar(20) NOT NULL,
  `CARD_ID` varchar(18) NOT NULL,
  `ADDRESS` varchar(50) NOT NULL,
  `POST_CODE` varchar(50) DEFAULT NULL,
  `TEL` varchar(16) DEFAULT NULL,
  `PHONE` varchar(11) NOT NULL,
  `QQ_NUM` varchar(10) DEFAULT NULL,
  `EMAIL` varchar(50) NOT NULL,
  `SEX` int(11) NOT NULL DEFAULT '1',
  `PARTY` varchar(10) DEFAULT NULL,
  `BIRTHDAY` datetime DEFAULT NULL,
  `RACE` varchar(100) DEFAULT NULL,
  `EDUCATION` varchar(10) DEFAULT NULL,
  `SPECIALITY` varchar(20) DEFAULT NULL,
  `HOBBY` varchar(100) DEFAULT NULL,
  `REMARK` varchar(500) DEFAULT NULL,
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `FK_EMP_DEPT` (`DEPT_ID`),
  KEY `FK_EMP_JOB` (`JOB_ID`),
  CONSTRAINT `employee_inf_ibfk_1` FOREIGN KEY (`DEPT_ID`) REFERENCES `dept_inf` (`ID`),
  CONSTRAINT `employee_inf_ibfk_2` FOREIGN KEY (`JOB_ID`) REFERENCES `job_inf` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of employee_inf
-- ----------------------------
INSERT INTO `employee_inf` VALUES ('1', '1', '8', '爱丽丝', '4328011988', '广州天河', '510000', '020-77777777', '13902001111', '36750066', '251425887@qq.com', '2', '党员', '1980-01-01 00:00:00', '满', '本科', '美声', '唱歌', '四大天王', '2016-03-14 11:35:18');
INSERT INTO `employee_inf` VALUES ('2', '2', '1', '杰克', '22623', '43234', '42427424', '42242', '4247242', '42424', '251425887@qq.com', '2', null, null, null, null, null, null, null, '2016-03-14 11:35:18');
INSERT INTO `employee_inf` VALUES ('3', '1', '2', 'bb', '432801197711251038', '广州', '510000', '020-99999999', '13907351532', '36750064', '36750064@qq.com', '1', '党员', '1977-11-25 00:00:00', '汉', '本科', '计算机', '爬山', '无', '2016-07-14 09:54:52');
INSERT INTO `employee_inf` VALUES ('6', '5', '9', '苏明心', '401481199999999999', '河南舞钢', '462500', '020-99999999', '13789358099', '1378935809', '123@qq.com', '1', '团员', '2020-03-16 00:00:00', '汉', '本科', '软件工程', '电脑', '', '2020-03-16 13:25:02');
INSERT INTO `employee_inf` VALUES ('7', '2', '4', '苏明美', '410481199905162000', '河南舞阳', '462600', '020-99999999', '13789358000', '1378935809', '135@qq.com', '2', '党员', '2020-03-12 00:00:00', '汉', '本科', '软件工程', '电脑', '', '2020-03-16 13:34:28');

-- ----------------------------
-- Table structure for job_inf
-- ----------------------------
DROP TABLE IF EXISTS `job_inf`;
CREATE TABLE `job_inf` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `REMARK` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of job_inf
-- ----------------------------
INSERT INTO `job_inf` VALUES ('1', '职员', '职员');
INSERT INTO `job_inf` VALUES ('2', 'Java开发工程师', 'Java开发工程师');
INSERT INTO `job_inf` VALUES ('3', 'Java中级开发工程师', 'Java中级开发工程师');
INSERT INTO `job_inf` VALUES ('4', 'Java高级开发工程师', 'Java高级开发工程师');
INSERT INTO `job_inf` VALUES ('5', '系统管理员', '系统管理员');
INSERT INTO `job_inf` VALUES ('6', '架构师', '架构师');
INSERT INTO `job_inf` VALUES ('7', '主管', '主管');
INSERT INTO `job_inf` VALUES ('8', '大堂经理', '大堂经理');
INSERT INTO `job_inf` VALUES ('9', '总经理', '总经理');
INSERT INTO `job_inf` VALUES ('11', '老教师', '老教师');

-- ----------------------------
-- Table structure for notice_inf
-- ----------------------------
DROP TABLE IF EXISTS `notice_inf`;
CREATE TABLE `notice_inf` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(50) NOT NULL,
  `CONTENT` text NOT NULL,
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `USER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_NOTICE_USER` (`USER_ID`),
  CONSTRAINT `notice_inf_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `user_inf` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of notice_inf
-- ----------------------------
INSERT INTO `notice_inf` VALUES ('1', '公告1', '疫情严重，推迟开学', '2020-02-25 10:30:12', '10');
INSERT INTO `notice_inf` VALUES ('2', '公告02', '到底要不要', '2020-03-22 20:03:54', '1');
INSERT INTO `notice_inf` VALUES ('3', '公告3', '好像开学', '2020-03-22 20:04:08', '1');
INSERT INTO `notice_inf` VALUES ('4', '公告4', '开不开心', '2020-03-22 20:04:21', '10');
INSERT INTO `notice_inf` VALUES ('5', '植树节', '大家植树', '2020-03-22 20:04:37', '1');
INSERT INTO `notice_inf` VALUES ('6', '端午节', '端午节不放假三倍工资', '2020-03-22 20:04:54', '1');
INSERT INTO `notice_inf` VALUES ('8', '公告03', '只是一条公告', '2020-03-22 22:20:55', '1');
INSERT INTO `notice_inf` VALUES ('9', '公告04', '这又只是一条公告', '2020-03-22 22:25:25', '1');
INSERT INTO `notice_inf` VALUES ('10', '公告05', '这又双叒叕是一条公告', '2020-03-22 22:26:30', '1');
INSERT INTO `notice_inf` VALUES ('11', '对对对', '好好好', '2020-03-27 18:46:24', '1');

-- ----------------------------
-- Table structure for user_inf
-- ----------------------------
DROP TABLE IF EXISTS `user_inf`;
CREATE TABLE `user_inf` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `loginname` varchar(20) NOT NULL,
  `PASSWORD` varchar(16) NOT NULL,
  `STATUS` int(11) NOT NULL DEFAULT '1',
  `createdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `username` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_inf
-- ----------------------------
INSERT INTO `user_inf` VALUES ('1', 'admin', '123456', '2', '2016-03-12 09:34:28', '超级管理员');
INSERT INTO `user_inf` VALUES ('2', 'lwx', '123456', '1', '2020-02-11 10:56:16', '普通用户');
INSERT INTO `user_inf` VALUES ('3', 'theShy', '123456', '1', '2020-02-11 10:56:35', '羞男');
INSERT INTO `user_inf` VALUES ('4', 'jkl', '123456', '2', '2020-02-25 10:57:17', '超级管理员');
INSERT INTO `user_inf` VALUES ('10', 'yenaifa', '123456', '1', '2020-02-25 10:27:01', '叶奈法');
INSERT INTO `user_inf` VALUES ('11', 'dadadada', '123456', '1', '2020-02-25 10:58:41', '大大');
INSERT INTO `user_inf` VALUES ('14', 'aaaaaa', '123456', '1', '2020-03-02 09:47:39', '小明');
