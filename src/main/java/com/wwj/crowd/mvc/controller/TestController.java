package com.wwj.crowd.mvc.controller;

import com.wwj.crowd.bean.Admin;
import com.wwj.crowd.service.api.AdminService;
import com.wwj.crowd.util.CrowdUtil;
import com.wwj.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {

    @Autowired
    private AdminService adminService;

    Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/test/ssm")
    public String testSSM(Map<String,Object> map, HttpServletRequest request){

        boolean judgeResult = CrowdUtil.judgeRequestType(request);

        logger.info("judgeResult",judgeResult);

        List<Admin> adminList = adminService.getAll();
        map.put("adminList",adminList);

        System.out.println(1 / 0);

        return "target";
    }

    @ResponseBody
    @RequestMapping("/send/array/first")
    public ResultEntity<List<Integer>> testReceiveArray(@RequestParam("array[]") List<Integer> array){
        for (Integer num : array) {
            System.out.print(num + "\t");
        }
        return ResultEntity.successWithData(array);
    }

    @ResponseBody
    @RequestMapping("/send/array/second")
    public String testReceiveArray2(@RequestParam("array") List<Integer> array){
        for (Integer num : array) {
            System.out.println(num + "\t");
        }
        return "success";
    }
}
