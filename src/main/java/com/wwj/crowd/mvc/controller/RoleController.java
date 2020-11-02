package com.wwj.crowd.mvc.controller;

import com.github.pagehelper.PageInfo;
import com.wwj.crowd.bean.Role;
import com.wwj.crowd.service.api.RoleService;
import com.wwj.crowd.util.CrowdConstant;
import com.wwj.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @return
     */
    @RequestMapping("/role/get/page")
    public String getPageInfo(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            ModelMap map
    ) {
        //获取分页数据，若获取数据时抛出异常，则会交给异常映射机制处理
        PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);
        map.addAttribute(CrowdConstant.ATTR_NAME_ROLE_PAGE_INFO,pageInfo);
        return "role-page";
    }
}