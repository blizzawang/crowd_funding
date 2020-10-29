package com.wwj.crowd.service.impl;

import com.wwj.crowd.bean.Admin;
import com.wwj.crowd.dao.AdminMapper;
import com.wwj.crowd.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void saveAdmin(Admin admin) {
        adminMapper.insert(admin);
    }
}
