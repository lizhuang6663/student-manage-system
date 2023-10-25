package com.student_manageSystem.dao;

import java.util.ArrayList;

public interface StudentDao {

    //判断学生的ID是否存在
    boolean judge_StudentIDIsExist(String id);

    //学生登录
    boolean student_log(String id, String pwd);

    //学生注册；教师，管理员增加学生
    void insert_student(String id, String pwd, String name, String sex, int grade, String teacher_id);


    //通过学生的教师姓名 来找到真实的教师ID（最后我们会把真实的教师ID存到学生数据库表中）
    String replaceName_withId(String name);

    //教师，管理员删除学生
    void delete_student(String id);

    //学生自己修改自己的信息
    void update_studentByOneself(String oldID, String pwd, String name, String sex, int grade, String teacher_id);


    //教师，管理员修改学生的信息
    void update_studentByTeacherAndManager(String oldID, com.student_manageSystem.pojo.Student student);

    //学生查看自己的信息，把数据封装到Student对象中，并返回
    com.student_manageSystem.pojo.Student select_studentByOneself(String id, String pwd);

    //将学生的teacher_id 换成对应的真实教师姓名
    void select_teacherByTeacher_id(com.student_manageSystem.pojo.Student student);



    //教师：
    //把学生数据库中的 属于相应教师的 数据传递给学生表格（教师使用）
    void selectAll_studentTable_forTeacher(ArrayList<com.student_manageSystem.pojo.Student> students, String teacher_id);

    //通过  ID查询  把学生数据库中的数据传递给学生表格
    void select_studentTableByID_forTeacher(ArrayList<com.student_manageSystem.pojo.Student> students, String id_s, String id_t);

    //通过姓名模糊查询 把学生数据库中的数据传递给学生表格
    void select_studentTableByName_forTeacher(ArrayList<com.student_manageSystem.pojo.Student> students, String name_s, String id_t);





    //管理员：
    //把所有的学生数据存放到List中
    void selectAll_studentTable_forManager(ArrayList<com.student_manageSystem.pojo.Student> students);

    //通过ID查询，把学生数据存放到List中
    void select_studentTableByID_forManager(ArrayList<com.student_manageSystem.pojo.Student> students, String id);

    //通过grade查询，把学生数据存放到List中
    void select_studentTableByGrade_forManager(ArrayList<com.student_manageSystem.pojo.Student> students, int grade) ;

    //通过ID和grade查询，把学生数据存放到List中
    void select_studentTableByIDAndGrade_forManager(ArrayList<com.student_manageSystem.pojo.Student> students, String studentID, int grade);

    //通过姓名模糊查询，把学生数据存放到List中
    void select_studentTableByName_forManager(ArrayList<com.student_manageSystem.pojo.Student> students, String name);

}
