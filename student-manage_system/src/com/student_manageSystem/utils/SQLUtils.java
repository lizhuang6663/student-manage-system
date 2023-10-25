package com.student_manageSystem.utils;

import java.sql.*;

public class SQLUtils {
    //封装字符串常量
    public final static String URL = "jdbc:mysql://127.0.0.1:3306/student_manage_system?useSSL=false";
    public final static String USER = "root";
    public final static String PASSWORD = "root";


    /**
     * 获取数据库连接对象
     * @return 返回数据库连接对象
     */
    public static Connection getConnection() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }



    /**
     * 关闭资源
     * @param rs
     * @param stmt
     * @param conn
     */
    public static void closeResource(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
