package com.wwj.crowd.test;

import com.wwj.crowd.bean.Admin;
import com.wwj.crowd.dao.AdminMapper;
import com.wwj.crowd.service.api.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class CrowdTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Test
    public void testConnection() throws Exception{
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void testLog(){
        //获取Logger对象
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);
        //打印日志
        logger.debug("hello,I am debug level!");
        logger.info("hello,I am info level!");
        logger.warn("hello,I am warn level!");
        logger.error("hello,I am error level!");
    }

    @Test
    public void testInsertAdmin(){
        Admin admin = new Admin(null,"tom","123123","汤姆","tom@163.com",null);
        int count = adminMapper.insert(admin);
    }

    @Test
    public void testTx(){
        Admin admin = new Admin(null,"jerry","123456","杰瑞","jerry@163.com",null);
        adminService.saveAdmin(admin);
    }
}
