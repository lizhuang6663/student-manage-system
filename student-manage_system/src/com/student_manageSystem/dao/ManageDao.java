package com.student_manageSystem.dao;

public interface ManageDao {

    //管理员登录
    boolean manager_log(String id, String pwd);

    //计算各个年级，各个分数段的人数
    int histogram(int grade, int from, int to);

}
