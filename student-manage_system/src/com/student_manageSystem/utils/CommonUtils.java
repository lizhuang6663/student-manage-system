package com.student_manageSystem.utils;

/**
 * 普通工具类
 */
public class CommonUtils {

    /**
     * 判断字符串是否可以转换为数字（用来查询使用）
     * @param str : 输入字符串；str为空时，返回false
     * @return : true.可以转换为数字；false.不可以
     */
    public static boolean isStrToNum(String str) {
        try {
            Integer.parseInt(str);//如果可以执行这行代码，就代表可以转换为数字，否则return false
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }



    /**
     * 判断数据是否在正确的区间（0<=每科成绩<=100；1<=年级<=4；班级>=0）
     * @param data : 数据的大小
     * @param min : 允许的最小值（包含最小值）
     * @param max: 允许的最大值（包含最大值）
     */
    public static boolean isDataSuitable(int data, int min, int max) {
        return data >= min && data <= max;
    }

}
