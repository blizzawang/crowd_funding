package com.wwj.crowd.service.api;

import com.wwj.crowd.bean.Admin;

import java.util.List;

public interface AdminService {

    void saveAdmin(Admin admin);

    List<Admin> getAll();
}
