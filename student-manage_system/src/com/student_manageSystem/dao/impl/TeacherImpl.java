package com.student_manageSystem.dao.impl;

import com.student_manageSystem.dao.TeacherDao;
import com.student_manageSystem.pojo.Teacher;
import com.student_manageSystem.utils.SQLUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * 教师的增删改查
 */
public class TeacherImpl implements TeacherDao {

    /**
     * 查询教师姓名，判断老师是否存在
     * @param name
     * @return
     */
    @Override
    public boolean judge_teacherNameIsExist(String name) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select * from t_teacher_table where name = ?";

        try {
            conn = SQLUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            //释放资源
            SQLUtils.closeResource(rs, pstmt, conn);
        }
    }




    /**
     * 查询教师ID，判断老师是否存在
     * @param id
     * @return
     */
    @Override
    public boolean judge_teacherIDIsExist(String id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select * from t_teacher_table where id = ?";

        try {
            conn = SQLUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            //释放资源
            SQLUtils.closeResource(rs, pstmt, conn);
        }

    }



    /**
     * 判断教师的姓名和ID是否相对应
     * @param name
     * @param id_t
     * @return
     */
    @Override
    public boolean judgeTeacher_nameAndId(String name, String id_t) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select id from t_teacher_table where name = ?";

        try {
            conn = SQLUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            String id = null;
            if (rs.next()) {
               id = rs.getString("id");
            }
            return id != null && id.equals(id_t);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            //释放资源
            SQLUtils.closeResource(rs, pstmt, conn);
        }

    }





    /**
     * 教师登录，查询ID和密码是否正确
     * @param id : id
     * @param pwd : 密码
     * @return
     */
    @Override
    public boolean teacher_log(String id, String pwd) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select * from t_teacher_table where id = ? && pwd = ?";

        try {
            conn = SQLUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, pwd);
            rs = pstmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            SQLUtils.closeResource(rs, pstmt, conn);
        }

    }



    /**
     * 增加教师
     * @param teacher
     */
    @Override
    public void insert_teacher(Teacher teacher) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = SQLUtils.getConnection();
            String sql = "insert into t_teacher_table values (?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, teacher.getId());
            pstmt.setString(2, teacher.getPwd());
            pstmt.setString(3, teacher.getName());
            pstmt.setString(4, teacher.getSex());
            pstmt.setInt(5, teacher.getAge());
            pstmt.setString(6, teacher.getGraduate_school());
            pstmt.setInt(7, teacher.getSalary());
            pstmt.setString(8, teacher.getTelephone());

            int count = pstmt.executeUpdate();
            if (count > 0) System.out.println("成功注册或增加了一个教师");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            SQLUtils.closeResource(null, pstmt, conn);
        }
    }



    /**
     * 删除教师
     * @param id
     */
    @Override
    public void delete_teacher(String id) {
        try {
            Connection conn = SQLUtils.getConnection();
            String sql = "delete from t_teacher_table where id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,id);
            int count = pstmt.executeUpdate();

            if (count > 0) System.out.println("成功删除了一位教师");
            SQLUtils.closeResource(null, pstmt, conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





    /**
     * 更新教师
     * @param oldID
     * @param teacher
     */
    @Override
    public void update_teacher(String oldID, Teacher teacher){
        PreparedStatement pstmt = null;
        try {
            Connection conn = SQLUtils.getConnection();
            String sql = "update t_teacher_table set id=?, pwd=?, name=?, sex=?, age=?, graduate_school=?, salary=?, telephone=? where id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, teacher.getId());
            pstmt.setString(2, teacher.getPwd());
            pstmt.setString(3, teacher.getName());
            pstmt.setString(4, teacher.getSex());
            pstmt.setInt(5, teacher.getAge());
            pstmt.setString(6, teacher.getGraduate_school());
            pstmt.setInt(7, teacher.getSalary());
            pstmt.setString(8, teacher.getTelephone());
            pstmt.setString(9,oldID);

            int count = pstmt.executeUpdate();
            if (count > 0) System.out.println("成功更新了教师数据");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            SQLUtils.closeResource(null, pstmt, null);
        }

    }



    /**
     * 把教师数据库中的 所有  数据传递给教师表格（和管理员登录后首先看到的教师表格有关，也和刷新按钮有关）
     * @param teachers
     */
    @Override
    public void selectAll_teacherTable(ArrayList<Teacher> teachers) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = SQLUtils.getConnection();
            //先清空所有的数据
            teachers.clear();

            String sql = "select * from t_teacher_table";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String pwd = rs.getString("pwd");
                String name = rs.getString("name");
                String sex = rs.getString("sex");
                int age = rs.getInt("age");
                String graduate_school = rs.getString("graduate_school");
                int salary = rs.getInt("salary");
                String telephone = rs.getString("telephone");

                //创建teacher对象，并添加到ArrayList
                Teacher teacher = new Teacher(id, pwd, name, sex, age, graduate_school, salary, telephone);
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            //关闭资源
            SQLUtils.closeResource(rs, pstmt, conn);
        }
    }






    /**
     * 通过  ID查询  把老师数据库中的数据传递给老师表格
     * @param teachers
     * @param id
     */
    @Override
    public void select_teacherTableByID(ArrayList<Teacher> teachers, String id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = SQLUtils.getConnection();
            //先清空所有的数据
            teachers.clear();

            String sql = "select * from t_teacher_table where id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            //因为ID是唯一的，只有一个，所以不用使用while()
            if (rs.next()) {
                String pwd = rs.getString("pwd");
                String name = rs.getString("name");
                String sex = rs.getString("sex");
                int age = rs.getInt("age");
                String graduate_school = rs.getString("graduate_school");
                int salary = rs.getInt("salary");
                String telephone = rs.getString("telephone");

                //创建teacher对象，并添加到ArrayList
                Teacher teacher = new Teacher(id, pwd, name, sex, age, graduate_school, salary, telephone);
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            //关闭资源
            SQLUtils.closeResource(rs, pstmt, conn);
        }

    }






    /**
     * 通过姓名模糊查询 把教师数据库中的数据传递给教师表格
     * @param teachers
     * @param name
     */
    @Override
    public void select_teacherTableByName(ArrayList<Teacher> teachers, String name) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = SQLUtils.getConnection();
            //先清空所有的数据
            teachers.clear();

            //模糊查询
            String sql = "select * from t_teacher_table where name like '%' " +  "?" + " '%'; ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String pwd = rs.getString("pwd");
                //这里应该要获取一下name的值，因为我们是进行模糊查询，所以要获取一下完整的name值
                String name2 = rs.getString("name");
                String sex = rs.getString("sex");
                int age = rs.getInt("age");
                String graduate_school = rs.getString("graduate_school");
                int salary = rs.getInt("salary");
                String telephone = rs.getString("telephone");

                //创建teacher对象，并添加到ArrayList
                Teacher teacher = new Teacher(id, pwd, name2, sex, age, graduate_school, salary, telephone);
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            SQLUtils.closeResource(rs, pstmt, conn);
        }

    }






    /**
     * 判断教师是否教的有学生（用于管理员删除教师，因为学生表和教师表添加了外键，不能随便删除教师）
     * @param id_t
     * @return
     */
    @Override
    public boolean judgeTeacher_havaStudents(String id_t) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select * from t_student_table where teacher_id = ?";

        try {
            conn = SQLUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id_t);
            rs = pstmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            SQLUtils.closeResource(rs, pstmt, conn);
        }

    }

}
