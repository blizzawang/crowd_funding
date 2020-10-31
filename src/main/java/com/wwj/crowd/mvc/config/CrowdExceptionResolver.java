package com.wwj.crowd.mvc.config;

import com.google.gson.Gson;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.wwj.crowd.exception.LoginFailedException;
import com.wwj.crowd.util.CrowdConstant;
import com.wwj.crowd.util.CrowdUtil;
import com.wwj.crowd.util.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@ControllerAdvice   //标注当前类为异常处理类
public class CrowdExceptionResolver {

    /**
     * 处理登录异常
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView resolverLoginFailedException(LoginFailedException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //当抛出登录异常时，让用户重新回到登录页面
        String viewName = "admin-login";
        return commonResolver(viewName,exception,request,response);
    }


    /**
     * 将一个具体的异常与方法关联起来，当出现该异常时，执行此方法
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ModelAndView resolverNullPointException(NullPointerException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolver(viewName,exception,request,response);
    }

    /**
     * 处理数学异常
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(value = ArithmeticException.class)
    public ModelAndView resolverArithmeticException(ArithmeticException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolver(viewName,exception,request,response);
    }

    /**
     * 异常映射模板方法
     * @param viewName
     * @return
     */
    private ModelAndView commonResolver(String viewName,Exception exception,HttpServletRequest request,HttpServletResponse response) throws IOException {
        //判断请求类型
        boolean judgeResult = CrowdUtil.judgeRequestType(request);
        if(judgeResult){
            //如果是AJAX请求，则创建ResultEntity对象
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());
            //创建Gson对象
            Gson gson = new Gson();
            //将resultEntity对象转换为json字符串
            String json = gson.toJson(resultEntity);
            //将json字符串作为响应体返回给浏览器
            response.getWriter().write(json);

            return null;    //由于已经返回了数据，所以此处返回null即可
        }
        //如果是普通请求
        ModelAndView modelAndView = new ModelAndView();
        //封装异常信息
        modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION,exception);
        //设置视图
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}
