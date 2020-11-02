package com.wwj.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wwj.crowd.bean.Role;
import com.wwj.crowd.dao.RoleMapper;
import com.wwj.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {
        //开启分页功能
        PageHelper.startPage(pageNum,pageSize);
        //查询Role
        List<Role> roleList = roleMapper.selectRoleByKeyword(keyword);
        //封装PageInfo对象
        PageInfo<Role> pageInfo = new PageInfo<>(roleList);
        return pageInfo;
    }
}
