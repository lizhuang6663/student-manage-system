package com.student_manageSystem.dao.impl;

import com.student_manageSystem.dao.StudentDao;
import com.student_manageSystem.utils.MD5Utils;
import com.student_manageSystem.utils.SQLUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * StudentDao负责学生的增删改查
 */
public class StudentImpl implements StudentDao {

    /**
     * 判断学生的ID是否存在
     * @param id : id
     * @return : true:存在该数据，false：不存在
     */
    @Override
    public boolean judge_StudentIDIsExist(String id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select * from t_student_table where id = ?";

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
     * 学生登录
     * @param id : id
     * @param pwd : 密码
     * @return : true.正确；false.错误
     * @throws Exception
     */
    @Override
    public  boolean student_log(String id, String pwd) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select * from t_student_table where id = ? && pwd = ?";

        //不可逆加密，将学生输入的密码加密后与数据库中的加密密码比较，如果相等，就说明输入的密码正确
        String encryption_pwd = MD5Utils.md5Encryption(pwd);

        try {
            conn = SQLUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, encryption_pwd);
            rs = pstmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            SQLUtils.closeResource(rs, pstmt, conn);
        }

    }



    /**
     * 增加学生（包含：学生注册；老师或管理员增加学生）
     * @param grade
     * @param name
     * @param sex
     * @param pwd
     */
    @Override
    public void insert_student(String id, String pwd, String name, String sex, int grade, String teacher_id) {
        //我们传过来的teacher_id 其实是教师名字，这里我要把传过来的teacher_id（教师名字）改为真正的教师ID
        teacher_id = replaceName_withId(teacher_id);

        //将密码加密的结果添加到数据库中
        String encryption_pwd = MD5Utils.md5Encryption(pwd);

        try {
            Connection conn = SQLUtils.getConnection();
            String sql = "insert into t_student_table values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, encryption_pwd);
            pstmt.setString(3, name);
            pstmt.setString(4, sex);
            pstmt.setInt(5, grade);
            pstmt.setInt(6, 0);
            pstmt.setInt(7, 0);
            pstmt.setInt(8, 0);
            pstmt.setInt(9, 0);
            pstmt.setInt(10, 0);
            pstmt.setInt(11, 0);
            pstmt.setInt(12, 0);
            pstmt.setString(13, teacher_id);

            int count = pstmt.executeUpdate();
            if (count > 0) System.out.println("成功注册或增加了一个学生");
            SQLUtils.closeResource(null, pstmt, conn);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * 通过学生的teacher_id（我们输入的其实是教师姓名） 来找到真实的教师ID（最后我们会把真实的教师ID存到学生数据库表中）
     * @param name
     */
    @Override
    public String replaceName_withId(String name) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select id from t_teacher_table where name = ? ";

        try {
            conn = SQLUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            String id = null;
            if (rs.next()) {
                id = rs.getString("id");
            }
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            //释放资源
            SQLUtils.closeResource(rs, pstmt, conn);
        }
    }






    /**
     * 教师、管理员删除学生
     * @param id
     * @throws Exception
     */
    @Override
    public void delete_student(String id) {
        try {
            Connection conn = SQLUtils.getConnection();
            String sql = "delete from t_student_table where id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,id);
            int count = pstmt.executeUpdate();
            if (count > 0) System.out.println("成功删除了一个学生");
            SQLUtils.closeResource(null, pstmt, conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * 学生自己修改自己的信息（无法修改学号和成绩）
     * @param oldID
     * @param pwd
     * @param name
     * @param sex
     * @param grade
     * @throws Exception
     */
    @Override
    public void update_studentByOneself(String oldID, String pwd, String name, String sex, int grade, String teacher_id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        //把密码加密
        String encryption_pwd = MD5Utils.md5Encryption(pwd);

        try {
            conn = SQLUtils.getConnection();
            String sql = "update t_student_table set pwd=?, name=?, sex=?, grade=?, teacher_id=? where id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, encryption_pwd);
            pstmt.setString(2, name);
            pstmt.setString(3, sex);
            pstmt.setInt(4, grade);
            pstmt.setString(5, teacher_id);
            pstmt.setString(6, oldID);

            int count = pstmt.executeUpdate();
            if (count > 0) System.out.println("学生更改了自己的数据");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            SQLUtils.closeResource(null, pstmt, conn);
        }
    }






    /**
     * 老师，管理员修改学生信息(可以修改学号，成绩)
     * @param oldID : 之前的学号，根据这个学号来查找到学生
     * @param student : 学生对象，里面封装了学生的属性
     */
    @Override
    public void update_studentByTeacherAndManager(String oldID, com.student_manageSystem.pojo.Student student) {

        try {
            Connection conn = SQLUtils.getConnection();
            String sql = "update t_student_table set id=?, name=?, sex=?, grade=?, chinese=?, math=?, english=?, chemistry=?, political=?, history=?, total_score=?, teacher_id=? where id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, student.getId());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getSex());
            pstmt.setInt(4, student.getGrade());
            pstmt.setInt(5, student.getChinese());
            pstmt.setInt(6, student.getMath());
            pstmt.setInt(7, student.getEnglish());
            pstmt.setInt(8, student.getChemistry());
            pstmt.setInt(9, student.getPolitical());
            pstmt.setInt(10, student.getHistory());
            pstmt.setInt(11, student.getTotal_score());
            pstmt.setString(12, student.getTeacher_id());
            pstmt.setString(13, oldID);

            int count = pstmt.executeUpdate();
            if (count > 0) System.out.println("成功更新了学生数据");
            SQLUtils.closeResource(null, pstmt, conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





    /**
     *  学生自己查看自己
     * @param id
     * @param pwd
     * @return
     */
    @Override
    public com.student_manageSystem.pojo.Student select_studentByOneself(String id, String pwd)  {
        com.student_manageSystem.pojo.Student student = new com.student_manageSystem.pojo.Student();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = SQLUtils.getConnection();
            String sql = "select * from t_student_table where id = ? && pwd = ?";
            pstmt =conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, pwd);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                student.setId(rs.getString("id"));
                student.setPwd(rs.getString("pwd"));
                student.setName(rs.getString("name"));
                student.setSex(rs.getString("sex"));
                student.setGrade(rs.getInt("grade"));
                student.setChinese(rs.getInt("chinese"));
                student.setMath(rs.getInt("math"));
                student.setEnglish(rs.getInt("english"));
                student.setChemistry(rs.getInt("chemistry"));
                student.setPolitical(rs.getInt("political"));
                student.setHistory(rs.getInt("history"));
                student.setTotal_score(rs.getInt("total_score"));
                student.setTeacher_id(rs.getString("teacher_id"));

                //把teacher_id 改为 教师姓名
                select_teacherByTeacher_id(student);
            }

            //返回学生对象
            return student;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            SQLUtils.closeResource(rs, pstmt, conn);
        }
    }



    /**
     * 将学生的teacher_id 换成对应的真实教师姓名
     * @param student
     */
    @Override
    public void select_teacherByTeacher_id(com.student_manageSystem.pojo.Student student) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = SQLUtils.getConnection();
            //多表查询：显式内连接
            String sql = "select t.name from t_teacher_table t inner join t_student_table s on s.id = ? && t.id = s.teacher_id";
            pstmt =conn.prepareStatement(sql);
            pstmt.setString(1, student.getId());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("t.name");
                //把teacher_id 改为 teacher 的name
                student.setTeacher_id(name);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            SQLUtils.closeResource(rs, pstmt, conn);
        }
    }





    /**
     * 把学生数据库中的 属于相应教师的 数据传递给学生表格（教师使用）
     * @param students
     * @param teacher_id : 教师的ID号码
     */
    @Override
    public void selectAll_studentTable_forTeacher(ArrayList<com.student_manageSystem.pojo.Student> students, String teacher_id) {
        try {
            Connection conn = SQLUtils.getConnection();
            //先清空所有的数据
            students.clear();

            //多表查询，隐式内连接
            String sql = "select s.* from t_student_table s, t_teacher_table t where t.id = s.teacher_id && s.teacher_id = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, teacher_id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String pwd = rs.getString("pwd");
                String name = rs.getString("name");
                String sex = rs.getString("sex");
                int grade = rs.getInt("grade");
                int chinese = rs.getInt("chinese");
                int math = rs.getInt("math");
                int english = rs.getInt("english");
                int chemistry = rs.getInt("chemistry");
                int political = rs.getInt("political");
                int history = rs.getInt("history");
                int total_score = rs.getInt("total_score");

                //创建student对象，并添加到ArrayList
                com.student_manageSystem.pojo.Student student = new com.student_manageSystem.pojo.Student(id, pwd, name, sex, grade, chinese, math, english, chemistry, political, history, total_score, teacher_id);
                //把teacher_id 改为 teacher 的name
                select_teacherByTeacher_id(student);

                students.add(student);
            }
            //关闭资源
            SQLUtils.closeResource(rs, pstmt, conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * 通过  ID查询  把学生数据库中的数据传递给学生表格（教师使用）
     * @param students
     * @param id_s : 学生的ID号码
     * @param id_t : 教师的ID号码
     */
    @Override
    public void select_studentTableByID_forTeacher(ArrayList<com.student_manageSystem.pojo.Student> students, String id_s, String id_t) {
        try {
            Connection conn = SQLUtils.getConnection();
            //先清空所有的数据
            students.clear();

            //多表查询，显式内连接
            String sql = "select s.* from t_student_table s inner join t_teacher_table t on s.teacher_id = t.id && t.id = ? && s.id = ?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id_t);
            pstmt.setString(2, id_s);
            ResultSet rs = pstmt.executeQuery();

            //因为ID是唯一的，只有一个，所以不用使用while()
            if (rs.next()) {
                String pwd = rs.getString("pwd");
                String name = rs.getString("name");
                String sex = rs.getString("sex");
                int grade = rs.getInt("grade");
                int chinese = rs.getInt("chinese");
                int math = rs.getInt("math");
                int english = rs.getInt("english");
                int chemistry = rs.getInt("chemistry");
                int political = rs.getInt("political");
                int history = rs.getInt("history");
                int total_score = rs.getInt("total_score");
                String teacher_id = rs.getString("teacher_id");

                //创建student对象，并添加到ArrayList
                com.student_manageSystem.pojo.Student student = new com.student_manageSystem.pojo.Student(id_s, pwd, name, sex, grade, chinese, math, english, chemistry, political, history, total_score, teacher_id);
                //把teacher_id 改为 teacher 的name
                select_teacherByTeacher_id(student);

                students.add(student);
            }
            //关闭资源
            SQLUtils.closeResource(rs, pstmt, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    /**
     * 通过姓名模糊查询 把学生数据库中的数据传递给学生表格（教师使用）
     * @param students
     * @param name_s
     * @param id_t
     */
    @Override
    public void select_studentTableByName_forTeacher(ArrayList<com.student_manageSystem.pojo.Student> students, String name_s, String id_t) {
        try {
            Connection conn = SQLUtils.getConnection();
            //先清空所有的数据
            students.clear();

            //模糊查询，多表查询，隐式内连接
            String sql = "select * from t_student_table s where s.name like '%' " + '?' + " '%' and s.teacher_id = ?; ";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name_s);
            pstmt.setString(2, id_t);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String name2 = rs.getString("name");
                String pwd = rs.getString("pwd");
                String sex = rs.getString("sex");
                int grade = rs.getInt("grade");
                int chinese = rs.getInt("chinese");
                int math = rs.getInt("math");
                int english = rs.getInt("english");
                int chemistry = rs.getInt("chemistry");
                int political = rs.getInt("political");
                int history = rs.getInt("history");
                int total_score = rs.getInt("total_score");
                String teacher_id = rs.getString("teacher_id");

                //创建student对象，并添加到ArrayList
                com.student_manageSystem.pojo.Student student = new com.student_manageSystem.pojo.Student(id, pwd, name2, sex, grade, chinese, math, english, chemistry, political, history, total_score, teacher_id);
                //把teacher_id 改为 teacher 的name
                select_teacherByTeacher_id(student);

                students.add(student);
            }
            //关闭资源
            SQLUtils.closeResource(rs, pstmt, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }






    /**
     * ----------------------------------------------------------------------------------------------------------
     */

    /**
     *  把学生数据库中的 所有  数据传递给学生表格（管理员使用）
     * @param students
     */
    @Override
    public void selectAll_studentTable_forManager(ArrayList<com.student_manageSystem.pojo.Student> students) {
        try {
            Connection conn = SQLUtils.getConnection();
            //先清空所有的数据
            students.clear();

            String sql = "select * from t_student_table";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String pwd = rs.getString("pwd");
                String name = rs.getString("name");
                String sex = rs.getString("sex");
                int grade = rs.getInt("grade");
                int chinese = rs.getInt("chinese");
                int math = rs.getInt("math");
                int english = rs.getInt("english");
                int chemistry = rs.getInt("chemistry");
                int political = rs.getInt("political");
                int history = rs.getInt("history");
                int total_score = rs.getInt("total_score");
                String teacher_id = rs.getString("teacher_id");


                //创建student对象，并添加到ArrayList
                com.student_manageSystem.pojo.Student student = new com.student_manageSystem.pojo.Student(id, pwd, name, sex, grade, chinese, math, english, chemistry, political, history, total_score, teacher_id);
                //把teacher_id 改为 teacher 的name
                select_teacherByTeacher_id(student);

                students.add(student);
            }
            //关闭资源
            SQLUtils.closeResource(rs, pstmt, conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 通过  ID查询  把学生数据库中的数据传递给学生表格（管理员使用）
     * @param students
     * @param id
     */
    @Override
    public void select_studentTableByID_forManager(ArrayList<com.student_manageSystem.pojo.Student> students, String id) {
        try {
            Connection conn = SQLUtils.getConnection();
            //先清空所有的数据
            students.clear();

            String sql = "select * from t_student_table where id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            //因为ID是唯一的，只有一个，所以不用使用while()
            if (rs.next()) {
                String pwd = rs.getString("pwd");
                String name = rs.getString("name");
                String sex = rs.getString("sex");
                int grade = rs.getInt("grade");
                int chinese = rs.getInt("chinese");
                int math = rs.getInt("math");
                int english = rs.getInt("english");
                int chemistry = rs.getInt("chemistry");
                int political = rs.getInt("political");
                int history = rs.getInt("history");
                int total_score = rs.getInt("total_score");
                String teacher_id = rs.getString("teacher_id");

                //创建student对象，并添加到ArrayList
                com.student_manageSystem.pojo.Student student = new com.student_manageSystem.pojo.Student(id, pwd, name, sex, grade, chinese, math, english, chemistry, political, history, total_score, teacher_id);
                //把teacher_id 改为 teacher 的name
                select_teacherByTeacher_id(student);

                students.add(student);
            }
            //关闭资源
            SQLUtils.closeResource(rs, pstmt, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * 通过  年级查询  把学生数据库中的数据传递给学生表格（管理员使用）
     * @param students
     * @param grade
     */
    @Override
    public void select_studentTableByGrade_forManager(ArrayList<com.student_manageSystem.pojo.Student> students, int grade) {
        try {
            Connection conn = SQLUtils.getConnection();
            //先清空所有的数据
            students.clear();

            String sql = "select * from t_student_table where grade = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, grade);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String pwd = rs.getString("pwd");
                String name = rs.getString("name");
                String sex = rs.getString("sex");
                int chinese = rs.getInt("chinese");
                int math = rs.getInt("math");
                int english = rs.getInt("english");
                int chemistry = rs.getInt("chemistry");
                int political = rs.getInt("political");
                int history = rs.getInt("history");
                int total_score = rs.getInt("total_score");
                String teacher_id = rs.getString("teacher_id");

                //创建student对象，并添加到ArrayList
                com.student_manageSystem.pojo.Student student = new com.student_manageSystem.pojo.Student(id, pwd, name, sex, grade, chinese, math, english, chemistry, political, history, total_score,teacher_id);
                //把teacher_id 改为 teacher 的name
                select_teacherByTeacher_id(student);

                students.add(student);
            }
            //关闭资源
            SQLUtils.closeResource(rs, pstmt, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * 同时通过  ID和年级查询  把学生数据库中的数据传递给学生表格（管理员使用）
     * @param students
     * @param id
     * @param grade
     */
    @Override
    public void select_studentTableByIDAndGrade_forManager(ArrayList<com.student_manageSystem.pojo.Student> students, String id, int grade) {
        try {
            Connection conn = SQLUtils.getConnection();
            //先清空所有的数据
            students.clear();

            String sql = "select * from t_student_table where id = ? and grade = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setInt(2, grade);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String pwd = rs.getString("pwd");
                String name = rs.getString("name");
                String sex = rs.getString("sex");
                int chinese = rs.getInt("chinese");
                int math = rs.getInt("math");
                int english = rs.getInt("english");
                int chemistry = rs.getInt("chemistry");
                int political = rs.getInt("political");
                int history = rs.getInt("history");
                int total_score = rs.getInt("total_score");
                String teacher_id = rs.getString("teacher_id");

                //创建student对象，并添加到ArrayList
                com.student_manageSystem.pojo.Student student = new com.student_manageSystem.pojo.Student(id, pwd, name, sex, grade, chinese, math, english, chemistry, political, history, total_score, teacher_id);
                //把teacher_id 改为 teacher 的name
                select_teacherByTeacher_id(student);

                students.add(student);
            }
            //关闭资源
            SQLUtils.closeResource(rs, pstmt, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    /**
     * 通过姓名模糊查询 把学生数据库中的数据传递给学生表格（管理员使用）
     * @param students
     * @param name
     */
    @Override
    public void select_studentTableByName_forManager(ArrayList<com.student_manageSystem.pojo.Student> students, String name) {
        try {
            Connection conn = SQLUtils.getConnection();
            //先清空所有的数据
            students.clear();

            //模糊查询
            String sql = "select * from t_student_table where name like '%' " + "?" + " '%'; ";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String name2 = rs.getString("name");
                String pwd = rs.getString("pwd");
                String sex = rs.getString("sex");
                int grade = rs.getInt("grade");
                int chinese = rs.getInt("chinese");
                int math = rs.getInt("math");
                int english = rs.getInt("english");
                int chemistry = rs.getInt("chemistry");
                int political = rs.getInt("political");
                int history = rs.getInt("history");
                int total_score = rs.getInt("total_score");
                String teacher_id = rs.getString("teacher_id");

                //创建student对象，并添加到ArrayList
                com.student_manageSystem.pojo.Student student = new com.student_manageSystem.pojo.Student(id, pwd, name2, sex, grade, chinese, math, english, chemistry, political, history, total_score, teacher_id);
                //把teacher_id 改为 teacher 的name
                select_teacherByTeacher_id(student);

                students.add(student);
            }
            //关闭资源
            SQLUtils.closeResource(rs, pstmt, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
