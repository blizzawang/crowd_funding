package com.wwj.crowd.util;

import javax.servlet.http.HttpServletRequest;

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
}