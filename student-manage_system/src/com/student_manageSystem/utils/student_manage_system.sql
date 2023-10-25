/*
 Navicat Premium Data Transfer

 Source Server         : Collection
 Source Server Type    : MySQL
 Source Server Version : 80033 (8.0.33)
 Source Host           : localhost:3306
 Source Schema         : student_manage_system

 Target Server Type    : MySQL
 Target Server Version : 80033 (8.0.33)
 File Encoding         : 65001

 Date: 24/10/2023 21:30:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_grade_table
-- ----------------------------
DROP TABLE IF EXISTS `t_grade_table`;
CREATE TABLE `t_grade_table` (
  `grade` int NOT NULL COMMENT '年级',
  `teacher_id` varchar(20) DEFAULT NULL COMMENT '教师ID',
  `class_school` int DEFAULT NULL COMMENT '班级',
  PRIMARY KEY (`grade`),
  KEY `fk_grade_teacher_id` (`teacher_id`),
  CONSTRAINT `fk_grade_teacher_id` FOREIGN KEY (`teacher_id`) REFERENCES `t_teacher_table` (`id`),
  CONSTRAINT `t_grade_table_chk_1` CHECK (((`grade` >= 1) and (`grade` <= 4)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='年级表格';

-- ----------------------------
-- Records of t_grade_table
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for t_manager_table
-- ----------------------------
DROP TABLE IF EXISTS `t_manager_table`;
CREATE TABLE `t_manager_table` (
  `id` varchar(20) NOT NULL COMMENT '管理员ID',
  `pwd` varchar(20) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='管理员表格';

-- ----------------------------
-- Records of t_manager_table
-- ----------------------------
BEGIN;
INSERT INTO `t_manager_table` (`id`, `pwd`) VALUES ('root', 'root');
COMMIT;

-- ----------------------------
-- Table structure for t_student_table
-- ----------------------------
DROP TABLE IF EXISTS `t_student_table`;
CREATE TABLE `t_student_table` (
  `id` varchar(20) NOT NULL COMMENT '学生ID',
  `pwd` varchar(32) DEFAULT NULL COMMENT '密码',
  `name` char(20) DEFAULT NULL COMMENT '姓名',
  `sex` char(1) DEFAULT NULL COMMENT '性别',
  `grade` int DEFAULT NULL COMMENT '年级',
  `chinese` int DEFAULT NULL COMMENT '语文',
  `math` int DEFAULT NULL COMMENT '数学',
  `english` int DEFAULT NULL COMMENT '英语',
  `chemistry` int DEFAULT NULL COMMENT '化学',
  `political` int DEFAULT NULL COMMENT '政治',
  `history` int DEFAULT NULL COMMENT '历史',
  `total_score` int DEFAULT NULL COMMENT '总分',
  `teacher_id` varchar(20) DEFAULT NULL COMMENT '教师ID',
  PRIMARY KEY (`id`),
  KEY `fk_student_teacher_id` (`teacher_id`),
  KEY `fk_student_grade` (`grade`),
  CONSTRAINT `fk_student_teacher_id` FOREIGN KEY (`teacher_id`) REFERENCES `t_teacher_table` (`id`),
  CONSTRAINT `t_student_table_chk_1` CHECK (((`grade` >= 1) and (`grade` <= 4))),
  CONSTRAINT `t_student_table_chk_2` CHECK (((`chinese` >= 0) and (`chinese` <= 100))),
  CONSTRAINT `t_student_table_chk_3` CHECK (((`math` >= 0) and (`math` <= 100))),
  CONSTRAINT `t_student_table_chk_4` CHECK (((`english` >= 0) and (`english` <= 100))),
  CONSTRAINT `t_student_table_chk_5` CHECK (((`chemistry` >= 0) and (`chemistry` <= 100))),
  CONSTRAINT `t_student_table_chk_6` CHECK (((`political` >= 0) and (`political` <= 100))),
  CONSTRAINT `t_student_table_chk_7` CHECK (((`history` >= 0) and (`history` <= 100))),
  CONSTRAINT `t_student_table_chk_8` CHECK (((`total_score` >= 0) and (`total_score` <= 600)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='学生表格';

-- ----------------------------
-- Records of t_student_table
-- ----------------------------
BEGIN;
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('1', 'c4ca4238a0b923820dcc509a6f75849b', '1', '男', 1, 11, 10, 10, 10, 10, 10, 61, '001');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('2', 'c4ca4238a0b923820dcc509a6f75849b', '1', '男', 1, 0, 0, 0, 0, 0, 0, 0, '002');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20210234206', 'b6dd4350092b69bff0c9eb218e1ebd48', 'wang', '男', 1, 10, 10, 0, 0, 0, 0, 20, '001');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544001', '698d51a19d8a121ce581499d7b701668', '刘备', '男', 4, 94, 80, 62, 64, 98, 95, 493, '001');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544002', '698d51a19d8a121ce581499d7b701668', '关羽', '男', 4, 92, 92, 69, 84, 82, 91, 510, '001');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544003', '698d51a19d8a121ce581499d7b701668', '张飞', '男', 4, 19, 32, 8, 29, 9, 5, 102, '001');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544004', '698d51a19d8a121ce581499d7b701668', '曹操', '男', 4, 98, 92, 81, 64, 98, 97, 530, '002');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544005', '698d51a19d8a121ce581499d7b701668', '许褚', '男', 4, 18, 29, 32, 23, 18, 32, 152, '002');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544006', '698d51a19d8a121ce581499d7b701668', '曹植', '男', 4, 95, 90, 82, 88, 91, 94, 540, '002');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544007', '698d51a19d8a121ce581499d7b701668', '孙权', '男', 4, 89, 83, 65, 64, 88, 87, 476, '003');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544008', '698d51a19d8a121ce581499d7b701668', '周瑜', '男', 4, 40, 50, 89, 39, 12, 49, 279, '003');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544009', '698d51a19d8a121ce581499d7b701668', '周泰', '男', 4, 48, 28, 19, 29, 18, 29, 171, '003');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544011', '698d51a19d8a121ce581499d7b701668', '诸葛亮', '男', 3, 98, 96, 87, 88, 92, 98, 559, '001');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544012', '698d51a19d8a121ce581499d7b701668', '马超', '男', 3, 93, 94, 73, 81, 79, 85, 505, '001');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544013', '698d51a19d8a121ce581499d7b701668', '赵云', '男', 3, 75, 85, 92, 85, 74, 54, 465, '001');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544014', '698d51a19d8a121ce581499d7b701668', '夏侯惇', '男', 3, 84, 52, 94, 82, 82, 49, 443, '002');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544015', '698d51a19d8a121ce581499d7b701668', '夏侯渊', '男', 3, 12, 51, 23, 42, 32, 41, 201, '002');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544016', '698d51a19d8a121ce581499d7b701668', '曹仁', '男', 3, 94, 92, 84, 72, 95, 85, 522, '002');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544017', '698d51a19d8a121ce581499d7b701668', '陆逊', '男', 3, 19, 39, 28, 19, 43, 29, 177, '003');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544018', '698d51a19d8a121ce581499d7b701668', '鲁肃', '男', 3, 12, 51, 23, 42, 32, 41, 201, '003');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544019', '698d51a19d8a121ce581499d7b701668', '吕蒙', '男', 3, 86, 75, 94, 85, 92, 84, 516, '003');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544021', '698d51a19d8a121ce581499d7b701668', '廖化', '男', 2, 94, 92, 84, 85, 85, 82, 522, '001');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544022', '698d51a19d8a121ce581499d7b701668', '张翼', '男', 2, 25, 19, 29, 48, 23, 23, 167, '001');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544023', '698d51a19d8a121ce581499d7b701668', '孙尚香', '女', 2, 91, 38, 73, 82, 71, 83, 438, '001');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544024', '698d51a19d8a121ce581499d7b701668', '卞夫人', '女', 2, 49, 31, 81, 84, 83, 91, 419, '002');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544025', '698d51a19d8a121ce581499d7b701668', '曹洪', '男', 2, 91, 81, 84, 78, 81, 61, 476, '002');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544026', '698d51a19d8a121ce581499d7b701668', '张辽', '男', 2, 40, 50, 89, 39, 12, 49, 279, '002');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544027', '698d51a19d8a121ce581499d7b701668', '程普', '男', 2, 88, 73, 82, 84, 92, 69, 488, '003');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544028', '698d51a19d8a121ce581499d7b701668', '黄盖', '男', 2, 23, 49, 19, 10, 29, 18, 148, '003');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544029', '698d51a19d8a121ce581499d7b701668', '韩当', '男', 2, 19, 39, 28, 19, 43, 29, 177, '003');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544031', '698d51a19d8a121ce581499d7b701668', '姜维', '男', 1, 92, 92, 69, 84, 82, 91, 510, '001');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544032', '698d51a19d8a121ce581499d7b701668', '刘禅', '男', 1, 23, 49, 19, 10, 29, 18, 148, '001');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544033', '698d51a19d8a121ce581499d7b701668', '魏延', '男', 1, 25, 19, 29, 48, 23, 23, 167, '001');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544034', '698d51a19d8a121ce581499d7b701668', '于禁', '男', 1, 19, 39, 28, 19, 43, 29, 177, '002');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544035', '698d51a19d8a121ce581499d7b701668', '张郃', '男', 1, 84, 52, 94, 82, 82, 49, 443, '002');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544036', '698d51a19d8a121ce581499d7b701668', '郭嘉', '男', 1, 12, 51, 23, 42, 32, 41, 201, '002');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544037', '698d51a19d8a121ce581499d7b701668', '小乔', '女', 1, 19, 32, 8, 29, 9, 5, 102, '003');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544038', '698d51a19d8a121ce581499d7b701668', '大乔', '女', 1, 97, 92, 80, 84, 95, 95, 543, '003');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('20221544039', '698d51a19d8a121ce581499d7b701668', '甘宁', '男', 1, 40, 67, 89, 46, 69, 49, 360, '003');
INSERT INTO `t_student_table` (`id`, `pwd`, `name`, `sex`, `grade`, `chinese`, `math`, `english`, `chemistry`, `political`, `history`, `total_score`, `teacher_id`) VALUES ('3', 'eccbc87e4b5ce2fe28308fd9f2a7baf3', '3', '男', 3, 10, 10, 10, 10, 10, 10, 60, '003');
COMMIT;

-- ----------------------------
-- Table structure for t_teacher_table
-- ----------------------------
DROP TABLE IF EXISTS `t_teacher_table`;
CREATE TABLE `t_teacher_table` (
  `id` varchar(20) NOT NULL COMMENT '老师ID',
  `pwd` varchar(20) DEFAULT NULL COMMENT '密码',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `sex` char(1) DEFAULT NULL COMMENT '性别',
  `age` int DEFAULT NULL COMMENT '年龄',
  `graduate_school` varchar(20) DEFAULT NULL COMMENT '毕业学校',
  `salary` int DEFAULT NULL COMMENT '工资',
  `telephone` varchar(11) DEFAULT NULL COMMENT '电话号码',
  PRIMARY KEY (`id`),
  CONSTRAINT `t_teacher_table_chk_1` CHECK (((`age` >= 22) and (`age` <= 100)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='教师表格';

-- ----------------------------
-- Records of t_teacher_table
-- ----------------------------
BEGIN;
INSERT INTO `t_teacher_table` (`id`, `pwd`, `name`, `sex`, `age`, `graduate_school`, `salary`, `telephone`) VALUES ('001', '111', '蜀汉', '男', 43, '蜀汉集团大学', 30000, '11111111111');
INSERT INTO `t_teacher_table` (`id`, `pwd`, `name`, `sex`, `age`, `graduate_school`, `salary`, `telephone`) VALUES ('002', '222', '曹魏', '男', 47, '曹魏集团大学', 45000, '22222222222');
INSERT INTO `t_teacher_table` (`id`, `pwd`, `name`, `sex`, `age`, `graduate_school`, `salary`, `telephone`) VALUES ('003', '333', '东吴', '男', 58, '东吴集团大学', 27000, '33333333333');
COMMIT;

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `id` int NOT NULL COMMENT 'ID',
  `name` char(5) DEFAULT NULL COMMENT '姓名',
  `pwd` varchar(25) DEFAULT NULL COMMENT '密码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='测试表';

-- ----------------------------
-- Records of test
-- ----------------------------
BEGIN;
INSERT INTO `test` (`id`, `name`, `pwd`) VALUES (0, NULL, '1');
INSERT INTO `test` (`id`, `name`, `pwd`) VALUES (1, '李李李', '1');
INSERT INTO `test` (`id`, `name`, `pwd`) VALUES (1, '李', NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
