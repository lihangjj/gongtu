/*
Navicat MySQL Data Transfer

Source Server         : qlm
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : gongtu

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-02-26 06:34:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `accountid` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `bank` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`accountid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('2121212121212122', '波多野结衣', '中国建设银行');
INSERT INTO `account` VALUES ('342523452345', '樱井莉香', '中国邮政银行');
INSERT INTO `account` VALUES ('6228480344320460961', '刘德华', '招商银行');
INSERT INTO `account` VALUES ('6228480472320460961', '苍老师', '交通银行');
INSERT INTO `account` VALUES ('88888888748', '松岛枫', '中国邮政银行');

-- ----------------------------
-- Table structure for action
-- ----------------------------
DROP TABLE IF EXISTS `action`;
CREATE TABLE `action` (
  `title` varchar(50) DEFAULT NULL,
  `flag` varchar(50) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `sflag` int(11) DEFAULT '0' COMMENT '1为后台列表显示，0是不显示',
  `actionid` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`actionid`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of action
-- ----------------------------
INSERT INTO `action` VALUES ('添加员工', 'member:add', '/pages/back/member/add', '0', '1');
INSERT INTO `action` VALUES ('添加员工', 'member:addPre', '/pages/back/member/addPre', '1', '2');
INSERT INTO `action` VALUES ('员工列表', 'member:list', '/pages/back/member/list', '1', '3');
INSERT INTO `action` VALUES ('密码修改', 'member:editPasswordPre', '/pages/back/member/editPre', '1', '4');
INSERT INTO `action` VALUES ('锁定或激活', 'member:lockOrUnlock', '/pages/back/member/lockOrUnlock', '0', '5');
INSERT INTO `action` VALUES ('修改用户', 'member:editMember', '/pages/back/member/editMember', '0', '6');
INSERT INTO `action` VALUES ('背景风格', 'system:setStylePre', '/pages/back/system/setStylePre', '1', '7');
INSERT INTO `action` VALUES ('背景风格', 'system:setStyle', '/pages/back/system/setStyle', '0', '8');
INSERT INTO `action` VALUES ('字体风格', 'system:setFontPre', '/pages/back/system/setFontPre', '1', '9');
INSERT INTO `action` VALUES ('字体风格', 'system:setFont', '/pages/back/system/setFont', '0', '10');
INSERT INTO `action` VALUES ('增加权限', 'action:add', '/pages/back/action/add', '0', '11');
INSERT INTO `action` VALUES ('权限列表', 'action:list', '/pages/back/action/list', '1', '12');
INSERT INTO `action` VALUES ('权限修改', 'action:edit', '/pages/back/action/edit', '0', '13');
INSERT INTO `action` VALUES ('角色列表', 'role:list', '/pages/back/role/list', '1', '14');
INSERT INTO `action` VALUES ('增加角色', 'role:add', '/pages/back/role/add', '0', '15');
INSERT INTO `action` VALUES ('修改角色', 'role:edit', '/pages/back/role/edit', '0', '16');
INSERT INTO `action` VALUES ('增加部门', 'dept:add', '/pages/back/dept/add', '0', '17');
INSERT INTO `action` VALUES ('部门增加', 'dept:addPre', '/pages/back/dept/addPre', '1', '18');
INSERT INTO `action` VALUES ('部门列表', 'dept:list', '/pages/back/dept/list', '1', '19');
INSERT INTO `action` VALUES ('部门修改', 'dept:edit', '/pages/back/dept/edit', '0', '20');
INSERT INTO `action` VALUES ('部门删除', 'dept:dele', '/pages/back/dept/dele', '0', '21');
INSERT INTO `action` VALUES ('职位增加', 'job:addPre', '/pages/back/job/addPre', '1', '22');
INSERT INTO `action` VALUES ('职位列表', 'job:list', '/pages/back/job/list', '1', '23');
INSERT INTO `action` VALUES ('职位修改', 'job:edit', '/pages/back/job/edit', '0', '24');
INSERT INTO `action` VALUES ('职位删除', 'job:delete', '/pages/back/job/delete', '0', '25');
INSERT INTO `action` VALUES ('添加合同', 'contract:addPre', '/pages/back/contract/addPre', '1', '26');
INSERT INTO `action` VALUES ('添加合同', 'contract:add', '/pages/back/contract/add', '0', '27');
INSERT INTO `action` VALUES ('客户列表', 'contract:list', '/pages/back/contract/list', '1', '28');
INSERT INTO `action` VALUES ('合同列表', 'contract:list', '/pages/back/contract/clist', '1', '29');
INSERT INTO `action` VALUES ('修改合同', 'contract:edit', '/pages/back/contract/edit', '0', '30');
INSERT INTO `action` VALUES ('公司账户', 'account:list', '/pages/back/finance/accountList', '1', '31');
INSERT INTO `action` VALUES ('删除合同', 'contract:delete', '/pages/back/contract/plDeleteContract', '0', '32');
INSERT INTO `action` VALUES ('项目列表', 'project:list', '/pages/back/project/list', '1', '33');
INSERT INTO `action` VALUES ('添加日志', 'log:addPre', '/pages/back/log/addPre', '1', '34');
INSERT INTO `action` VALUES ('添加日志', 'log:add', '/pages/back/log/add', '0', '35');
INSERT INTO `action` VALUES ('日志列表', 'log:list', '/pages/back/log/list', '1', '36');
INSERT INTO `action` VALUES ('修改日志', 'log:editPre', '/pages/back/log/editPre', '0', '37');
INSERT INTO `action` VALUES ('修改日志', 'log:edit', '/pages/back/log/edit', '0', '38');
INSERT INTO `action` VALUES ('删除日志', 'log:delete', '/pages/back/log/plDeleteLog', '0', '39');
INSERT INTO `action` VALUES ('公司成本', 'finance:cost', '/pages/back/finance/costList', '1', '40');
INSERT INTO `action` VALUES ('新增成本', 'finance:costAddPre', '/pages/back/finance/costAddPre', '1', '41');
INSERT INTO `action` VALUES ('我的消息', 'message:mList', '/pages/back/message/mlist', '1', '42');
INSERT INTO `action` VALUES ('发送消息', 'message:addPre', '/pages/back/message/addPre', '1', '43');
INSERT INTO `action` VALUES ('发送消息', 'message:add', '/pages/back/message/add', '0', '44');
INSERT INTO `action` VALUES ('收到消息', 'message:list', '/pages/back/message/list', '1', '45');

-- ----------------------------
-- Table structure for client
-- ----------------------------
DROP TABLE IF EXISTS `client`;
CREATE TABLE `client` (
  `clientid` int(11) NOT NULL AUTO_INCREMENT,
  `claim` text,
  `phone` varchar(15) DEFAULT NULL,
  `budget` varchar(10) DEFAULT NULL,
  `name` varchar(10) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `company` varchar(50) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `pubdate` datetime DEFAULT NULL,
  `dealFlag` int(11) DEFAULT '0',
  PRIMARY KEY (`clientid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of client
-- ----------------------------
INSERT INTO `client` VALUES ('1', '约会神器', '1', '20', '大姐', '去', '1', '1', '2018-01-16 08:21:25', '1');
INSERT INTO `client` VALUES ('2', '开发一个聊天交友的程序', '18883873212', '10万~30万', '大哥', '渝北', '猪八戒网络', '猪八戒', '2018-01-16 08:22:05', '1');
INSERT INTO `client` VALUES ('3', '开发一个酒店管理系统', '18883873212', '10万~30万', '王麻子', '渝北', '猪八戒网络', '猪八戒', '2018-01-16 08:24:44', '1');
INSERT INTO `client` VALUES ('4', '开发卖衣服的商城', '18883873212', '10万~30', '李四', '渝北', '猪八戒网络', '猪八戒', '2018-01-16 08:32:49', '1');
INSERT INTO `client` VALUES ('5', '开发一款租车APP', '18883873212', '10万~30万', '张三', '渝北带我去', '猪八戒网络带我去', '猪八戒带我去', '2018-01-16 08:33:52', '1');
INSERT INTO `client` VALUES ('6', 'vhyvgvh', '18883873123', '不确定', null, null, null, null, '2018-01-21 11:20:57', '0');
INSERT INTO `client` VALUES ('7', '谭佳佳', '18223170162', '项目预算', null, null, null, null, '2018-01-21 15:05:38', '0');
INSERT INTO `client` VALUES ('8', ' 要做个手机APP', '17783370717', '不确定', '李虎', 'null', 'null', 'null', '2018-01-22 01:05:58', '1');

-- ----------------------------
-- Table structure for contract
-- ----------------------------
DROP TABLE IF EXISTS `contract`;
CREATE TABLE `contract` (
  `contractid` int(11) NOT NULL AUTO_INCREMENT,
  `companyName` varchar(255) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `qq` varchar(255) DEFAULT NULL,
  `principal` varchar(255) DEFAULT NULL COMMENT '负责人',
  `principalPhone` varchar(255) DEFAULT NULL,
  `principalQQ` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `note` text,
  `signingDate` date DEFAULT NULL COMMENT '签订时间',
  `period` int(11) DEFAULT NULL COMMENT '签订周期多少月',
  `expireDate` date DEFAULT NULL COMMENT '到期时间',
  `status` varchar(255) DEFAULT NULL,
  `allCost` int(11) DEFAULT NULL,
  `alreadyPay` int(11) DEFAULT '0',
  `dflag` int(11) DEFAULT '0' COMMENT '1是删除',
  PRIMARY KEY (`contractid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of contract
-- ----------------------------
INSERT INTO `contract` VALUES ('1', '鸳鸯园林', '徐冰', '13896006088', '', '黎顺明', '18223170162', '237377116', '重庆渝北鸳鸯', '这是测试数据（实际中填写真实的备注信息）', '2018-02-06', '12', '2019-02-06', '完结', '381000', '381000', '0');
INSERT INTO `contract` VALUES ('2', '通讯电力', '刘杨梅', '15922969149', '1146912891', '陈云祥', '13908379123', '9192456', '重庆渝北双龙', '通讯电力的说明', '2018-02-06', '12', '2019-02-06', '完结', '2010000', '380012', '0');
INSERT INTO `contract` VALUES ('3', '水里公司', '彭榴', '15823020752', '9427415', '杨宏强', '13883290312', '', '', '水里公司的说明', '2018-02-06', '6', '2018-08-06', '进行中', '26150', '12445', '0');
INSERT INTO `contract` VALUES ('5', '南方铁道部', '', '', '', '', '', '', '', '', '2018-03-09', '36', '2021-03-09', '进行中', '122300', '22300', '0');
INSERT INTO `contract` VALUES ('6', '长江航道2018', '方法', '', '', '', '', '', '', '', '2018-02-22', '24', '2020-02-22', '进行中', '10000', '5000', '0');
INSERT INTO `contract` VALUES ('7', '长江航道', '', '', '', '', '', '', '', '', null, null, null, '完结', '153168', '0', '0');

-- ----------------------------
-- Table structure for cost
-- ----------------------------
DROP TABLE IF EXISTS `cost`;
CREATE TABLE `cost` (
  `costid` double NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `heji` double DEFAULT '0',
  `fangzu` double DEFAULT '0' COMMENT '是谁花的钱',
  `shui` double DEFAULT '0',
  `dian` double DEFAULT '0',
  `qiche` double DEFAULT '0',
  `canyin` double DEFAULT '0',
  `haocai` double DEFAULT '0',
  `shebei` double DEFAULT '0',
  `tuiguang` double DEFAULT '0',
  `dflag` int(11) DEFAULT '0',
  PRIMARY KEY (`costid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cost
-- ----------------------------
INSERT INTO `cost` VALUES ('1', '2018-01-25', '21.28', '2.66', '2.66', '2.66', '2.66', '2.66', '2.66', '2.66', '2.66', '1');
INSERT INTO `cost` VALUES ('2', '2018-02-25', '18.88', '2.36', '2.36', '2.36', '2.36', '2.36', '2.36', '2.36', '2.36', '1');
INSERT INTO `cost` VALUES ('3', '2018-03-25', '24', '3', '3', '3', '3', '3', '3', '3', '3', '0');
INSERT INTO `cost` VALUES ('4', '2018-04-25', '1588.8', '198.6', '198.6', '198.6', '198.6', '198.6', '198.6', '198.6', '198.6', '0');
INSERT INTO `cost` VALUES ('5', '2018-02-25', '12452.8', '1556.6', '1556.6', '1556.6', '1556.6', '1556.6', '1556.6', '1556.6', '1556.6', '0');
INSERT INTO `cost` VALUES ('6', '2018-02-25', '3687.8', '2.3', '526.5', '526.5', '526.5', '526.5', '526.5', '526.5', '526.5', '0');
INSERT INTO `cost` VALUES ('7', '2018-02-25', '4212', '526.5', '526.5', '526.5', '526.5', '526.5', '526.5', '526.5', '526.5', '0');
INSERT INTO `cost` VALUES ('8', '2018-02-25', '4212', '526.5', '526.5', '526.5', '526.5', '526.5', '526.5', '526.5', '526.5', '0');
INSERT INTO `cost` VALUES ('9', '2018-02-25', '4212', '526.5', '526.5', '526.5', '526.5', '526.5', '526.5', '526.5', '526.5', '0');
INSERT INTO `cost` VALUES ('10', '2018-02-25', '4212', '526.5', '526.5', '526.5', '526.5', '526.5', '526.5', '526.5', '526.5', '0');
INSERT INTO `cost` VALUES ('11', '2018-02-25', '4212', '526.5', '526.5', '526.5', '526.5', '526.5', '526.5', '526.5', '526.5', '0');
INSERT INTO `cost` VALUES ('12', '2018-02-25', '215', '215', '0', '0', '0', '0', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `deptid` int(11) NOT NULL AUTO_INCREMENT,
  `dname` varchar(255) DEFAULT NULL,
  `renshu` int(11) DEFAULT '0',
  PRIMARY KEY (`deptid`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dept
-- ----------------------------
INSERT INTO `dept` VALUES ('1', '人才部', '0');
INSERT INTO `dept` VALUES ('2', '项目部', '0');
INSERT INTO `dept` VALUES ('21', '总经办', '0');

-- ----------------------------
-- Table structure for job
-- ----------------------------
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job` (
  `jobid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `deptid` int(11) DEFAULT NULL,
  PRIMARY KEY (`jobid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of job
-- ----------------------------
INSERT INTO `job` VALUES ('6', '人才主管', '1');
INSERT INTO `job` VALUES ('7', '人才专员', '1');
INSERT INTO `job` VALUES ('12', '项目主管', '2');
INSERT INTO `job` VALUES ('13', '项目专员', '2');
INSERT INTO `job` VALUES ('14', '总经理', '21');
INSERT INTO `job` VALUES ('15', '部门经理', '21');

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `logid` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL,
  `projectid` int(11) DEFAULT NULL,
  `note` text,
  `dflag` int(11) DEFAULT '0',
  `coordination` varchar(255) DEFAULT '是' COMMENT '是否协调',
  `time` datetime DEFAULT NULL,
  `memberid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`logid`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES ('1', '实施', '1', '日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1', '0', '是', '2011-01-21 19:15:25', 'lh');
INSERT INTO `log` VALUES ('2', '配合', '1', '日志1日哈哈哈志1日志1日志1日志1日志1日志你号', '0', '是', '2018-02-24 19:16:10', 'lh');
INSERT INTO `log` VALUES ('3', '其他', null, '日志1日志1日志1日志1日志1日1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1', '0', '是', '2018-02-20 19:16:16', 'lh');
INSERT INTO `log` VALUES ('4', '实施', '1', '2655日志1日志1日志1日志1日志1日1日志1日志1日志1', '0', '是', '2018-02-21 19:17:47', 'lh');
INSERT INTO `log` VALUES ('5', '实施', '1', '大无畏大无畏大无畏大无畏大无畏大无畏大无畏大无畏大无畏大无畏大无畏大无畏大无畏大无畏大无大无畏大无畏', '0', '是', '2018-02-21 19:17:51', 'lh');
INSERT INTO `log` VALUES ('6', '实施', '1', '的我w日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1', '0', '是', '2018-01-21 19:17:53', 'lh');
INSERT INTO `log` VALUES ('9', '实施', '1', 'refer日志1日志1日志1日志1日志1日志1', '0', '是', '2018-02-21 19:18:04', 'lh');
INSERT INTO `log` VALUES ('10', '实施', '1', '份额refer日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1', '0', '是', '2018-02-21 19:18:06', 'lh');
INSERT INTO `log` VALUES ('11', '实施', '1', 'few人分为氛围发范文芳我分为氛围范文芳为非我方的问富翁方法为', '0', '是', '2018-02-21 19:18:14', 'lh');
INSERT INTO `log` VALUES ('12', '实施', '1', '来u一个试试refer日志1日志1日志1日志志1', '0', '是', '2018-02-21 23:16:19', 'lh');
INSERT INTO `log` VALUES ('13', '实施', '1', '问富翁，信赖的r志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1', '0', '是', '2018-02-21 23:35:26', 'lh');
INSERT INTO `log` VALUES ('14', '实施', '1', '问的refer日志1日志1日日志1日志1日志1日志1日志1', '0', '是', '2018-02-21 23:36:22', 'lh');
INSERT INTO `log` VALUES ('15', '实施', '1', '问的，再来一个refer日志1日地方撒旦决赛对1日志1日志1日志1日志1日志1日志1', '0', '是', '2018-02-21 23:40:21', 'lh');
INSERT INTO `log` VALUES ('16', '实施', '1', '号的，砸来refer1日志1日志1日志1腊月说大话你塞', '0', '是', '2018-02-21 23:40:49', 'lh');
INSERT INTO `log` VALUES ('17', '实施', '2', '大无畏refer日志1日志1日志1上山打老虎里哦志1日志1日志1日志1日志1', '0', '是', '2018-02-21 23:43:00', 'lh');
INSERT INTO `log` VALUES ('18', '实施', '2', ' 的VS服务的 的VS服务的 的VS服务的 的VS服务的 的VS服务的 的VS服务的 的VS服务的 的VS服务的 的VS服务的', '0', '是', '2018-02-21 23:43:19', 'lh');
INSERT INTO `log` VALUES ('19', '实施', '2', '文档下refer日志1日志1日志1日志1日日志志1', '0', '是', '2018-02-21 23:44:24', 'lh');
INSERT INTO `log` VALUES ('20', '实施', '3', '文的问的我refer日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1日志1', '0', '是', '2018-02-21 23:44:28', 'lh');
INSERT INTO `log` VALUES ('21', '实施', '24', '文档', '0', '是', '2018-02-22 00:50:21', 'lh');
INSERT INTO `log` VALUES ('22', '实施', '7', '好的', '0', '是', '2018-02-22 01:50:01', 'lh');
INSERT INTO `log` VALUES ('23', '实施', '25', '我来添加个日志( •̀ ω •́ )y', '0', '是', '2018-02-22 02:34:09', 'lh');
INSERT INTO `log` VALUES ('24', '实施', '26', '再来添加个日志', '0', '是', '2018-02-22 02:34:31', 'lh');
INSERT INTO `log` VALUES ('25', '实施', '25', '号的，我继续添加昨天是有隔阂一回事带回待会我岁', '0', '是', '2018-02-22 02:34:38', 'lh');
INSERT INTO `log` VALUES ('26', '实施', '4', '嗯号的，OK，一二三四五，上山大老鼠', '0', '是', '2018-02-22 02:35:10', 'lh');
INSERT INTO `log` VALUES ('27', '实施', '1', 'w shi松岛枫喊你', '0', '是', '2018-02-24 22:04:31', 'sdf');
INSERT INTO `log` VALUES ('28', '实施', '1', 'w shi松岛枫w shi松岛枫w shi松岛枫', '0', '否', '2018-02-24 22:04:45', 'sdf');
INSERT INTO `log` VALUES ('30', '实施', '1', 'w jin进天籁以合格日志', '0', '是', '2018-02-25 04:10:59', 'lh');
INSERT INTO `log` VALUES ('31', '实施', '1', '电费网费', '0', '是', '2018-02-25 04:12:31', 'lh');
INSERT INTO `log` VALUES ('32', '实施', '1', 'v发的v额v额', '0', '是', '2018-02-25 04:12:34', 'lh');
INSERT INTO `log` VALUES ('33', '实施', '1', '范围日范文芳', '0', '是', '2018-02-25 04:12:37', 'lh');
INSERT INTO `log` VALUES ('34', '实施', '12', '完全得到', '0', '是', '2018-02-25 05:17:11', 'lh');
INSERT INTO `log` VALUES ('35', '实施', '12', '的温泉鹅我取得去的', '0', '是', '2018-02-25 05:17:15', 'lh');
INSERT INTO `log` VALUES ('36', '实施', '12', '对得起的温泉鹅的', '0', '是', '2018-02-25 05:17:18', 'lh');
INSERT INTO `log` VALUES ('37', '其他', null, '大旗网大旗网', '0', '是', '2018-02-25 05:25:12', 'lh');
INSERT INTO `log` VALUES ('38', '其他', null, '打威群岛亲卫队对得起我的', '0', '否', '2018-02-25 05:25:20', 'lh');
INSERT INTO `log` VALUES ('39', '实施', '24', '这是水里公司的陪培训日志', '0', '是', '2018-02-25 13:31:22', 'lh');
INSERT INTO `log` VALUES ('40', '实施', '24', '砸来合适的借口和丝袜的', '0', '是', '2018-02-25 13:31:30', 'lh');
INSERT INTO `log` VALUES ('41', '实施', '1', '号的看来都是浪费大家我睡觉哦', '0', '是', '2018-02-25 13:37:27', 'lh');
INSERT INTO `log` VALUES ('42', '实施', '27', '长江航道的看完就忘了', '0', '是', '2018-02-25 13:39:27', 'lh');

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `memberid` varchar(50) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `bigphoto` varchar(255) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `sflag` int(11) DEFAULT NULL COMMENT '是否是超级管理员0是超级管理员，1是普通管理员，999是锁定用户，2是删除用户',
  `age` int(11) DEFAULT NULL,
  `sex` varchar(2) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `note` text,
  `regdate` datetime DEFAULT NULL COMMENT '注册日期',
  `eflag` int(11) DEFAULT NULL COMMENT '错误标记次数',
  `contentColor` varchar(255) DEFAULT 'color:null',
  `hdColor` varchar(255) DEFAULT 'color:null',
  `menuColor` varchar(255) DEFAULT 'color:null',
  `bodyColor` varchar(255) DEFAULT 'color:null',
  `styleValue` varchar(255) DEFAULT 'default:null',
  `sysFont` varchar(255) DEFAULT 'null',
  `sysColor` varchar(255) DEFAULT 'null',
  `menuFontColor` varchar(255) DEFAULT 'null',
  `menuSelectedColor` varchar(255) DEFAULT 'null',
  `jobid` int(11) DEFAULT NULL,
  PRIMARY KEY (`memberid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES ('bdyjy', '波多野结衣', '/upload/member/75e89dbf-cb84-4803-81ca-028732397e77.jpeg', '/upload/member/051bf962-e4aa-47f6-adc3-8ef66f697da7.jpeg', '1', '1', '18', '女', '13896006088', null, '2018-02-06 23:50:10', null, 'color:null', 'color:null', 'color:null', 'color:null', 'default:null', 'null', 'null', 'null', 'null', '6');
INSERT INTO `member` VALUES ('cjk', '苍老师', '/upload/member/48d3ecae-379c-47bd-931c-8200bcd58de2.jpeg', '/upload/member/6f1f5637-f27b-4389-b068-4f75f26b82cb.jpeg', '1', '1', '18', '女', '18223170162', null, '2018-02-02 01:45:07', null, 'color:null', 'color:null', 'color:null', 'color:null', 'default:null', 'null', 'null', 'null', 'null', '6');
INSERT INTO `member` VALUES ('cl', '成龙', '/upload/member/a9cad0d1-f72c-48db-b339-5b810d15d99b.jpeg', '/upload/member/d8cae306-f2f2-4515-8add-afe10cde2845.jpeg', '1', '1', '95', '男', '18223170162', null, '2018-02-01 21:07:11', '0', 'color:null', 'color:null', 'color:null', 'color:null', 'default:null', 'null', 'null', 'null', 'null', '12');
INSERT INTO `member` VALUES ('fda', '饭岛爱', '/upload/member/5e88916c-b9e3-4d05-928e-f9a3e1588eaa.jpeg', '/upload/member/de040a1b-c112-447d-bc58-ac6278b0f3d0.jpeg', '1', '1', '18', '女', '13896006088', null, '2018-02-06 23:49:05', null, 'color:null', 'color:null', 'color:null', 'color:null', 'default:null', 'null', 'null', 'null', 'null', '6');
INSERT INTO `member` VALUES ('hh', '黄浩', '/upload/member/2c84a045-3a53-44d8-a6f2-04d860ee6ea5.jpeg', '/upload/member/c802c12e-85d1-4e60-a5aa-a815d959e359.jpeg', '1', '1', '26', '男', '13896006088', null, '2018-02-22 03:50:38', '0', 'color:null', 'color:null', 'color:null', 'color:null', 'default:null', 'null', 'null', 'null', 'null', '6');
INSERT INTO `member` VALUES ('ldh', '刘德华', '/upload/member/6217d6e3-3697-40c7-b411-39b7a7a1e73b.jpeg', '/upload/member/4b9225df-d359-497f-9f47-040a912ad205.jpeg', '1', '1', '26', '男', '18223170162', null, '2018-02-01 21:09:42', '0', 'color:null', 'color:null', 'color:null', 'color:null', 'default:null', 'null', 'null', 'null', 'null', '6');
INSERT INTO `member` VALUES ('lh', '黎杭', '/upload/member/cb2626b4-bc51-42f8-9b7a-0e1032365cb3.jpeg', '/upload/member/b7a168c3-86c7-4132-aa51-d6fc97a14f96.jpeg', '1', '0', '26', '男', '18223110163', null, '2018-02-01 21:17:04', '0', 'color:null', 'color:null', 'color:null', 'color:null', 'default:null', 'null', 'rgb(0, 0, 0)', 'rgb(0,0,0)', 'rgba(0, 162, 233, 0.5)', '6');
INSERT INTO `member` VALUES ('llj', '李连杰', '/upload/member/8236c5c5-f9ea-42cc-8cb6-14803f588f1e.jpeg', '/upload/member/4d9d14b6-b97b-4957-9125-f7b82fe76bdf.jpeg', '1', '1', '26', '男', '18838732123', null, '2018-01-20 20:59:43', '0', 'color:null', 'color:null', 'color:null', 'color:null', 'default:null', 'null', 'null', 'null', 'null', '7');
INSERT INTO `member` VALUES ('sdf', '松岛枫', '/upload/member/088c6383-4e4f-45e8-a72a-2e3345934d19.jpeg', '/upload/member/9c0b5bfc-fdf5-47a7-82fb-21d9e8decb33.jpeg', '1', '1', '18', '男', '18223170162', null, '2018-02-06 23:47:09', '0', 'color:null', 'color:null', 'color:null', 'color:null', 'default:null', 'null', 'null', 'null', 'null', '6');
INSERT INTO `member` VALUES ('vv', 'wq', '/upload/member/nophoto.png', '/upload/member/nophoto.png', '1', '1', '19', '男', '13896006088', null, '2018-02-22 03:55:22', '0', 'color:null', 'color:null', 'color:null', 'color:null', 'default:null', 'null', 'null', 'null', 'null', '7');
INSERT INTO `member` VALUES ('xzmly', '小泽玛利亚', '/upload/member/99edfa70-fc15-413a-8b63-4b400ce297b4.jpeg', '/upload/member/969c32a9-a4ef-4787-b0aa-e3303cc8ce22.jpeg', '1', '1', '19', '女', '13896006088', null, '2018-02-06 23:51:20', null, 'color:null', 'color:null', 'color:null', 'color:null', 'default:null', 'null', 'null', 'null', 'null', '7');
INSERT INTO `member` VALUES ('yjlx', '樱井莉香', '/upload/member/5c4b3ef0-a678-4ee2-9850-812e28aa4cd1.jpeg', '/upload/member/034a623a-e7b8-40da-9076-eca6c9435bd5.jpeg', '1', '1', '18', '女', '13896006088', null, '2018-02-06 23:52:45', null, 'color:null', 'color:null', 'color:null', 'color:null', 'default:null', 'null', 'null', 'null', 'null', '6');
INSERT INTO `member` VALUES ('zjl', '周杰伦', '/upload/member/d8b993ac-5ebc-4f15-941e-d51afb0785bd.jpeg', '/upload/member/34b59421-af6f-485a-b016-86f708c53a6a.jpeg', '1', '1', '25', '男', '18223170162', null, '2018-01-24 23:05:11', '0', 'color:null', 'color:null', 'color:null', 'color:null', 'default:null', 'null', 'null', 'null', 'null', '14');
INSERT INTO `member` VALUES ('zxc', '周星驰', '/upload/member/f286c9f1-97b2-4586-8f44-3ba8bb4d94e1.jpeg', '/upload/member/c9ea3923-186a-4aca-8805-20defc77e59a.jpeg', '2', '1', '22', '男', '18223170162', null, '2018-02-01 21:17:04', '0', 'color:null', 'color:null', 'color:null', 'color:null', 'default:null', 'null', 'null', 'null', 'null', '14');
INSERT INTO `member` VALUES ('zxy', '张学友', '/upload/member/e6cd1ea6-f95e-4fec-8d57-f264e6bba652.jpeg', '/upload/member/a910fc5b-c649-48f4-9409-587fc702cc51.jpeg', '1', '1', '26', '男', '18223170162', null, '2018-02-02 01:46:45', null, 'color:null', 'color:null', 'color:null', 'color:null', 'default:null', 'null', 'null', 'null', 'null', '6');

-- ----------------------------
-- Table structure for member_message
-- ----------------------------
DROP TABLE IF EXISTS `member_message`;
CREATE TABLE `member_message` (
  `messageid` int(11) DEFAULT NULL,
  `memberid` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT '未读' COMMENT '0表示未读，1表示已读'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of member_message
-- ----------------------------
INSERT INTO `member_message` VALUES ('39', 'lh', '未读');
INSERT INTO `member_message` VALUES ('40', 'lh', '未读');

-- ----------------------------
-- Table structure for member_role
-- ----------------------------
DROP TABLE IF EXISTS `member_role`;
CREATE TABLE `member_role` (
  `roleid` int(11) DEFAULT NULL,
  `memberid` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of member_role
-- ----------------------------
INSERT INTO `member_role` VALUES ('6', 'lh');
INSERT INTO `member_role` VALUES ('12', 'lh');
INSERT INTO `member_role` VALUES ('13', 'lh');
INSERT INTO `member_role` VALUES ('5', 'lh');
INSERT INTO `member_role` VALUES ('4', 'lh');
INSERT INTO `member_role` VALUES ('2', 'lh');
INSERT INTO `member_role` VALUES ('3', 'lh');
INSERT INTO `member_role` VALUES ('3', 'sdf');
INSERT INTO `member_role` VALUES ('4', 'sdf');
INSERT INTO `member_role` VALUES ('5', 'sdf');
INSERT INTO `member_role` VALUES ('7', 'sdf');
INSERT INTO `member_role` VALUES ('8', 'sdf');
INSERT INTO `member_role` VALUES ('3', 'fda');
INSERT INTO `member_role` VALUES ('4', 'fda');
INSERT INTO `member_role` VALUES ('5', 'fda');
INSERT INTO `member_role` VALUES ('7', 'fda');
INSERT INTO `member_role` VALUES ('8', 'fda');
INSERT INTO `member_role` VALUES ('3', 'bdyjy');
INSERT INTO `member_role` VALUES ('4', 'bdyjy');
INSERT INTO `member_role` VALUES ('5', 'bdyjy');
INSERT INTO `member_role` VALUES ('7', 'bdyjy');
INSERT INTO `member_role` VALUES ('8', 'bdyjy');
INSERT INTO `member_role` VALUES ('3', 'xzmly');
INSERT INTO `member_role` VALUES ('4', 'xzmly');
INSERT INTO `member_role` VALUES ('5', 'xzmly');
INSERT INTO `member_role` VALUES ('7', 'xzmly');
INSERT INTO `member_role` VALUES ('8', 'xzmly');
INSERT INTO `member_role` VALUES ('3', 'yjlx');
INSERT INTO `member_role` VALUES ('4', 'yjlx');
INSERT INTO `member_role` VALUES ('5', 'yjlx');
INSERT INTO `member_role` VALUES ('7', 'yjlx');
INSERT INTO `member_role` VALUES ('8', 'yjlx');
INSERT INTO `member_role` VALUES ('3', 'hh');
INSERT INTO `member_role` VALUES ('4', 'hh');
INSERT INTO `member_role` VALUES ('5', 'hh');
INSERT INTO `member_role` VALUES ('7', 'hh');
INSERT INTO `member_role` VALUES ('8', 'hh');
INSERT INTO `member_role` VALUES ('3', 'vv');
INSERT INTO `member_role` VALUES ('4', 'vv');
INSERT INTO `member_role` VALUES ('5', 'vv');
INSERT INTO `member_role` VALUES ('7', 'vv');
INSERT INTO `member_role` VALUES ('8', 'vv');
INSERT INTO `member_role` VALUES ('12', 'sdf');
INSERT INTO `member_role` VALUES ('2', 'sdf');
INSERT INTO `member_role` VALUES ('13', 'sdf');
INSERT INTO `member_role` VALUES ('6', 'sdf');
INSERT INTO `member_role` VALUES ('1', 'ldh');
INSERT INTO `member_role` VALUES ('12', 'ldh');
INSERT INTO `member_role` VALUES ('2', 'ldh');
INSERT INTO `member_role` VALUES ('13', 'ldh');
INSERT INTO `member_role` VALUES ('3', 'ldh');
INSERT INTO `member_role` VALUES ('4', 'ldh');
INSERT INTO `member_role` VALUES ('5', 'ldh');
INSERT INTO `member_role` VALUES ('6', 'ldh');
INSERT INTO `member_role` VALUES ('14', 'lh');
INSERT INTO `member_role` VALUES ('1', 'sdf');
INSERT INTO `member_role` VALUES ('15', 'lh');
INSERT INTO `member_role` VALUES ('15', 'sdf');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `messageid` int(11) NOT NULL AUTO_INCREMENT,
  `sender` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `flag` int(11) DEFAULT '0' COMMENT '0表示未发送，1表示发送过了',
  `dflag` int(11) DEFAULT '0',
  PRIMARY KEY (`messageid`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('8', '黎杭', '带我去啊的权威的文档好吧', '2018-02-26 05:32:55', 'shishi', '1', '0');
INSERT INTO `message` VALUES ('9', '黎杭', '问的你好吗，我的爱人', '2018-02-26 03:58:47', 'd对我期望', '0', '0');
INSERT INTO `message` VALUES ('34', '黎杭', '低洼轻武器', '2018-02-26 04:51:09', 'd的权威的', '0', '0');
INSERT INTO `message` VALUES ('35', '松岛枫', 'dwdq', '2018-02-26 05:00:21', 'dwqdq', '0', '0');
INSERT INTO `message` VALUES ('36', '黎杭', 'efverw还好还好哈', '2018-02-26 05:08:28', 'fwewe', '0', '0');
INSERT INTO `message` VALUES ('37', '黎杭', 'dwedwe', '2018-02-26 05:57:25', 'ddqdq', '1', '0');
INSERT INTO `message` VALUES ('38', '黎杭', 'wefdwe', '2018-02-26 06:21:02', 'fweqfwqe', '1', '0');
INSERT INTO `message` VALUES ('39', '黎杭', 'fwfwq', '2018-02-26 06:21:05', 'fweqfwqe', '1', '0');
INSERT INTO `message` VALUES ('40', '黎杭', 'fwfwq', '2018-02-26 06:21:09', 'fweqfwqe', '1', '0');

-- ----------------------------
-- Table structure for paylog
-- ----------------------------
DROP TABLE IF EXISTS `paylog`;
CREATE TABLE `paylog` (
  `paylogid` int(11) NOT NULL AUTO_INCREMENT,
  `contractid` int(11) DEFAULT NULL,
  `cost` int(11) DEFAULT NULL,
  `payway` varchar(255) DEFAULT NULL,
  `paybank` varchar(255) DEFAULT NULL,
  `payaccount` varchar(255) DEFAULT NULL,
  `shoubank` varchar(255) DEFAULT NULL,
  `shouaccount` varchar(255) DEFAULT NULL,
  `invoiceCost` int(11) DEFAULT NULL COMMENT '发票金额',
  `time` datetime DEFAULT NULL COMMENT '开具时间',
  `ykptime` datetime DEFAULT NULL COMMENT '已开票时间',
  `wkjCost` int(11) DEFAULT NULL COMMENT '未开具金额',
  `note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`paylogid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of paylog
-- ----------------------------
INSERT INTO `paylog` VALUES ('1', '1', '1000', '转账', '中国工商银行', '6228480470516215', '中国工商银行', '62284804701726594', '650', '2018-02-07 00:02:19', '2018-02-07 00:02:20', null, '我就想付个宽，这么麻烦吗');
INSERT INTO `paylog` VALUES ('2', '1', '987', '转账', '中国工商银行', '622848047055917212', '中国建设银行', '62284804701762294', '900', '2018-02-09 11:02:59', '2018-02-09 12:02:02', null, '');
INSERT INTO `paylog` VALUES ('3', '1', '379013', '转账', '交通银行', '6228480470516215', '中国工商银行', '62284804701726594', '2121', '2018-02-22 02:02:43', '2018-02-22 02:02:44', null, '又收到钱咯，开心哦');
INSERT INTO `paylog` VALUES ('4', '2', '379013', '转账', '交通银行', '6228480470516215', '中国工商银行', '62284804701726594', '2121', '2018-02-22 02:02:43', '2018-02-22 02:02:44', null, '又收到钱咯，开心哦');
INSERT INTO `paylog` VALUES ('5', '3', '100', '转账', '交通银行', '6228480470516215', '中国工商银行', '62284804701726594', '2121', '2018-02-22 02:02:43', '2018-02-22 02:02:44', null, '先付100块钱，剩下的差到');
INSERT INTO `paylog` VALUES ('6', '2', '999', '转账', '中国工商银行', '3472894326584', '中国农业银行', '687687686876', '888', '2018-02-22 11:02:30', '2018-02-23 14:02:07', null, '我就想首个款');
INSERT INTO `paylog` VALUES ('7', '5', '5959', '转账', '中国工商银行', '21211221', '中国工商银行', '345743795783478589', '2121', '2018-02-24 22:02:12', '2018-02-24 22:02:14', '212121', '21212121');
INSERT INTO `paylog` VALUES ('8', '3', '12345', '转账', '中国农业银行', '6228480470516215', '中国工商银行', '62284804701726594', '650', '2018-02-25 04:02:18', '2018-02-25 04:02:19', '21', '又收到钱咯，开心哦');
INSERT INTO `paylog` VALUES ('9', '5', '6341', '转账', '中国农业银行', '6228480470516215', '重庆银行', '62284804701726594', '2121', '2018-02-25 05:02:33', '2018-02-25 05:02:34', null, '来来付钱了');
INSERT INTO `paylog` VALUES ('10', '5', '10000', '网银', '中国农业银行', '6228480470516215', '中国工商银行', '6228480472320460961', '6999', '2018-02-25 13:02:21', '2018-02-25 13:02:22', null, '刷新了哦，再来一次付款');
INSERT INTO `paylog` VALUES ('11', '6', '5000', '转账', '中国农业银行', '6228480470516215', '中国建设银行', '2121212121212122', null, null, null, null, '今天收款5000');

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `projectid` int(11) NOT NULL AUTO_INCREMENT,
  `cost` int(11) DEFAULT NULL,
  `executive` varchar(255) DEFAULT NULL COMMENT '执行人',
  `contractid` int(11) DEFAULT NULL,
  `addDate` datetime DEFAULT NULL,
  `dflag` int(11) DEFAULT '0',
  PRIMARY KEY (`projectid`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('税金', 'qt', '1', '26000', '周星驰', '1', '2018-02-06 23:19:59', '0');
INSERT INTO `project` VALUES ('八大员17名', 'dp', '2', '51000', '周星驰', '1', '2018-02-06 23:19:59', '0');
INSERT INTO `project` VALUES ('中级技术工', 'dp', '3', '15000', '周星驰', '1', '2018-02-06 23:19:59', '0');
INSERT INTO `project` VALUES ('工程师代评', 'px', '4', '63000', '黎杭', '1', '2018-02-06 23:19:59', '0');
INSERT INTO `project` VALUES ('市政总承包', 'zz', '5', '56000', '黎杭', '1', '2018-02-06 23:19:59', '0');
INSERT INTO `project` VALUES ('安全许可证', 'zz', '6', '20000', '黎杭', '1', '2018-02-06 23:19:59', '0');
INSERT INTO `project` VALUES ('市政二建5名', 'gk', '7', '75000', '周杰伦', '1', '2018-02-06 23:19:59', '0');
INSERT INTO `project` VALUES ('市政工程师8', 'gk', '8', '75000', '李连杰', '1', '2018-02-06 23:19:59', '0');
INSERT INTO `project` VALUES ('电力设备发', 'qt', '9', '20000', '波多野结衣', '2', '2018-02-06 23:25:57', '0');
INSERT INTO `project` VALUES ('安许发票', 'qt', '10', '18000', '波多野结衣', '2', '2018-02-06 23:25:57', '0');
INSERT INTO `project` VALUES ('八大员15名', 'px', '11', '54000', '波多野结衣', '2', '2018-02-06 23:25:57', '0');
INSERT INTO `project` VALUES ('中级技工30', 'px', '12', '70000', '波多野结衣', '2', '2018-02-06 23:25:57', '0');
INSERT INTO `project` VALUES ('三类人员12', 'px', '13', '30000', '波多野结衣', '2', '2018-02-06 23:25:57', '0');
INSERT INTO `project` VALUES ('高电培训20', 'px', '14', '50000', '波多野结衣', '2', '2018-02-06 23:25:57', '0');
INSERT INTO `project` VALUES ('电力总承包', 'zz', '15', '60000', '波多野结衣', '2', '2018-02-06 23:25:57', '0');
INSERT INTO `project` VALUES ('安许新办', 'zz', '16', '38000', '波多野结衣', '2', '2018-02-06 23:25:57', '0');
INSERT INTO `project` VALUES ('四级承装修', 'zz', '17', '73000', '波多野结衣', '2', '2018-02-06 23:25:57', '0');
INSERT INTO `project` VALUES ('安装工长3', 'gk', '18', '27000', '波多野结衣', '2', '2018-02-06 23:25:57', '0');
INSERT INTO `project` VALUES ('助工10名', 'gk', '19', '50000', '波多野结衣', '2', '2018-02-06 23:25:57', '0');
INSERT INTO `project` VALUES ('机电二建5名', 'gk', '20', '1400000', '波多野结衣', '2', '2018-02-06 23:25:57', '0');
INSERT INTO `project` VALUES ('电力工程师', 'gk', '21', '120000', '波多野结衣', '2', '2018-02-06 23:25:57', '0');
INSERT INTO `project` VALUES ('八大员7名', 'qt', '22', '24500', '波多野结衣', '3', '2018-02-06 23:29:18', '0');
INSERT INTO `project` VALUES ('税金', 'qt', '23', '650', '波多野结衣', '3', '2018-02-06 23:29:18', '0');
INSERT INTO `project` VALUES ('税金', 'px', '24', '1000', '波多野结衣', '3', '2018-02-06 23:29:18', '0');
INSERT INTO `project` VALUES ('泡椒肉丝盖饭', 'zz', '25', '122121', '张学友', '5', '2018-02-22 02:16:40', '0');
INSERT INTO `project` VALUES ('青椒肉丝炒饭', 'zz', '26', '122', '张学友', '5', '2018-02-22 02:16:44', '0');
INSERT INTO `project` VALUES ('自身带瓶', 'dp', '27', '4000', '波多野结衣', '6', '2018-02-22 10:53:25', '0');
INSERT INTO `project` VALUES ('施工总承包', 'zz', '28', '6000', '波多野结衣', '6', '2018-02-22 10:53:25', '0');
INSERT INTO `project` VALUES ('来一个代评', 'dp', '29', '57', '张学友', '5', '2018-02-25 05:36:26', '0');
INSERT INTO `project` VALUES ('资质八大员', 'zz', '30', '150000', '张学友', '7', '2018-02-25 13:29:06', '0');
INSERT INTO `project` VALUES ('来一个其他可以吗', 'qt', '31', '69', '张学友', '7', '2018-02-25 13:42:29', '0');
INSERT INTO `project` VALUES ('来一个张三号码代评', 'dp', '32', '99', '张学友', '7', '2018-02-25 13:42:29', '0');
INSERT INTO `project` VALUES ('来一个培训好吗', 'px', '33', '3000', '张学友', '7', '2018-02-25 13:42:29', '0');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `roleid` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `flag` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`roleid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '项目管理', 'project');
INSERT INTO `role` VALUES ('2', '员工管理', 'member');
INSERT INTO `role` VALUES ('3', '部门管理', 'dept');
INSERT INTO `role` VALUES ('4', '系统管理', 'system');
INSERT INTO `role` VALUES ('5', '角色权限', 'roleAndAction');
INSERT INTO `role` VALUES ('6', '合同管理', 'contract');
INSERT INTO `role` VALUES ('12', '人才管理', 'talent');
INSERT INTO `role` VALUES ('13', '财务管理', 'finance');
INSERT INTO `role` VALUES ('14', '项目管理', 'project-p');
INSERT INTO `role` VALUES ('15', '消息管理', 'message');

-- ----------------------------
-- Table structure for role_action
-- ----------------------------
DROP TABLE IF EXISTS `role_action`;
CREATE TABLE `role_action` (
  `roleid` int(11) DEFAULT NULL,
  `actionid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_action
-- ----------------------------
INSERT INTO `role_action` VALUES ('5', '11');
INSERT INTO `role_action` VALUES ('5', '12');
INSERT INTO `role_action` VALUES ('5', '13');
INSERT INTO `role_action` VALUES ('5', '14');
INSERT INTO `role_action` VALUES ('5', '15');
INSERT INTO `role_action` VALUES ('5', '16');
INSERT INTO `role_action` VALUES ('2', '1');
INSERT INTO `role_action` VALUES ('2', '2');
INSERT INTO `role_action` VALUES ('2', '3');
INSERT INTO `role_action` VALUES ('2', '4');
INSERT INTO `role_action` VALUES ('2', '5');
INSERT INTO `role_action` VALUES ('2', '6');
INSERT INTO `role_action` VALUES ('4', '7');
INSERT INTO `role_action` VALUES ('4', '8');
INSERT INTO `role_action` VALUES ('4', '9');
INSERT INTO `role_action` VALUES ('4', '10');
INSERT INTO `role_action` VALUES ('3', '17');
INSERT INTO `role_action` VALUES ('3', '18');
INSERT INTO `role_action` VALUES ('3', '19');
INSERT INTO `role_action` VALUES ('3', '20');
INSERT INTO `role_action` VALUES ('3', '21');
INSERT INTO `role_action` VALUES ('3', '22');
INSERT INTO `role_action` VALUES ('3', '23');
INSERT INTO `role_action` VALUES ('3', '24');
INSERT INTO `role_action` VALUES ('3', '25');
INSERT INTO `role_action` VALUES ('6', '26');
INSERT INTO `role_action` VALUES ('6', '27');
INSERT INTO `role_action` VALUES ('6', '28');
INSERT INTO `role_action` VALUES ('6', '29');
INSERT INTO `role_action` VALUES ('6', '30');
INSERT INTO `role_action` VALUES ('13', '31');
INSERT INTO `role_action` VALUES ('6', '32');
INSERT INTO `role_action` VALUES ('1', '33');
INSERT INTO `role_action` VALUES ('1', '34');
INSERT INTO `role_action` VALUES ('1', '35');
INSERT INTO `role_action` VALUES ('1', '36');
INSERT INTO `role_action` VALUES ('1', '37');
INSERT INTO `role_action` VALUES ('1', '38');
INSERT INTO `role_action` VALUES ('14', '33');
INSERT INTO `role_action` VALUES ('14', '34');
INSERT INTO `role_action` VALUES ('14', '35');
INSERT INTO `role_action` VALUES ('14', '36');
INSERT INTO `role_action` VALUES ('14', '37');
INSERT INTO `role_action` VALUES ('14', '38');
INSERT INTO `role_action` VALUES ('14', '39');
INSERT INTO `role_action` VALUES ('13', '40');
INSERT INTO `role_action` VALUES ('13', '41');
INSERT INTO `role_action` VALUES ('15', '42');
INSERT INTO `role_action` VALUES ('15', '43');
INSERT INTO `role_action` VALUES ('15', '44');
INSERT INTO `role_action` VALUES ('15', '45');
