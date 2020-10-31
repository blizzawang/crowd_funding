package com.wwj.crowd.mvc.interceptor;

import com.wwj.crowd.bean.Admin;
import com.wwj.crowd.exception.AccessForbiddenException;
import com.wwj.crowd.util.CrowdConstant;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *  登录拦截器
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    /**
     * 进行登录检查，根据登录状态设置不同的资源访问限制
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取session
        HttpSession session = request.getSession();

        //判断用户是否登录
        Admin admin = (Admin) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);
        if(admin == null){
            //未登录，则抛出异常
            throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDEN);
        }

        //若已经登录，则直接放行
        return true;
    }
}
