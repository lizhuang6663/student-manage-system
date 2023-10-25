package com.student_manageSystem.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * md5数据单向加密(不可逆加密，备注：数据库中的模版数据，如刘备，张飞等密码都是：111)
 */
public class MD5Utils {
  
    /**
     * 使用JDK自带MessageDigest
     * @param pwd
     * @return
     */
    public static String md5Encryption(String pwd) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("md5");

            byte[] bytes = messageDigest.digest(pwd.getBytes("UTF-8"));
            //16是表示转换为16进制数
            String result = new BigInteger(1, bytes).toString(16);

            return result;

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
