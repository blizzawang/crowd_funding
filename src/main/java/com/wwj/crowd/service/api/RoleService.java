package com.wwj.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.wwj.crowd.bean.Role;

public interface RoleService {

    PageInfo<Role> getPageInfo(Integer pageNum,Integer pageSize,String keyword);
}
