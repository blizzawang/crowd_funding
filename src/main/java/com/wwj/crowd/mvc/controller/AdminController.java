package com.wwj.crowd.mvc.controller;

import com.github.pagehelper.PageInfo;
import com.wwj.crowd.bean.Admin;
import com.wwj.crowd.service.api.AdminService;
import com.wwj.crowd.util.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 登录
     * @param loginAcct
     * @param userPswd
     * @param session
     * @return
     */
    @RequestMapping("/admin/do/login")
    public String doLogin(
            @RequestParam("loginAcct") String loginAcct,
            @RequestParam("userPswd") String userPswd,
            HttpSession session
    ){

        //登录检查，若该方法成功返回admin对象，则登录成功；否则抛出异常
        Admin admin = adminService.getAdminByLoginAcct(loginAcct,userPswd);

        //将该对象存入session
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN,admin);

        //当从登录页面转发到后台主页面后，再去刷新后台主页面会导致表单重复提交
        //加上登录页面并没有数据需要传递给后台，所以这里应该采用重定向的方式跳转至后台
        //然而重定向是由浏览器进行操作的，浏览器却无法读取到WEB-INF下的页面
        //所以还需要SpringMVC为我们做一次转发
        return "redirect:/admin/to/main/page";
    }

    /**
     * 退出系统
     * @param session
     * @return
     */
    @RequestMapping("/admin/do/logout")
    public String doLogOut(HttpSession session){
        //清除session
        session.invalidate();
        return "redirect:/admin/to/login/page";
    }

    /**
     * 分页查询
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @param modelMap
     * @return
     */
    @RequestMapping("/admin/get/page")
    public String getPageInfo(
            @RequestParam(value = "keyword",defaultValue = "") String keyword,
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
            ModelMap modelMap
    ){
        //调用Service方法获取PageInfo对象
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
        //将PageInfo对象存入请求域
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO,pageInfo);
        return "admin-page";
    }

    @RequestMapping(value = {
            "/admin/remove/{adminId}/{pageNum}/{keyword}",
            "/admin/remove/{adminId}/{pageNum}"
    })
    public String remove(
            @PathVariable("adminId") Integer adminId,
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable(value = "keyword",required = false) String keyword,
            HttpSession session
    ){
        Admin admin = (Admin) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);
        if(Objects.equals(admin.getId(),adminId)){
            //若此时用户删除的是当前登录的用户信息，则抛出异常
            throw new RuntimeException(CrowdConstant.MESSAGE_ADMIN_REMOVE_STATUS);
        }
        //执行删除操作
        adminService.remove(adminId);
        //执行了删除操作之后不应该影响到当前页面的展示
        //比如当执行了关键字查询后再进行删除，应该携带上关键字和页码信息
        //否则分页显示就会被影响
        if(keyword == null){
            keyword = "";
        }
        return "redirect:/admin/get/page?pageNum=" + pageNum + "&keyword=" + keyword;
    }
}
