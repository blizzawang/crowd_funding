package com.wwj.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wwj.crowd.bean.Admin;
import com.wwj.crowd.bean.AdminExample;
import com.wwj.crowd.dao.AdminMapper;
import com.wwj.crowd.exception.LoginAcctAlreadyInUseException;
import com.wwj.crowd.exception.LoginFailedException;
import com.wwj.crowd.service.api.AdminService;
import com.wwj.crowd.util.CrowdConstant;
import com.wwj.crowd.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 保存用户数据
     * @param admin
     */
    @Override
    public void saveAdmin(Admin admin) {
        //给密码加密
        String userPswd = admin.getUserPswd();
        userPswd = CrowdUtil.md5(userPswd);
        admin.setUserPswd(userPswd);
        //生成创建时间
        String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        admin.setCreateTime(createTime);
        //若出现登录账号重复则抛出异常
        try {
            adminMapper.insert(admin);
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof DuplicateKeyException){
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    /**
     * 登录校验
     * @param loginAcct
     * @param userPswd
     * @return
     */
    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
        //根据登录账号查询Admin对象
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        if(adminList == null || adminList.size() == 0){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        Admin admin = adminList.get(0);

        //判断Admin对象是否存在
        if(admin == null){
            //若不存在，则抛出异常
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        //若存在，则将数据表中的密码取出
        String userPswdFromDB = admin.getUserPswd();

        //将表单提交的明文进行加密
        String userPswdFromForm = CrowdUtil.md5(userPswd);

        //将两串密码进行比较
        if(!Objects.equals(userPswdFromDB,userPswdFromForm)){
            //若密码不一致，则抛出异常
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        //若一致，则返回Admin对象
        return admin;
    }

    /**
     * 开启分页功能， 封装PageInfo对象
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        //开启分页功能
        PageHelper.startPage(pageNum,pageSize);
        //执行查询
        List<Admin> adminList = adminMapper.selectAdminByKeyword(keyword);
        //封装PageInfo
        PageInfo<Admin> pageInfo = new PageInfo<>(adminList);
        return pageInfo;
    }

    /**
     * 根据ID删除用户
     * @param adminId
     */
    @Override
    public void remove(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }
}