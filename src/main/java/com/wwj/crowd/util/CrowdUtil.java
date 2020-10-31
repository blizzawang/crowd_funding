package com.wwj.crowd.util;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 通用工具类
 */
public class CrowdUtil {

    /**
     * 判断当前请求是否为AJAX请求
     * @param request
     * @return
     *      true:当前请求是AJAX请求
     *      false:当前请求不是AJAX请求
     */
    public static boolean judgeRequestType(HttpServletRequest request){
        //获取请求消息头
        String accept = request.getHeader("Accept");
        String xRequestedWith = request.getHeader("X-Requested-With");

        return (accept != null && accept.contains("application/json")) || (xRequestedWith != null && xRequestedWith.contains("XMLHttpRequest"));
    }

    /**
     * 对明文字符串进行md5加密
     * @param source
     * @return
     */
    public static String md5(String source){
        //判断source是否为一个有效的字符串
        if(source == null || source.length() == 0){
            //无效则抛出异常
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        String algorithm = "md5";
        try {
            //获取MessageDigest对象
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            //获取明文对应的字节数组
            byte[] input = source.getBytes();
            //加密
            byte[] output = messageDigest.digest(input);
            //创建BigInteger对象
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum, output);
            //按照十六进制将bigInteger的值转为字符串
            int radix = 16;
            String encoded = bigInteger.toString(radix).toUpperCase();
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}