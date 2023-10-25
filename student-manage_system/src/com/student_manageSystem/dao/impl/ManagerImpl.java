package com.student_manageSystem.dao.impl;

import com.student_manageSystem.dao.ManageDao;
import com.student_manageSystem.utils.SQLUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ManagerImpl implements ManageDao {
    /**
     * 管理员登录
     * @param id
     * @param pwd
     * @return
     */
    @Override
    public boolean manager_log(String id, String pwd) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = SQLUtils.getConnection();
            String sql = "select * from t_manager_table where id = ? && pwd = ?";
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
     * 计算各个年级，各个分数段的人数
     * @param grade : 年级
     * @param from : 起始位置(包含)
     * @param to : 终止位置(不包含)
     * @return
     */
    public int histogram(int grade, int from, int to) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = SQLUtils.getConnection();
            String sql = "select count(id) as peopleNum from t_student_table where grade = ? && total_score >= ? && total_score < ?;";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, grade);
            pstmt.setInt(2, from);
            pstmt.setInt(3, to);
            rs = pstmt.executeQuery();

            int count = 0;
            if (rs.next()){
                count = rs.getInt("peopleNum");
            }
            return count;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            SQLUtils.closeResource(rs, pstmt, conn);
        }

    }


}
