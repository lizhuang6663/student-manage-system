package com.student_manageSystem.dao;

import com.student_manageSystem.pojo.Teacher;

import java.util.ArrayList;

public interface TeacherDao {
    //查询教师姓名，判断老师是否存在
    boolean judge_teacherNameIsExist(String name);

    //查询教师ID，判断老师是否存在
    boolean judge_teacherIDIsExist(String id);

    //判断教师的姓名和ID是否相对应
    boolean judgeTeacher_nameAndId(String name, String id_t);

    //教师登录，查询ID和密码是否正确
    boolean teacher_log(String id, String pwd);


    //增加教师
    void insert_teacher(Teacher teacher);
    //删除教师
    void delete_teacher(String id);

    //修改教师
    void update_teacher(String oldID, Teacher teacher);
    //把所有的教师的数据存放到List中
    void selectAll_teacherTable(ArrayList<Teacher> teachers);

    //通过id查询，把教师的数据存放到List中
    void select_teacherTableByID(ArrayList<Teacher> teachers, String id);

    //通过姓名模糊查询，把教师的数据存放到List中
    void select_teacherTableByName(ArrayList<Teacher> teachers, String name);


    //判断教师是否教的有学生（用于管理员删除教师，因为学生表和教师表添加了外键，不能随便删除教师）
    boolean judgeTeacher_havaStudents(String id_t);
}
